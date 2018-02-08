package com.pets_space.storages;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FollowPetStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(FollowPetStorage.getInstance());
    }

    @Test
    public void add() {

    }

    @Test
    public void getFollowPets() {
    }
}