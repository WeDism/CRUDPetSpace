package com.pets_space.storages;

import com.pets_space.models.GenusPet;
import com.pets_space.models.Pet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
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
        return Pet.builder()
                .petId(rs.getObject("pet_id", UUID.class))
                .name(rs.getString("name"))
                .owner(rs.getObject("user_essence_id", UUID.class))
                .genusPet(new GenusPet(rs.getString("genus")))
                .weight(rs.getDouble("weight"))
                .birthday(rs.getTimestamp("birthday") != null
                        ? LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()) : null)
                .build();
    }

    private void setPet(Pet pet, PreparedStatement statement) throws SQLException {
        statement.setObject(1, pet.getPetId());
        statement.setString(2, pet.getName());
        statement.setObject(5, pet.getOwner());
        statement.setString(6, pet.getGenusPet().getName());

        if (pet.getBirthday() != null) statement.setTimestamp(4, Timestamp.valueOf(pet.getBirthday()));
        else statement.setNull(4, Types.TIMESTAMP);
        if (pet.getWeight() != null) statement.setDouble(3, pet.getWeight());
        else statement.setNull(3, Types.REAL);
    }

    public boolean add(@NotNull Pet pet) {
        checkNotNull(pet);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO pet VALUES (?,?,?,?,?,?)")) {
            setPet(pet, statement);

            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
            return false;
        }
        return true;
    }

    public boolean delete(@NotNull Pet pet) {
        checkNotNull(pet);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM pet WHERE pet_id=?")) {
            statement.setObject(1, pet.getPetId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in delete pet", e);
            return false;
        }
        return true;
    }

    public boolean delete(@NotNull Set<Pet> pets) {
        checkState(!pets.isEmpty());
        checkState(pets.size() == pets.stream().filter(Objects::nonNull).collect(Collectors.toSet()).size());

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
            return false;
        }
        return true;
    }

    public boolean addAll(@NotNull Set<Pet> pets) {
        checkState(!pets.isEmpty());
        checkState(pets.size() == pets.stream().filter(Objects::nonNull).collect(Collectors.toSet()).size());

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO pet VALUES (?,?,?,?,?,?)")) {
            for (Pet pet : pets) {
                setPet(pet, statement);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
            return false;
        }
        return true;
    }

    public boolean update(@NotNull Pet pet) {
        checkNotNull(pet);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE pet SET name=?,weight=?,birthday=?,user_essence_id=?,genus=?")) {
            statement.setString(1, pet.getName());
            statement.setObject(4, pet.getOwner());
            statement.setString(5, pet.getGenusPet().getName());

            if (pet.getBirthday() != null) statement.setTimestamp(3, Timestamp.valueOf(pet.getBirthday()));
            else statement.setNull(3, Types.TIMESTAMP);
            if (pet.getWeight() != null) statement.setDouble(2, pet.getWeight());
            else statement.setNull(2, Types.REAL);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOG.error("Error occurred in update pet", e);
            return false;
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

    public Optional<Pet> findById(@NotNull UUID petId) {
        checkNotNull(petId);

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

    public Set<Pet> getPetsOfOwner(@NotNull UUID owner) {
        checkNotNull(owner);

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

    public Set<UUID> getUuidPetsOfOwner(@NotNull UUID owner) {
        checkNotNull(owner);

        Set<UUID> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT pet_id FROM pet WHERE user_essence_id=?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, owner);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = new HashSet<>(rs.getRow());
                rs.beforeFirst();
                while (rs.next()) {
                    result.add(rs.getObject("pet_id", UUID.class));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in get pets of owner", e);
        }
        return result;
    }
}
