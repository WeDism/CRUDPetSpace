package com.pets_space.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserEntry {
    public enum StatusEntry {
        ACTIVE, INACTIVE, DELETED
    }

    public enum Role {
        ROOT, ADMIN, USER
    }

    private UUID userEntryId;
    private String nickname;
    private String name;
    private String surname;
    private String patronymic;
    private String password;
    private List<Byte> phone;
    private String email;
    private Role role;
    private StatusEntry statusEntry;
    private List<UserEntry> friends;
    private List<Pet> pets;
    private Set<Pet> followPets;
    private List<Message> sentMessages;
    private List<Message> inboxMessages;
    private LocalDateTime birthday;

    public UUID getUserEntryId() {
        return this.userEntryId;
    }

    public void setUserEntryId(UUID userEntryId) {
        this.userEntryId = userEntryId;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public StatusEntry getStatusEntry() {
        return this.statusEntry;
    }

    public void setStatusEntry(StatusEntry statusEntry) {
        this.statusEntry = statusEntry;
    }

    public List<UserEntry> getFriends() {
        return this.friends;
    }

    public void setFriends(List<UserEntry> friends) {
        this.friends = friends;
    }

    public List<Pet> getPets() {
        return this.pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Set<Pet> getFollowPets() {
        return this.followPets;
    }

    public void setFollowPets(Set<Pet> followPets) {
        this.followPets = followPets;
    }

    public List<Message> getSentMessages() {
        return this.sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getInboxMessages() {
        return this.inboxMessages;
    }

    public void setInboxMessages(List<Message> inboxMessages) {
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
        if (!(o instanceof UserEntry)) return false;
        UserEntry userEntry = (UserEntry) o;
        return Objects.equals(getUserEntryId(), userEntry.getUserEntryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserEntryId());
    }
}
