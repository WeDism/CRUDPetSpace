package com.pets_space.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Pet {
    private UUID petId = UUID.randomUUID();
    private String name;
    private double weight;
    private LocalDateTime birthday;
    private UUID owner;
    private SpeciesPet species;

    public Pet() {
    }

    public UUID getPetId() {
        return this.petId;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public SpeciesPet getSpecies() {
        return this.species;
    }

    public LocalDateTime getBirthday() {
        return this.birthday;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setSpecies(SpeciesPet species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(this.getPetId(), pet.getPetId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPetId());
    }
}
