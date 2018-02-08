package com.pets_space.storages;

import com.pets_space.models.SpeciesPet;

public interface SpeciesPetStorageTestData {
    static SpeciesPet getSpeciesPetCat() {
        return new SpeciesPet("cat");
    }

    static SpeciesPet getSpeciesPetDog() {
        return new SpeciesPet("dog");
    }

    static SpeciesPet getSpeciesPetEagle() {
        return new SpeciesPet("eagle");
    }

}
