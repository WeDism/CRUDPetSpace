package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.UserEssence;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class FollowPetStorage {
    private static final Logger LOG = getLogger(FollowPetStorage.class);
    private static final FollowPetStorage INSTANCE = new FollowPetStorage();

    private FollowPetStorage() {
    }

    public static FollowPetStorage getInstance() {
        return INSTANCE;
    }

    public UserEssence add(UserEssence userEssence, Pet pet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO follow_pets VALUES (?,?)")) {
            statement.setObject(1, pet.getPetId());
            statement.setObject(2, userEssence.getUserEssenceId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
        }
        userEssence.getFollowPets().add(pet);
        return userEssence;
    }

    public Set<Pet> getFollowPets(UUID userEssenceId) {
        Set<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM follow_pets WHERE user_entry_id=?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, userEssenceId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = new HashSet<>(rs.getRow());
                rs.beforeFirst();
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
