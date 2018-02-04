package com.pets_space.models.essences;

import com.pets_space.models.Message;
import com.pets_space.models.Pet;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserEssence extends Essence {
    private String password;
    private List<Byte> phone;
    private String email;
    private Map<UUID, StateFriend> requestedFriendsFrom = new HashMap<>();
    private Map<UUID, StateFriend> requestedFriendsTo = new HashMap<>();
    private Set<Pet> pets = new HashSet<>();
    private Set<Pet> followPets;
    private Set<Message> sentMessages;
    private Set<Message> inboxMessages;
    private LocalDateTime birthday;

    private UserEssence() {
    }

    public static IUserEssenceId builder() {
        return new BuilderUserEssence();
    }

    public static class BuilderUserEssence implements IUserEssenceId, INickname, ISurname, IName, IRole, IStatusEssence, IEmail, IPassword, IBuild {

        UserEssence userEssence = new UserEssence();

        @Override
        public INickname userEssenceId(@NotNull UUID userEssenceId) {
            this.userEssence.userEssenceId = userEssenceId;
            return this;
        }

        @Override
        public IName nickname(@NotNull String nickname) {
            this.userEssence.nickname = nickname;
            return this;
        }

        @Override
        public ISurname name(@NotNull String name) {
            this.userEssence.name = name;
            return this;
        }

        @Override
        public IRole surname(@NotNull String surname) {
            this.userEssence.surname = surname;
            return this;
        }

        @Override
        public IStatusEssence role(@NotNull Role role) {
            this.userEssence.role = role;
            return this;
        }

        @Override
        public IEmail statusEssence(@NotNull StatusEssence statusEssence) {
            this.userEssence.statusEssence = statusEssence;
            return this;
        }

        @Override
        public IPassword email(@NotNull String email) {
            this.userEssence.email = email;
            return this;
        }

        @Override
        public IBuild password(@NotNull String password) {
            this.userEssence.password = password;
            return this;
        }

        public UserEssence build() {
            Set<?> setArguments = Set.of(this.userEssence.userEssenceId, this.userEssence.nickname,
                    this.userEssence.name, this.userEssence.surname, this.userEssence.role,
                    this.userEssence.statusEssence, this.userEssence.email, this.userEssence.password);
            if (setArguments.stream().anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException(
                        String.format("The arguments(%s) are null", setArguments.stream().filter(Objects::isNull).collect(Collectors.toList())));
            }
            return this.userEssence;
        }

        public BuilderUserEssence patronymic(String patronymic) {
            this.userEssence.patronymic = patronymic;
            return this;
        }

        public BuilderUserEssence phone(List<Byte> phone) {
            this.userEssence.phone = phone;
            return this;
        }

        public BuilderUserEssence birthday(LocalDateTime birthday) {
            this.userEssence.birthday = birthday;
            return this;
        }

        public BuilderUserEssence inboxMessages(Set<Message> inboxMessages) {
            this.userEssence.inboxMessages = inboxMessages;
            return this;
        }

        public BuilderUserEssence sentMessages(Set<Message> sentMessages) {
            this.userEssence.sentMessages = sentMessages;
            return this;
        }

        public BuilderUserEssence followPets(Set<Pet> followPets) {
            this.userEssence.followPets = followPets;
            return this;
        }

        public BuilderUserEssence pets(Set<Pet> pets) {
            this.userEssence.pets = pets;
            return this;
        }

        public BuilderUserEssence requestedFriendsTo(Map<UUID, StateFriend> requestedFriendsTo) {
            this.userEssence.requestedFriendsTo = requestedFriendsTo;
            return this;
        }

        public BuilderUserEssence requestedFriendsFrom(Map<UUID, StateFriend> requestedFriendsFrom) {
            this.userEssence.requestedFriendsFrom = requestedFriendsFrom;
            return this;
        }

    }

    public interface IUserEssenceId {
        INickname userEssenceId(@NotNull UUID userEssenceId);
    }

    public interface INickname {
        IName nickname(@NotNull String nickname);
    }

    public interface IName {
        ISurname name(@NotNull String name);
    }

    public interface ISurname {
        IRole surname(@NotNull String surname);
    }

    public interface IRole {
        IStatusEssence role(@NotNull Role role);
    }

    public interface IStatusEssence {
        IEmail statusEssence(@NotNull StatusEssence statusEssence);
    }

    public interface IEmail {
        IPassword email(@NotNull String email);
    }

    public interface IPassword {
        IBuild password(@NotNull String email);
    }

    public interface IBuild {
        BuilderUserEssence patronymic(String patronymic);

        BuilderUserEssence phone(List<Byte> phone);

        BuilderUserEssence birthday(LocalDateTime birthday);

        BuilderUserEssence inboxMessages(Set<Message> inboxMessages);

        BuilderUserEssence sentMessages(Set<Message> sentMessages);

        BuilderUserEssence followPets(Set<Pet> followPets);

        BuilderUserEssence pets(Set<Pet> pets);

        BuilderUserEssence requestedFriendsTo(Map<UUID, StateFriend> requestedFriendsTo);

        BuilderUserEssence requestedFriendsFrom(Map<UUID, StateFriend> requestedFriendsFrom);

        UserEssence build();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Byte> getPhone() {
        return this.phone;
    }

    public void setPhone(List<Byte> phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public UserEssence setRole(Role role) {
        super.setRole(role);
        return this;
    }

    @Override
    public UserEssence setPatronymic(String patronymic) {
        super.setPatronymic(patronymic);
        return this;
    }

    @Override
    public UserEssence setStatusEssence(StatusEssence statusEssence) {
        return (UserEssence) super.setStatusEssence(statusEssence);
    }

    public Map<UUID, StateFriend> getRequestedFriendsFrom() {
        return this.requestedFriendsFrom;
    }

    public void setRequestedFriendsFrom(Map<UUID, StateFriend> requestedFriendsFrom) {
        this.requestedFriendsFrom = requestedFriendsFrom;
    }

    public Map<UUID, StateFriend> getRequestedFriendsTo() {
        return this.requestedFriendsTo;
    }

    public void setRequestedFriendsTo(Map<UUID, StateFriend> requestedFriendsTo) {
        this.requestedFriendsTo = requestedFriendsTo;
    }

    public Set<Pet> getPets() {
        return Collections.unmodifiableSet(this.pets);
    }

    public void setPet(Pet pet) {
        pet.setOwner(this.getUserEssenceId());
        this.pets.add(pet);
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Pet> getFollowPets() {
        return this.followPets;
    }

    public void setFollowPets(Set<Pet> followPets) {
        this.followPets = followPets;
    }

    public Set<Message> getSentMessages() {
        return this.sentMessages;
    }

    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Set<Message> getInboxMessages() {
        return this.inboxMessages;
    }

    public void setInboxMessages(Set<Message> inboxMessages) {
        this.inboxMessages = inboxMessages;
    }

    public LocalDateTime getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEssence)) return false;
        UserEssence userEssence = (UserEssence) o;
        return Objects.equals(getUserEssenceId(), userEssence.getUserEssenceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserEssenceId());
    }
}
