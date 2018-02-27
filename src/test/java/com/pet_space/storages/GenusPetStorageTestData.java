package com.pet_space.storages;

import com.pet_space.models.GenusPet;

public interface GenusPetStorageTestData {
    static GenusPet getGenusPetCat() {
        return new GenusPet("cat");
    }

    static GenusPet getGenusPetDog() {
        return new GenusPet("dog");
    }

    static GenusPet getGenusPetEagle() {
        return new GenusPet("eagle");
    }

}
