package com.pets_space.storages;

import com.pets_space.models.essences.LiteEssence;
import com.pets_space.models.essences.Role;
import org.slf4j.Logger;

import java.sql.*;
import java.util.*;

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

        final String role = rs.getString("role");
        liteEssence.setRole(Arrays.stream(Role.values()).filter(e -> e.name().equals(role)).findFirst().orElse(null));

        return liteEssence;
    }

    public Optional<Set<LiteEssence>> findByIds(Set<UUID> userEssenceIds) {
        Optional<Set<LiteEssence>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT user_essence_id,nickname,name,surname,patronymic,role FROM user_essence WHERE user_essence_id IN (?)",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            Array varchar = statement.getConnection().createArrayOf("VARCHAR", userEssenceIds.toArray());

            statement.setArray(1, varchar);
            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = Optional.of(new HashSet<>(rs.getRow()));
                rs.beforeFirst();
                if (rs.next()) {
                    result.get().add(this.getLiteEssence(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

}
