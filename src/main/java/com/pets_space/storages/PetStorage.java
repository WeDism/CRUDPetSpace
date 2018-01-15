package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.SpeciesPet;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        pet.setOwner(UserEntryStorage.getInstance().findById(rs.getObject("user_entry_id", UUID.class)).orElse(null));
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
            statement.setObject(5, pet.getOwner().getUserEntryId());
            statement.setString(6, pet.getSpecies().getName());
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
        }
        return pet;
    }

    public void update(Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE pet SET name=?,weight=?,birthday=?,user_entry_id=?,species=?"
             )) {
            statement.setString(1, pet.getName());
            statement.setDouble(2, pet.getWeight());
            statement.setTimestamp(3, Timestamp.valueOf(pet.getBirthday()));
            statement.setObject(4, pet.getOwner().getUserEntryId());
            statement.setString(5, pet.getSpecies().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred in update pet", e);
        }
    }

    public List<Pet> getAll() {
        List<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM pet")) {
                result = new ArrayList<>(rs.getFetchSize());
                while (rs.next()) {
                    Pet pet = getPet(rs);
                    result.add(pet);
                }
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

}
