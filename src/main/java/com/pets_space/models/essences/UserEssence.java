package com.pets_space.models.essences;

import com.pets_space.models.Message;
import com.pets_space.models.Pet;

import java.time.LocalDateTime;
import java.util.*;

public class UserEssence extends Essence{
    private String password;
    private List<Byte> phone;
    private String email;
    private StatusEssence statusEssence;
    private Map<UUID, StateFriend> requestedFriendsFrom = new HashMap<>();
    private Map<UUID, StateFriend> requestedFriendsTo = new HashMap<>();
    private Set<Pet> pets = new HashSet<>();
    private Set<Pet> followPets;
    private Set<Message> sentMessages;
    private Set<Message> inboxMessages;
    private LocalDateTime birthday;

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
        this.role = role;
        return this;
    }

    public StatusEssence getStatusEssence() {
        return this.statusEssence;
    }

    public void setStatusEssence(StatusEssence statusEssence) {
        this.statusEssence = statusEssence;
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
