package com.pets_space.storages;

import org.junit.Test;

import static com.pets_space.storages.SpeciesPetStorageTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpeciesPetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(SpeciesPetStorage.getInstance());
    }

    @Test
    public void add() {
        assertTrue(this.speciesPetStorage.add(getSpeciesPetCat()));
    }

    @Test
    public void addDoubleSpices() {
        assertTrue(this.speciesPetStorage.add(getSpeciesPetCat()));
        assertFalse(this.speciesPetStorage.add(getSpeciesPetCat()));
    }

    @Test
    public void getAll() {
        assertTrue(this.speciesPetStorage.add(getSpeciesPetCat()));
        assertTrue(this.speciesPetStorage.add(getSpeciesPetDog()));
        assertTrue(this.speciesPetStorage.add(getSpeciesPetEagle()));

        assertThat(3, is(this.speciesPetStorage.getAll().size()));
    }

    @Test
    public void validateSpecies() {
        assertTrue(this.speciesPetStorage.add(getSpeciesPetCat()));
        assertTrue(this.speciesPetStorage.validateSpecies(getSpeciesPetCat()));
    }
}