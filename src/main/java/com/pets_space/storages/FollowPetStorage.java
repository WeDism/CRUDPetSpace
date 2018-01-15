package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.UserEntry;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class FollowPetStorage {
    private static final Logger LOG = getLogger(FollowPetStorage.class);
    private static final FollowPetStorage INSTANCE = new FollowPetStorage();

    private FollowPetStorage() {
    }

    public static FollowPetStorage getInstance() {
        return INSTANCE;
    }

    public UserEntry add(UserEntry userEntry, Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO follow_pets VALUES (?,?)")) {
            statement.setObject(1, pet.getPetId());
            statement.setObject(2, userEntry.getUserEntryId());
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
        }
        userEntry.getFollowPets().add(pet);
        return userEntry;
    }

    public Set<Pet> getFollowPets(UUID userEntryId) {
        Set<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM follow_pets WHERE user_entry_id=?")) {
            statement.setObject(1, userEntryId);
            try (ResultSet rs = statement.executeQuery()) {
                result = new HashSet<>(rs.getFetchSize());
                if (rs.next()) {
                    Optional<Pet> pet = getPet(rs);
                    if (pet.isPresent()) result.add(pet.get());
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting follow pets", e);
        }
        return result;
    }

    private Optional<Pet> getPet(ResultSet rs) throws SQLException {
        return PetStorage.getInstance().findById(rs.getObject("user_entry_id", UUID.class));
    }

}
