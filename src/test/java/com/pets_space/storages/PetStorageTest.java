package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.SpeciesPet;
import com.pets_space.models.essences.UserEssence;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Set;

import static com.pets_space.storages.PetStorageTestData.*;
import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceRichard;
import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceSteven;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(PetStorage.getInstance());
    }

    @Test
    public void addWithOwnerAndSpeciesPet() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.speciesPetStorage.add(new SpeciesPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
    }

    @Test
    public void addWithSpeciesPet() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.speciesPetStorage.add(new SpeciesPet("cat")));
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
        assertTrue(this.speciesPetStorage.add(new SpeciesPet("cat")));
        Pet pet = getPetTimon(userEssenceSteven.getUserEssenceId());
        assertTrue(this.petStorage.add(pet));
        assertTrue(this.petStorage.delete(pet));
    }

    @Test
    public void deleteCollection() {
    }

    @Test
    public void addAll() {

    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceRichard = getUserEssenceRichard();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.speciesPetStorage.add(new SpeciesPet("cat")));
        assertTrue(this.speciesPetStorage.add(new SpeciesPet("dog")));

        Pet petLayma = getPetLayma(userEssenceSteven.getUserEssenceId());
        Pet petPers = getPetPers(userEssenceSteven.getUserEssenceId());
        Pet petTimon = getPetTimon(userEssenceRichard.getUserEssenceId());

        Set<Pet> pets = Set.of(petLayma, petPers, petTimon);
        assertTrue(this.petStorage.addAll(pets));
        assertThat(pets, is(this.petStorage.getAll()));
    }

    @Test
    public void findById() {
    }

    @Test
    public void getPetsOfOwner() {
    }

    @Test
    public void getPetsOfOwner1() {
    }
}