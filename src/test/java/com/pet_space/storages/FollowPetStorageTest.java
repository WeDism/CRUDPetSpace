package com.pet_space.storages;

import com.pet_space.models.Pet;
import com.pet_space.models.essences.UserEssence;
import org.junit.Test;

import java.util.Set;

import static com.pet_space.storages.GenusPetStorageTestData.getGenusPetCat;
import static com.pet_space.storages.GenusPetStorageTestData.getGenusPetDog;
import static com.pet_space.storages.PetStorageTestData.getPetLayma;
import static com.pet_space.storages.PetStorageTestData.getPetPers;
import static com.pet_space.storages.UserEssenceStorageTestData.getUserEssenceRichard;
import static com.pet_space.storages.UserEssenceStorageTestData.getUserEssenceSteven;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FollowPetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(FollowPetStorage.getInstance());
    }

    @Test
    public void add() {
        UserEssence userEssenceRichard = getUserEssenceRichard();
        UserEssence userEssenceSteven = getUserEssenceSteven();
        Pet petPers = getPetPers(userEssenceRichard.getUserEssenceId());

        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(getGenusPetCat()));
        assertTrue(this.petStorage.add(petPers));
        assertTrue(this.followPetStorage.add(userEssenceSteven.getUserEssenceId(), petPers.getPetId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addItsFollowPet() {
        UserEssence userEssenceRichard = getUserEssenceRichard();
        Pet petPers = getPetPers(userEssenceRichard.getUserEssenceId());

        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.genus.add(getGenusPetCat()));
        assertTrue(this.petStorage.add(petPers));
        this.followPetStorage.add(userEssenceRichard.getUserEssenceId(), petPers.getPetId());
    }

    @Test
    public void getFollowPets() {
        UserEssence userEssenceRichard = getUserEssenceRichard();
        UserEssence userEssenceSteven = getUserEssenceSteven();
        Pet petPers = getPetPers(userEssenceRichard.getUserEssenceId());
        Pet petLayma = getPetLayma(userEssenceRichard.getUserEssenceId());

        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.genus.add(getGenusPetCat()));
        assertTrue(this.genus.add(getGenusPetDog()));
        assertTrue(this.petStorage.add(petPers));
        assertTrue(this.petStorage.add(petLayma));
        assertTrue(this.followPetStorage.add(userEssenceSteven.getUserEssenceId(), petPers.getPetId()));
        assertTrue(this.followPetStorage.add(userEssenceSteven.getUserEssenceId(), petLayma.getPetId()));
        assertThat(Set.of(petLayma, petPers), is(this.followPetStorage.getFollowPets(userEssenceSteven.getUserEssenceId())));
    }
}