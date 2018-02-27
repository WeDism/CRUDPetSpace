package com.pet_space.storages;

import com.pet_space.models.Pet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

public class FollowPetStorage {
    private static final Logger LOG = getLogger(FollowPetStorage.class);
    private static final FollowPetStorage INSTANCE = new FollowPetStorage();
    private final PetStorage pets = PetStorage.getInstance();

    private FollowPetStorage() {
    }

    public static FollowPetStorage getInstance() {
        return INSTANCE;
    }

    public boolean add(@NotNull UUID userEssenceId, @NotNull UUID petId) {
        checkNotNull(userEssenceId);
        checkArgument(!userEssenceId.equals(petId));
        checkArgument(!this.pets.getUuidPetsOfOwner(userEssenceId).contains(petId));

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO follow_pets VALUES (?,?)")) {
            statement.setObject(1, petId);
            statement.setObject(2, userEssenceId);
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating pet", e);
            return false;
        }
        return true;
    }

    public Set<Pet> getFollowPets(@NotNull UUID userEssenceId) {
        checkNotNull(userEssenceId);

        Set<Pet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT pet_id FROM follow_pets WHERE user_essence_id=?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, userEssenceId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = new HashSet<>(rs.getRow());
                rs.beforeFirst();
                while (rs.next()) {
                    result.add(pets.findById(rs.getObject("pet_id", UUID.class)).orElse(null));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting follow pets", e);
        }
        return result;
    }
}
