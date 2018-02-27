package com.pet_space.models.essences;

import com.pet_space.models.Message;
import com.pet_space.models.Pet;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserEssence extends Essence {
    private String password;
    private List<Byte> phone = new ArrayList<>();
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

        private BuilderUserEssence() {
        }

        @Override
        public INickname userEssenceId(@NotNull UUID userEssenceId) {
            this.userEssence.setUserEssenceId(userEssenceId);
            return this;
        }

        @Override
        public IName nickname(@NotNull String nickname) {
            this.userEssence.setNickname(nickname);
            return this;
        }

        @Override
        public ISurname name(@NotNull String name) {
            this.userEssence.setName(name);
            return this;
        }

        @Override
        public IRole surname(@NotNull String surname) {
            this.userEssence.setSurname(surname);
            return this;
        }

        @Override
        public IStatusEssence role(@NotNull Role role) {
            this.userEssence.setRole(role);
            return this;
        }

        @Override
        public IEmail statusEssence(@NotNull StatusEssence statusEssence) {
            this.userEssence.setStatusEssence(statusEssence);
            return this;
        }

        @Override
        public IPassword email(@NotNull String email) {
            this.userEssence.setEmail(email);
            return this;
        }

        @Override
        public IBuild password(@NotNull String password) {
            this.userEssence.setPassword(password);
            return this;
        }

        public UserEssence build() {
            List<?> setArguments = Arrays.asList(this.userEssence.userEssenceId, this.userEssence.nickname,
                    this.userEssence.name, this.userEssence.surname, this.userEssence.role,
                    this.userEssence.statusEssence, this.userEssence.email, this.userEssence.password);
            if (setArguments.stream().anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException(
                        String.format("The arguments(%s) are null", setArguments.stream().filter(Objects::isNull).collect(Collectors.toList())));
            }
            return this.userEssence;
        }

        public BuilderUserEssence patronymic(String patronymic) {
            this.userEssence.setPatronymic(patronymic);
            return this;
        }

        public BuilderUserEssence phone(List<Byte> phone) {
            this.userEssence.setPhone(phone);
            return this;
        }

        public BuilderUserEssence birthday(LocalDateTime birthday) {
            this.userEssence.setBirthday(birthday);
            return this;
        }

        public BuilderUserEssence inboxMessages(Set<Message> inboxMessages) {
            this.userEssence.setInboxMessages(inboxMessages);
            return this;
        }

        public BuilderUserEssence sentMessages(Set<Message> sentMessages) {
            this.userEssence.setSentMessages(sentMessages);
            return this;
        }

        public BuilderUserEssence followPets(Set<Pet> followPets) {
            this.userEssence.setFollowPets(followPets);
            return this;
        }

        public BuilderUserEssence pets(Set<Pet> pets) {
            this.userEssence.setPets(pets);
            return this;
        }

        public BuilderUserEssence requestedFriendsTo(Map<UUID, StateFriend> requestedFriendsTo) {
            this.userEssence.setRequestedFriendsTo(requestedFriendsTo);
            return this;
        }

        public BuilderUserEssence requestedFriendsFrom(Map<UUID, StateFriend> requestedFriendsFrom) {
            this.userEssence.setRequestedFriendsFrom(requestedFriendsFrom);
            return this;
        }

        @Override
        public IBuild aboutOfSelf(String text) {
            this.userEssence.setAboutOfSelf(text);
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
        IBuild patronymic(String patronymic);

        IBuild phone(List<Byte> phone);

        IBuild birthday(LocalDateTime birthday);

        IBuild inboxMessages(Set<Message> inboxMessages);

        IBuild sentMessages(Set<Message> sentMessages);

        IBuild followPets(Set<Pet> followPets);

        IBuild pets(Set<Pet> pets);

        IBuild requestedFriendsTo(Map<UUID, StateFriend> requestedFriendsTo);

        IBuild requestedFriendsFrom(Map<UUID, StateFriend> requestedFriendsFrom);

        IBuild aboutOfSelf(String text);

        UserEssence build();
    }

    public String getPassword() {
        return this.password;
    }

    public UserEssence setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Byte> getPhone() {
        return this.phone;
    }

    public UserEssence setPhone(List<Byte> phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public UserEssence setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserEssence setRole(Role role) {
        return (UserEssence) super.setRole(role);
    }

    @Override
    public UserEssence setPatronymic(String patronymic) {
        return (UserEssence) super.setPatronymic(patronymic);
    }

    @Override
    public UserEssence setStatusEssence(StatusEssence statusEssence) {
        return (UserEssence) super.setStatusEssence(statusEssence);
    }

    @Override
    public UserEssence setUserEssenceId(UUID userEssenceId) {
        return (UserEssence) super.setUserEssenceId(userEssenceId);
    }

    @Override
    public UserEssence setAboutOfSelf(String aboutOfSelf) {
        return (UserEssence) super.setAboutOfSelf(aboutOfSelf);
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
