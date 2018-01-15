package com.pets_space.storages;

import com.pets_space.models.UserEntry;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class UserEntryStorage {
    private static final Logger LOG = getLogger(UserEntryStorage.class);
    private static final UserEntryStorage INSTANCE = new UserEntryStorage();

    private UserEntryStorage() {
    }

    public static UserEntryStorage getInstance() {
        return UserEntryStorage.INSTANCE;
    }

    private UserEntry getUserEntry(ResultSet rs) throws SQLException {
        UserEntry userEntry = new UserEntry();
        userEntry.setUserEntryId(rs.getObject("user_entry_id", UUID.class));
        userEntry.setNickname(rs.getString("nickname"));
        userEntry.setSurname(rs.getString("surname"));
        userEntry.setPatronymic(rs.getString("pathronymic"));
        userEntry.setPassword(rs.getString("password"));
        userEntry.setEmail(rs.getString("email"));

        final String role = rs.getString("role");
        userEntry.setRole(Arrays.stream(UserEntry.Role.values()).filter(e -> e.name().equals(role)).findFirst().orElse(null));

        final String status = rs.getString("status");
        userEntry.setStatusEntry(Arrays.stream(UserEntry.StatusEntry.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null));

        final Timestamp birthday = rs.getTimestamp("birthday");
        userEntry.setBirthday(birthday != null ? LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()) : null);
        userEntry.setFollowPets(FollowPetStorage.getInstance().getFollowPets(userEntry.getUserEntryId()));
        return userEntry;
    }

    public UserEntry add(UserEntry userEntry) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO user_entry VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            statement.setObject(1, userEntry.getUserEntryId());
            statement.setString(2, userEntry.getNickname());
            statement.setString(3, userEntry.getName());
            statement.setString(4, userEntry.getSurname());
            statement.setString(5, userEntry.getPatronymic());
            statement.setString(6, userEntry.getPassword());
            statement.setString(7, userEntry.getEmail());
            statement.setTimestamp(8, Timestamp.valueOf(userEntry.getBirthday()));
            statement.setString(9, userEntry.getRole().name());
            statement.setString(10, userEntry.getStatusEntry().name());
        } catch (SQLException e) {
            LOG.error("Error occurred in creating userEntry", e);
        }
        return userEntry;
    }

    public void update(UserEntry userEntry) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_entry SET nickname=?,name=?,surname=?,pathronymic=?,password=?,email=?,role=?,status=?,birthday=?")) {
            statement.setString(1, userEntry.getNickname());
            statement.setString(2, userEntry.getName());
            statement.setString(3, userEntry.getSurname());
            statement.setString(4, userEntry.getPatronymic());
            statement.setString(5, userEntry.getPassword());
            statement.setString(6, userEntry.getEmail());
            statement.setString(7, userEntry.getRole().name());
            statement.setString(8, userEntry.getStatusEntry().name());
            statement.setTimestamp(9, Timestamp.valueOf(userEntry.getBirthday()));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEntry", e);
        }
    }

    public List<UserEntry> getAll() {
        List<UserEntry> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM user_entry")) {
                result = new ArrayList<>(rs.getFetchSize());
                while (rs.next()) {
                    UserEntry userEntry = getUserEntry(rs);
                    result.add(userEntry);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all users", e);
        }
        return result;
    }

    public Optional<UserEntry> findByCredential(String nickname, String password) {
        Optional<UserEntry> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM user_entry WHERE nickname=? AND password=?")) {
            statement.setString(1, nickname);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserEntry userEntry = this.getUserEntry(rs);
                    result = Optional.of(userEntry);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by credential user", e);
        }
        return result;
    }

    public Optional<UserEntry> findById(UUID userEntryId) {
        Optional<UserEntry> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_entry WHERE user_entry_id=?")) {
            statement.setObject(1, userEntryId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserEntry userEntry = getUserEntry(rs);
                    result = Optional.of(userEntry);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

}
