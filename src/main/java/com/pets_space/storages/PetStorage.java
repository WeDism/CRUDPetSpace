package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.SpeciesPet;
import com.pets_space.models.UserEssence;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class PetStorage {
    private static final Logger LOG = getLogger(PetStorage.class);
    private static final PetStorage INSTANCE = new PetStorage();

    private PetStorage() {
    }

    public static PetStorage getInstance() {
        return INSTANCE;
    }

    private Pet getPet(ResultSet rs) throws SQLException {
        Pet pet = new Pet();
        pet.setPetId(rs.getObject("pet_id", UUID.class));
        pet.setName(rs.getString("name"));
        pet.setWeight(rs.getDouble("weight"));
        pet.setBirthday(LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()));
        pet.setOwner(rs.getObject("user_essence_id", UUID.class));
        pet.setSpecies(new SpeciesPet(rs.getString("species")));
        return pet;
    }

    public Pet add(Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO pet VALUES (?,?,?,?,?,?)")) {
            statement.setObject(1, pet.getPetId());
            statement.setString(2, pet.getName());
            statement.setDouble(3, pet.getWeight());
            statement.setTimestamp(4, Timestamp.valueOf(pet.getBirthday()));
            statement.setObject(5, pet.getOwner());
            statement.setString(6, pet.getSpecies().getName());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
        }
        return pet;
    }

    public void delete(Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM pet WHERE pet_id=?")) {
            statement.setObject(1, pet.getPetId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in delete pet", e);
        }
    }

    public void delete(Collection<Pet> pets) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM pet WHERE pet_id=?")) {
            for (Pet pet : pets) {
                statement.setObject(1, pet.getPetId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            LOG.error("Error occurred in delete pets", e);
        }
    }

    public void addAll(Set<Pet> pets) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO pet VALUES (?,?,?,?,?,?)")) {
            for (Pet pet : pets) {
                statement.setObject(1, pet.getPetId());
                statement.setString(2, pet.getName());
                statement.setDouble(3, pet.getWeight());
                statement.setTimestamp(4, Timestamp.valueOf(pet.getBirthday()));
                statement.setObject(5, pet.getOwner());
                statement.setString(6, pet.getSpecies().getName());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
        }
    }

    public void update(Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE pet SET name=?,weight=?,birthday=?,user_essence_id=?,species=?")) {
            statement.setString(1, pet.getName());
            statement.setDouble(2, pet.getWeight());
            statement.setTimestamp(3, Timestamp.valueOf(pet.getBirthday()));
            statement.setObject(4, pet.getOwner());
            statement.setString(5, pet.getSpecies().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred in update pet", e);
        }
    }

    public Set<Pet> getAll() {
        Set<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("SELECT * FROM pet")) {
            rs.last();
            result = new HashSet<>(rs.getRow());
            rs.beforeFirst();
            while (rs.next()) {
                Pet pet = getPet(rs);
                result.add(pet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all pets", e);
        }
        return result;
    }

    public Optional<Pet> findById(UUID petId) {
        Optional<Pet> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pet WHERE pet_id=?")) {
            statement.setObject(1, petId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Pet pet = getPet(rs);
                    result = Optional.of(pet);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting pet", e);
        }
        return result;
    }

    public Set<Pet> getPetsOfOwner(UserEssence owner) {
        return this.getPetsOfOwner(owner.getUserEssenceId());
    }

    public Set<Pet> getPetsOfOwner(UUID owner) {
        Set<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pet WHERE user_essence_id=?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, owner);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = new HashSet<>(rs.getRow());
                rs.beforeFirst();
                while (rs.next()) {
                    Pet pet = getPet(rs);
                    result.add(pet);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in get pets of owner", e);
        }
        return result;
    }
}
