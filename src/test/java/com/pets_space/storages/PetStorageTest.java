package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.GenusPet;
import com.pets_space.models.essences.UserEssence;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.pets_space.storages.PetStorageTestData.*;
import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceRichard;
import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceSteven;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(PetStorage.getInstance());
    }

    @Test
    public void addWithOwnerAndGenusPet() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(new GenusPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
    }

    @Test
    public void addWithGenusPet() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.genus.add(new GenusPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertFalse(this.petStorage.add(pet));
    }

    @Test
    public void addWithOwner() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertFalse(this.petStorage.add(pet));
    }

    @Test
    public void delete() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(new GenusPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
        assertTrue(this.petStorage.delete(pet));
    }

    @Test
    public void deleteCollection() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(new GenusPet("cat")));
        assertTrue(this.genus.add(new GenusPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Pet petTimon = getPetTimon(userEssenceRichard.getUserEssenceId());
        Set<Pet> pets = Set.of(petLayma, petPers, petTimon);

        assertTrue(this.petStorage.addAll(pets));
        assertThat(pets, is(this.petStorage.getAll()));
        assertTrue(this.petStorage.delete(pets));
    }

    @Test(expected = IllegalStateException.class)
    public void addAll() {
        UserEssence userEssenceSteven = getUserEssenceSteven();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(new GenusPet("cat")));
        assertTrue(this.genus.add(new GenusPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Set<Pet> pets = Set.of(petLayma, petPers);

        assertTrue(this.petStorage.addAll(pets));
        assertThat(pets, is(this.petStorage.getAll()));
        assertFalse(this.petStorage.addAll(new HashSet<>(Arrays.asList(petLayma, petPers, null))));
    }

    @Test
    public void update() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(new GenusPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
        assertTrue(this.petStorage.update(pet.setName("Lucky Timon")));
        assertTrue(this.petStorage.update(pet
                .setName("Lucky Tim")
                .setOwner(userEssenceRichard.getUserEssenceId())));
    }

    @Test
    public void getAll() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(new GenusPet("cat")));
        assertTrue(this.genus.add(new GenusPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Pet petTimon = getPetTimon(userEssenceRichard.getUserEssenceId());
        Set<Pet> pets = Set.of(petLayma, petPers, petTimon);

        assertTrue(this.petStorage.addAll(pets));
        assertThat(pets, is(this.petStorage.getAll()));
    }

    @Test
    public void findById() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(new GenusPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
        assertTrue(this.petStorage.findById(pet.getPetId()).isPresent());
    }

    @Test
    public void getPetsOfOwner() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(new GenusPet("cat")));
        assertTrue(this.genus.add(new GenusPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Pet petTimon = getPetTimon(userEssenceRichard.getUserEssenceId());

        assertTrue(this.petStorage.addAll(Set.of(petLayma, petPers, petTimon)));
        assertThat(Set.of(petLayma, petPers), is(this.petStorage.getPetsOfOwner(userEssenceSteven.getUserEssenceId())));
        assertThat(Set.of(petTimon), is(this.petStorage.getPetsOfOwner(userEssenceRichard.getUserEssenceId())));
    }

    @Test
    public void getUuidPetsOfOwner() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(new GenusPet("cat")));
        assertTrue(this.genus.add(new GenusPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Pet petTimon = getPetTimon(userEssenceRichard.getUserEssenceId());

        assertTrue(this.petStorage.addAll(Set.of(petLayma, petPers, petTimon)));
        assertThat(Set.of(petLayma.getPetId(), petPers.getPetId()), is(this.petStorage.getUuidPetsOfOwner(userEssenceSteven.getUserEssenceId())));
        assertThat(Set.of(petTimon.getPetId()), is(this.petStorage.getUuidPetsOfOwner(userEssenceRichard.getUserEssenceId())));
    }
}