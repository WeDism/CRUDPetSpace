package com.pets_space.storages;

import com.google.common.collect.Sets;
import com.pets_space.models.EssenceForSearchFriend;
import com.pets_space.models.Pet;
import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.StateFriend;
import com.pets_space.models.essences.StatusEssence;
import com.pets_space.models.essences.UserEssence;
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
        userEssence.setRole(Arrays.stream(Role.values()).filter(e -> e.name().equals(role)).findFirst().orElse(null));

        final String status = rs.getString("status");
        userEssence.setStatusEssence(Arrays.stream(StatusEssence.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null));

        final Timestamp birthday = rs.getTimestamp("birthday");
        userEssence.setBirthday(birthday != null ? LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()) : null);
        userEssence.setFollowPets(FollowPetStorage.getInstance().getFollowPets(userEssence.getUserEssenceId()));

        userEssence.setPets(this.pets.getPetsOfOwner(userEssence));

        Optional<Map<UUID, StateFriend>> friendsRequestedFrom = this.getFriendsRequestedFrom(userEssence.getUserEssenceId());
        friendsRequestedFrom.ifPresent(userEssence::setRequestedFriendsFrom);

        Optional<Map<UUID, StateFriend>> friendsRequestedTo = this.getFriendsRequestedTo(userEssence.getUserEssenceId());
        friendsRequestedTo.ifPresent(userEssence::setRequestedFriendsTo);

        return userEssence;
    }

    private Optional<UserEssence> getOptional(PreparedStatement statement) throws SQLException {
        Optional<UserEssence> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                UserEssence userEssence = this.getUserEssence(rs);
                result = Optional.of(userEssence);
            }
        }
        return result;
    }

    private Optional<Set<UserEssence>> getSetUserEssences(PreparedStatement statement) throws SQLException {
        Optional<Set<UserEssence>> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            rs.last();
            result = Optional.of(new HashSet<>(rs.getRow()));
            rs.beforeFirst();
            if (rs.next()) {
                UserEssence userEssence = getUserEssence(rs);
                result.get().add(userEssence);
            }
        }
        return result;
    }

    private Optional<Map<UUID, StateFriend>> getSetUserEssencesId(PreparedStatement statement) throws SQLException {
        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            rs.last();
            result = Optional.of(new HashMap<>(rs.getRow()));
            rs.beforeFirst();
            if (rs.next()) {
                UUID userEssenceId = rs.getObject("essence_id", UUID.class);
                final String status = rs.getString("status");
                StateFriend stateFriend = Arrays.stream(StateFriend.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null);
                result.get().put(userEssenceId, stateFriend);
            }
        }
        return result;
    }

    public Optional<Map<UUID, StateFriend>> getFriendsRequestedTo(UUID essence) {
        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT f.friend_id as essence_id, f.status FROM user_essence ue JOIN friends f USING(user_essence_id) WHERE ue.user_essence_id=?",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, essence);
            result = this.getSetUserEssencesId(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in get friend request from", e);
        }
        return result;
    }

    private Optional<Map<UUID, StateFriend>> getFriendsRequestedFrom(UUID essence) {
        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT f.friend_id as essence_id, f.status FROM user_essence ue JOIN friends f ON ue.user_essence_id=f.friend_id AND f.friend_id=?",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, essence);
            result = this.getSetUserEssencesId(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in get friend request to", e);
        }
        return result;
    }

    private void setFriend(UUID essence, UUID friend, StateFriend stateFriend) throws SQLException {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE friends SET user_essence_id=?,friend_id=?,status=?")) {

            statement.setObject(1, essence);
            statement.setObject(2, friend);
            statement.setString(3, stateFriend.name());
            statement.execute();
        }
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
                     connection.prepareStatement("UPDATE user_essence SET role=? WHERE user_essence_id=?")) {
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
            result = this.getOptional(statement);
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
            result = this.getOptional(statement);
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
            result = this.getOptional(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in find by nickname user", e);
        }
        return result;
    }

    public Optional<Set<UserEssence>> findEssences(EssenceForSearchFriend essence) {
        Iterator<String> essenceIterator = essence.iterator();
        Optional<Set<UserEssence>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_essence WHERE " + essence.resultPath(),
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            if (essenceIterator.hasNext()) statement.setString(1, essenceIterator.next());
            if (essenceIterator.hasNext()) statement.setString(2, essenceIterator.next());
            if (essenceIterator.hasNext()) statement.setString(3, essenceIterator.next());

            result = this.getSetUserEssences(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in find by id user", e);
        }
        return result;
    }

    public boolean setFriendsRequest(UserEssence essence, UUID friend) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO friends VALUES (?,?,?)")) {

            statement.setObject(1, essence.getUserEssenceId());
            statement.setObject(2, friend);
            statement.setString(3, StateFriend.REQUESTED.name());
            statement.execute();
            essence.getRequestedFriendsFrom().put(friend, StateFriend.REQUESTED);
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend request", e);
            return false;
        }
        return true;
    }

    public boolean deleteFriendsRequest(UserEssence essence, UUID friend) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM friends WHERE user_essence_id=? AND friend_id=?")) {

            statement.setObject(1, essence.getUserEssenceId());
            statement.setObject(2, friend);
            statement.execute();
            essence.getRequestedFriendsFrom().remove(friend);
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend request", e);
            return false;
        }
        return true;
    }

    public void setFriendsRejectedTo(UUID essence, UUID friend) {
        try {
            this.setFriend(essence, friend, StateFriend.REJECTED);
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend rejected", e);
        }
    }

    public void setFriendsApprovedTo(UUID essence, UUID friend) {
        try {
            this.setFriend(essence, friend, StateFriend.APPROVED);
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend approved", e);
        }
    }

}
