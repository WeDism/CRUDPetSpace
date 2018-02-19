package com.pets_space.storages;

import org.junit.Test;

import static com.pets_space.storages.GenusPetStorageTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GenusPetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(GenusPetStorage.getInstance());
    }

    @Test
    public void add() {
        assertTrue(this.genusPetStorage.add(getGenusPetCat()));
    }

    @Test
    public void addDoubleSpices() {
        assertTrue(this.genusPetStorage.add(getGenusPetCat()));
        assertFalse(this.genusPetStorage.add(getGenusPetCat()));
    }

    @Test
    public void getAll() {
        assertTrue(this.genusPetStorage.add(getGenusPetCat()));
        assertTrue(this.genusPetStorage.add(getGenusPetDog()));
        assertTrue(this.genusPetStorage.add(getGenusPetEagle()));

        assertThat(3, is(this.genusPetStorage.getAll().size()));
    }

    @Test
    public void validateGenus() {
        assertTrue(this.genusPetStorage.add(getGenusPetCat()));
        assertTrue(this.genusPetStorage.validateGenus(getGenusPetCat()));
    }
}