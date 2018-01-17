package com.pets_space.storages;

import com.google.common.collect.Sets;
import com.pets_space.models.Pet;
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
    private final PetStorage pets = PetStorage.getInstance();

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

        userEntry.setPets(this.pets.getPetsOfOwner(userEntry));
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
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating userEntry", e);
        }
        return userEntry;
    }

    public void update(UserEntry userEntry) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_entry SET " +
                             "nickname=?,name=?,surname=?,pathronymic=?,password=?,email=?,role=?,status=?,birthday=? WHERE user_entry_id=?")) {
            connection.setAutoCommit(false);
            statement.setString(1, userEntry.getNickname());
            statement.setString(2, userEntry.getName());
            statement.setString(3, userEntry.getSurname());
            statement.setString(4, userEntry.getPatronymic());
            statement.setString(5, userEntry.getPassword());
            statement.setString(6, userEntry.getEmail());
            statement.setString(7, userEntry.getRole().name());
            statement.setString(8, userEntry.getStatusEntry().name());
            statement.setTimestamp(9, userEntry.getBirthday() != null ? Timestamp.valueOf(userEntry.getBirthday()) : null);
            statement.setObject(10, userEntry.getUserEntryId());
            statement.executeUpdate();
            Set<Pet> differenceSets = Sets.difference(userEntry.getPets(), this.pets.getPetsOfOwner(userEntry));
            this.pets.addAll(differenceSets);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEntry", e);
        }
    }

    public Set<UserEntry> getAll() {
        Set<UserEntry> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("SELECT * FROM user_entry")) {
            rs.last();
            result = new HashSet<>(rs.getRow());
            rs.beforeFirst();
            while (rs.next()) {
                UserEntry userEntry = getUserEntry(rs);
                result.add(userEntry);
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
