package com.pets_space.storages;

import com.google.common.base.Strings;
import com.pets_space.models.essences.LiteEssence;
import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.StatusEssence;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.*;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.slf4j.LoggerFactory.getLogger;

public class LiteEssenceStorage {
    private static final Logger LOG = getLogger(UserEssenceStorage.class);
    private static final LiteEssenceStorage INSTANCE = new LiteEssenceStorage();
    private final PetStorage pets = PetStorage.getInstance();

    private LiteEssenceStorage() {
    }

    public static LiteEssenceStorage getInstance() {
        return LiteEssenceStorage.INSTANCE;
    }

    private LiteEssence getLiteEssence(ResultSet rs) throws SQLException {
        LiteEssence liteEssence = new LiteEssence();
        liteEssence.setUserEssenceId(rs.getObject("user_essence_id", UUID.class));
        liteEssence.setNickname(rs.getString("nickname"));
        liteEssence.setName(rs.getString("name"));
        liteEssence.setSurname(rs.getString("surname"));
        liteEssence.setPatronymic(rs.getString("patronymic"));
        liteEssence.setAboutOfSelf(rs.getString("about_of_self"));

        final String role = rs.getString("role");
        liteEssence.setRole(Arrays.stream(Role.values()).filter(e -> e.name().equals(role)).findFirst().orElse(null));
        final String status = rs.getString("status");
        liteEssence.setStatusEssence(Arrays.stream(StatusEssence.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null));

        return liteEssence;
    }

    private Optional<LiteEssence> getOptional(PreparedStatement statement) throws SQLException {
        Optional<LiteEssence> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                LiteEssence liteEssence = this.getLiteEssence(rs);
                result = Optional.of(liteEssence);
            }
        }
        return result;
    }

    public Optional<LiteEssence> findById(@NotNull UUID userEssenceId) {
        checkNotNull(userEssenceId);

        Optional<LiteEssence> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT user_essence_id,nickname,name,surname,patronymic,role,status,about_of_self FROM user_essence WHERE user_essence_id=?")) {
            statement.setObject(1, userEssenceId);
            result = this.getOptional(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

    public Optional<Set<LiteEssence>> findByIds(@NotNull Set<UUID> userEssenceIds) {
        checkNotNull(userEssenceIds);
        checkState(userEssenceIds.stream().noneMatch(Objects::isNull));

        if (userEssenceIds.size() == 0) return Optional.empty();

        Optional<Set<LiteEssence>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT user_essence_id,nickname,name,surname,patronymic,role,status,about_of_self FROM user_essence WHERE user_essence_id = ALL(?)",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            Array varchar = statement.getConnection().createArrayOf("UUID", userEssenceIds.toArray());

            statement.setArray(1, varchar);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = Optional.of(new HashSet<>(rs.getRow()));
                rs.beforeFirst();
                while (rs.next()) {
                    result.get().add(this.getLiteEssence(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

}
