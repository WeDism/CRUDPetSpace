package com.pets_space.storages;

import com.google.common.collect.Sets;
import com.pets_space.models.EssenceForSearchFriend;
import com.pets_space.models.Pet;
import com.pets_space.models.UserEssence;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class UserEssenceStorage {
    private static final Logger LOG = getLogger(UserEssenceStorage.class);
    private static final UserEssenceStorage INSTANCE = new UserEssenceStorage();
    private final PetStorage pets = PetStorage.getInstance();

    private UserEssenceStorage() {
    }

    public static UserEssenceStorage getInstance() {
        return UserEssenceStorage.INSTANCE;
    }

    private UserEssence getUserEssence(ResultSet rs) throws SQLException {
        UserEssence userEssence = new UserEssence();
        userEssence.setUserEssenceId(rs.getObject("user_essence_id", UUID.class));
        userEssence.setNickname(rs.getString("nickname"));
        userEssence.setName(rs.getString("name"));
        userEssence.setSurname(rs.getString("surname"));
        userEssence.setPatronymic(rs.getString("patronymic"));
        userEssence.setPassword(rs.getString("password"));
        userEssence.setEmail(rs.getString("email"));

        final String role = rs.getString("role");
        userEssence.setRole(Arrays.stream(UserEssence.Role.values()).filter(e -> e.name().equals(role)).findFirst().orElse(null));

        final String status = rs.getString("status");
        userEssence.setStatusEssence(Arrays.stream(UserEssence.StatusEssence.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null));

        final Timestamp birthday = rs.getTimestamp("birthday");
        userEssence.setBirthday(birthday != null ? LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()) : null);
        userEssence.setFollowPets(FollowPetStorage.getInstance().getFollowPets(userEssence.getUserEssenceId()));

        userEssence.setPets(this.pets.getPetsOfOwner(userEssence));
        return userEssence;
    }

    public UserEssence add(UserEssence userEssence) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO user_essence VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            statement.setObject(1, userEssence.getUserEssenceId());
            statement.setString(2, userEssence.getNickname());
            statement.setString(3, userEssence.getName());
            statement.setString(4, userEssence.getSurname());
            statement.setString(5, userEssence.getPatronymic());
            statement.setString(6, userEssence.getPassword());
            statement.setString(7, userEssence.getEmail());
            statement.setTimestamp(8, Timestamp.valueOf(userEssence.getBirthday()));
            statement.setString(9, userEssence.getRole().name());
            statement.setString(10, userEssence.getStatusEssence().name());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating userEssence", e);
        }
        return userEssence;
    }

    public UserEssence update(UserEssence userEssence) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_essence SET " +
                             "nickname=?,name=?,surname=?,pathronymic=?,password=?,email=?,role=?,status=?,birthday=? WHERE user_essence_id=?")) {
            connection.setAutoCommit(false);
            statement.setString(1, userEssence.getNickname());
            statement.setString(2, userEssence.getName());
            statement.setString(3, userEssence.getSurname());
            statement.setString(4, userEssence.getPatronymic());
            statement.setString(5, userEssence.getPassword());
            statement.setString(6, userEssence.getEmail());
            statement.setString(7, userEssence.getRole().name());
            statement.setString(8, userEssence.getStatusEssence().name());
            statement.setTimestamp(9, userEssence.getBirthday() != null ? Timestamp.valueOf(userEssence.getBirthday()) : null);
            statement.setObject(10, userEssence.getUserEssenceId());
            statement.executeUpdate();
            Set<Pet> differenceSets = Sets.difference(userEssence.getPets(), this.pets.getPetsOfOwner(userEssence));
            if (!differenceSets.isEmpty()) this.pets.addAll(differenceSets);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEssence", e);
        }
        return userEssence;
    }

    public UserEssence updateRole(UserEssence userEssence) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_essence SET " +
                             "role=? WHERE user_essence_id=?")) {
            statement.setString(1, userEssence.getRole().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEssence role", e);
        }
        return userEssence;
    }

    public void delete(UUID userEssence) {
        Optional<UserEssence> essence = this.findById(userEssence);
        if (essence.isPresent()) {
            try (Connection connection = Pool.getDataSource().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("DELETE FROM user_essence WHERE user_essence_id=?")) {
                connection.setAutoCommit(false);
                this.pets.delete(essence.get().getPets());
                statement.setObject(1, userEssence);
                statement.executeUpdate();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.error("Error occurred in update userEssence", e);
            }
        }
    }

    public Set<UserEssence> getAll() {
        Set<UserEssence> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("SELECT * FROM user_essence")) {
            rs.last();
            result = new HashSet<>(rs.getRow());
            rs.beforeFirst();
            while (rs.next()) {
                UserEssence userEssence = getUserEssence(rs);
                result.add(userEssence);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all users", e);
        }
        return result;
    }

    public Optional<UserEssence> findByCredential(String nickname, String password) {
        Optional<UserEssence> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM user_essence WHERE nickname=? AND password=?")) {
            statement.setString(1, nickname);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserEssence userEssence = this.getUserEssence(rs);
                    result = Optional.of(userEssence);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by credential user", e);
        }
        return result;
    }

    public Optional<UserEssence> findById(UUID userEssenceId) {
        Optional<UserEssence> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_essence WHERE user_essence_id=?")) {
            statement.setObject(1, userEssenceId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserEssence userEssence = getUserEssence(rs);
                    result = Optional.of(userEssence);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

    public Optional<UserEssence> findByNickname(String nickname) {
        Optional<UserEssence> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_essence WHERE nickname=?")) {
            statement.setString(1, nickname);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserEssence userEssence = getUserEssence(rs);
                    result = Optional.of(userEssence);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by nickname user", e);
        }
        return result;
    }

    public Optional<Set<UserEssence>> findFriends(EssenceForSearchFriend essence) {
        Iterator<String> essenceIterator = essence.iterator();
        Optional<Set<UserEssence>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_essence WHERE " + essence.resultPath(),
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            if (essenceIterator.hasNext()) statement.setString(1, essenceIterator.next());
            if (essenceIterator.hasNext()) statement.setString(2, essenceIterator.next());
            if (essenceIterator.hasNext()) statement.setString(3, essenceIterator.next());

            try (ResultSet rs = statement.executeQuery()) {
                rs.last();
                result = Optional.of(new HashSet<>(rs.getRow()));
                rs.beforeFirst();
                if (rs.next()) {
                    UserEssence userEssence = getUserEssence(rs);
                    result.get().add(userEssence);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

}
