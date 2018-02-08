package com.pets_space.models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Pet {
    private UUID petId;
    private String name;
    private Double weight;
    private LocalDateTime birthday;
    private UUID owner;
    private SpeciesPet species;

    private Pet() {
    }

    public static IPetId builder() {
        return new BuilderPet();
    }

    public static class BuilderPet implements IPetId, IName, IOwner, ISpeciesPet, IBuild {
        Pet pet = new Pet();

        @Override
        public IName petId(@NotNull UUID petId) {
            this.pet.setPetId(petId);
            return this;
        }

        @Override
        public IOwner name(@NotNull String name) {
            this.pet.setName(name);
            return this;
        }

        @Override
        public ISpeciesPet owner(@NotNull UUID owner) {
            this.pet.setOwner(owner);
            return this;
        }

        @Override
        public IBuild species(SpeciesPet species) {
            this.pet.setSpecies(species);
            return this;
        }

        @Override
        public Pet build() {
            Set<?> setArguments = Set.of(this.pet.petId, this.pet.name, this.pet.owner, this.pet.species);
            if (setArguments.stream().anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException(
                        String.format("The arguments(%s) are null", setArguments.stream().filter(Objects::isNull).collect(Collectors.toList())));
            }
            return this.pet;
        }

        @Override
        public IBuild weight(Double weight) {
            this.pet.setWeight(weight);
            return this;
        }

        @Override
        public IBuild birthday(LocalDateTime birthday) {
            this.pet.setBirthday(birthday);
            return this;
        }
    }

    public interface IPetId {
        IName petId(@NotNull UUID petId);
    }

    public interface IName {
        IOwner name(@NotNull String name);
    }

    public interface IOwner {
        ISpeciesPet owner(@NotNull UUID owner);
    }

    public interface ISpeciesPet {
        IBuild species(SpeciesPet species);
    }

    public interface IBuild {
        IBuild weight(Double weight);

        IBuild birthday(LocalDateTime birthday);

        Pet build();
    }

    public UUID getPetId() {
        return this.petId;
    }

    public String getName() {
        return this.name;
    }

    public Double getWeight() {
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

    public Pet setPetId(UUID petId) {
        this.petId = petId;
        return this;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public Pet setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Pet setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public Pet setOwner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public Pet setSpecies(SpeciesPet species) {
        this.species = species;
        return this;
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
