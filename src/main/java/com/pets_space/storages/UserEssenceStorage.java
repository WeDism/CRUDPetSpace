package com.pets_space.storages;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.pets_space.models.EssenceForSearchFriend;
import com.pets_space.models.Pet;
import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.StateFriend;
import com.pets_space.models.essences.StatusEssence;
import com.pets_space.models.essences.UserEssence;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
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

    private UserEssence getUserEssence(@NotNull ResultSet rs) throws SQLException {
        checkNotNull(rs);

        final String role = rs.getString("role");
        final String status = rs.getString("status");
        final Timestamp birthday = rs.getTimestamp("birthday");

        UserEssence userEssence = UserEssence.builder()
                .userEssenceId(rs.getObject("user_essence_id", UUID.class))
                .nickname(rs.getString("nickname"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .role(Arrays.stream(Role.values()).filter(e -> e.name().equals(role)).findFirst().get())
                .statusEssence(Arrays.stream(StatusEssence.values()).filter(e -> e.name().equals(status)).findFirst().get())
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .patronymic(rs.getString("patronymic"))
                .birthday(birthday != null ? LocalDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneId.systemDefault()) : null)
                .aboutOfSelf(rs.getString("about_of_self"))
                .build();

        userEssence.setFollowPets(FollowPetStorage.getInstance().getFollowPets(userEssence.getUserEssenceId()));

        userEssence.setPets(this.pets.getPetsOfOwner(userEssence.getUserEssenceId()));

        Optional<Map<UUID, StateFriend>> friendsRequestedFrom = this.getFriendsRequestedFrom(userEssence.getUserEssenceId());
        friendsRequestedFrom.ifPresent(userEssence::setRequestedFriendsFrom);

        Optional<Map<UUID, StateFriend>> friendsRequestedTo = this.getFriendsRequestedTo(userEssence.getUserEssenceId());
        friendsRequestedTo.ifPresent(userEssence::setRequestedFriendsTo);

        return userEssence;
    }

    private Optional<UserEssence> getOptional(@NotNull PreparedStatement statement) throws SQLException {
        checkNotNull(statement);

        Optional<UserEssence> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                UserEssence userEssence = this.getUserEssence(rs);
                result = Optional.of(userEssence);
            }
        }
        return result;
    }

    private Optional<Set<UserEssence>> getSetUserEssences(@NotNull PreparedStatement statement) throws SQLException {
        checkNotNull(statement);

        Optional<Set<UserEssence>> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            rs.last();
            result = Optional.of(new HashSet<>(rs.getRow()));
            rs.beforeFirst();
            while (rs.next()) {
                UserEssence userEssence = getUserEssence(rs);
                result.get().add(userEssence);
            }
        }
        return result;
    }

    private Optional<Map<UUID, StateFriend>> getSetUserEssencesId(@NotNull PreparedStatement statement) throws SQLException {
        checkNotNull(statement);

        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (ResultSet rs = statement.executeQuery()) {
            rs.last();
            result = Optional.of(new HashMap<>(rs.getRow()));
            rs.beforeFirst();
            while (rs.next()) {
                UUID userEssenceId = rs.getObject("essence_id", UUID.class);
                final String status = rs.getString("status");
                StateFriend stateFriend = Arrays.stream(StateFriend.values()).filter(e -> e.name().equals(status)).findFirst().orElse(null);
                result.get().put(userEssenceId, stateFriend);
            }
        }
        return result;
    }

    private Optional<Map<UUID, StateFriend>> getFriendsRequestedTo(@NotNull UUID essence) {
        checkNotNull(essence);

        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT f.user_essence_id AS essence_id, f.status FROM user_essence ue JOIN friends f ON ue.user_essence_id=f.user_essence_id WHERE f.friend_id=?",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, essence);
            result = this.getSetUserEssencesId(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in get friend request from", e);
        }
        return result;
    }

    private Optional<Map<UUID, StateFriend>> getFriendsRequestedFrom(@NotNull UUID essence) {
        checkNotNull(essence);

        Optional<Map<UUID, StateFriend>> result = Optional.empty();
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT f.friend_id AS essence_id, f.status FROM user_essence ue JOIN friends f ON ue.user_essence_id=f.friend_id AND f.user_essence_id=?",
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, essence);
            result = this.getSetUserEssencesId(statement);
        } catch (SQLException e) {
            LOG.error("Error occurred in get friend request to", e);
        }
        return result;
    }

    public boolean setFriendState(@NotNull UUID essence, @NotNull UUID friend, @NotNull StateFriend stateFriend) {
        checkNotNull(essence);
        checkNotNull(friend);
        checkNotNull(stateFriend);
        checkArgument(!essence.equals(friend));

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE friends SET status=? WHERE user_essence_id=? AND friend_id=?")) {

            statement.setString(1, stateFriend.name());
            statement.setObject(2, friend);
            statement.setObject(3, essence);
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend state", e);
            return false;
        }
        return true;
    }

    public boolean add(@NotNull UserEssence userEssence) {
        checkNotNull(userEssence);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO user_essence VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {

            statement.setObject(1, userEssence.getUserEssenceId());
            statement.setString(2, userEssence.getNickname());
            statement.setString(3, userEssence.getName());
            statement.setString(4, userEssence.getSurname());
            statement.setString(6, userEssence.getPassword());
            statement.setString(7, userEssence.getEmail());
            statement.setString(10, userEssence.getRole().name());
            statement.setString(11, userEssence.getStatusEssence().name());

            if (!Strings.isNullOrEmpty(userEssence.getPatronymic()))
                statement.setString(5, userEssence.getPatronymic());
            else statement.setNull(5, Types.VARCHAR);

            String numberPhone = userEssence.getPhone().stream().map(String::valueOf).collect(Collectors.joining());
            if (!Strings.isNullOrEmpty(numberPhone)) statement.setString(8, numberPhone);
            else statement.setNull(8, Types.VARCHAR);

            if (userEssence.getBirthday() != null)
                statement.setTimestamp(9, Timestamp.valueOf(userEssence.getBirthday()));
            else statement.setNull(9, Types.TIMESTAMP);

            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating userEssence", e);
            return false;
        }
        return true;
    }

    public boolean update(@NotNull UserEssence userEssence) {
        checkNotNull(userEssence);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_essence SET " +
                             "nickname=?,name=?,surname=?,patronymic=?,password=?,email=?,role=?,phone=?,status=?,birthday=? WHERE user_essence_id=?")) {
            connection.setAutoCommit(false);

            statement.setString(1, userEssence.getNickname());
            statement.setString(2, userEssence.getName());
            statement.setString(3, userEssence.getSurname());
            statement.setString(5, userEssence.getPassword());
            statement.setString(6, userEssence.getEmail());
            statement.setString(7, userEssence.getRole().name());
            statement.setString(9, userEssence.getStatusEssence().name());
            statement.setObject(11, userEssence.getUserEssenceId());


            if (!Strings.isNullOrEmpty(userEssence.getPatronymic()))
                statement.setString(4, userEssence.getPatronymic());
            else statement.setNull(4, Types.VARCHAR);

            String numberPhone = userEssence.getPhone().stream().map(String::valueOf).collect(Collectors.joining());
            if (!Strings.isNullOrEmpty(numberPhone)) statement.setString(8, numberPhone);
            else statement.setNull(8, Types.VARCHAR);

            if (userEssence.getBirthday() != null)
                statement.setTimestamp(10, Timestamp.valueOf(userEssence.getBirthday()));
            else statement.setNull(10, Types.TIMESTAMP);

            statement.executeUpdate();
            Set<Pet> differenceSets = Sets.difference(userEssence.getPets(), this.pets.getPetsOfOwner(userEssence.getUserEssenceId()));
            if (!differenceSets.isEmpty()) this.pets.addAll(differenceSets);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEssence", e);
            return false;
        }
        return true;
    }

    public boolean updateRole(@NotNull UserEssence userEssence) {
        checkNotNull(userEssence);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE user_essence SET role=? WHERE user_essence_id=?")) {
            statement.setString(1, userEssence.getRole().name());
            statement.setObject(2, userEssence.getUserEssenceId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred in update userEssence role", e);
            return false;
        }
        return true;
    }

    public boolean delete(@NotNull UUID userEssence) {
        checkNotNull(userEssence);

        Optional<UserEssence> essence = this.findById(userEssence);
        if (essence.isPresent()) {
            try (Connection connection = Pool.getDataSource().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("DELETE FROM user_essence WHERE user_essence_id=?")) {
                connection.setAutoCommit(false);
                if (essence.get().getPets().size() > 0) this.pets.delete(essence.get().getPets());
                statement.setObject(1, userEssence);
                statement.executeUpdate();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.error("Error occurred in update userEssence", e);
                return false;
            }
        }
        return true;
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

    public Optional<UserEssence> findByCredential(@NotNull String nickname, @NotNull String password) {
        checkNotNull(nickname);
        checkNotNull(password);

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

    public Optional<UserEssence> findById(@NotNull UUID userEssenceId) {
        checkNotNull(userEssenceId);

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

    public Optional<UserEssence> findByNickname(@NotNull String nickname) {
        checkNotNull(nickname);

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

    public Optional<Set<UserEssence>> findEssences(@NotNull EssenceForSearchFriend essence) {
        checkNotNull(essence);

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

    public boolean setFriendsRequest(@NotNull UUID essence, @NotNull UUID friend) {
        checkNotNull(essence);
        checkArgument(!essence.equals(friend));

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO friends VALUES (?,?,?)")) {

            statement.setObject(1, essence);
            statement.setObject(2, friend);
            statement.setString(3, StateFriend.REQUESTED.name());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend request", e);
            return false;
        }
        return true;
    }

    public boolean deleteFriendsRequest(@NotNull UUID essence, @NotNull UUID friend) {
        checkNotNull(essence);
        checkNotNull(friend);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM friends WHERE user_essence_id=? AND friend_id=?")) {

            statement.setObject(1, essence);
            statement.setObject(2, friend);
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in set friend request", e);
            return false;
        }
        return true;
    }

}
