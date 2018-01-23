package com.pets_space.models;

import java.time.LocalDateTime;
import java.util.*;

public class UserEssence {
    public enum StatusEssence {
        ACTIVE, INACTIVE, DELETED
    }

    public enum Role {
        ROOT, ADMIN, USER
    }

    private UUID userEssenceId;
    private String nickname;
    private String name;
    private String surname;
    private String patronymic;
    private String password;
    private List<Byte> phone;
    private String email;
    private Role role;
    private StatusEssence statusEssence;
    private Set<UserEssence> friends;
    private Set<Pet> pets = new HashSet<>();
    private Set<Pet> followPets;
    private Set<Message> sentMessages;
    private Set<Message> inboxMessages;
    private LocalDateTime birthday;

    public UUID getUserEssenceId() {
        return this.userEssenceId;
    }

    public void setUserEssenceId(UUID userEssenceId) {
        this.userEssenceId = userEssenceId;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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

    public Role getRole() {
        return this.role;
    }

    public UserEssence setRole(Role role) {
        this.role = role;
        return this;
    }

    public StatusEssence getStatusEssence() {
        return this.statusEssence;
    }

    public void setStatusEssence(StatusEssence statusEssence) {
        this.statusEssence = statusEssence;
    }

    public Set<UserEssence> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<UserEssence> friends) {
        this.friends = friends;
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
