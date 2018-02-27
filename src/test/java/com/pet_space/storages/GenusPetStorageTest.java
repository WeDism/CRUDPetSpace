package com.pet_space.storages;

import org.junit.Test;

import static com.pet_space.storages.GenusPetStorageTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GenusPetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(GenusPetStorage.getInstance());
    }

    @Test
    public void add() {
        assertTrue(this.genus.add(getGenusPetCat()));
    }

    @Test
    public void addDoubleSpices() {
        assertTrue(this.genus.add(getGenusPetCat()));
        assertFalse(this.genus.add(getGenusPetCat()));
    }

    @Test
    public void getAll() {
        assertTrue(this.genus.add(getGenusPetCat()));
        assertTrue(this.genus.add(getGenusPetDog()));
        assertTrue(this.genus.add(getGenusPetEagle()));

        assertThat(3, is(this.genus.getAll().size()));
    }

    @Test
    public void validateGenus() {
        assertTrue(this.genus.add(getGenusPetCat()));
        assertTrue(this.genus.validateGenus(getGenusPetCat()));
    }
}