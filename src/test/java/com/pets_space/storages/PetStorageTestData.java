package com.pets_space.storages;

import com.pets_space.models.Pet;
import com.pets_space.models.SpeciesPet;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PetStorageTestData {
    static Pet getPetTimon(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Timon")
                .owner(owner)
                .species(new SpeciesPet("cat"))
                .weight(5d)
                .build();
    }

    static Pet getPetPers(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Pers")
                .owner(owner)
                .species(new SpeciesPet("cat"))
                .weight(5d)
                .birthday(LocalDateTime.now())
                .build();
    }

    static Pet getPetLayma(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Layma")
                .owner(owner)
                .species(new SpeciesPet("dog"))
                .build();
    }
}
