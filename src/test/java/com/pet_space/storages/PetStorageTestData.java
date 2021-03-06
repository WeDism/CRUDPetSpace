package com.pet_space.storages;

import com.pet_space.models.GenusPet;
import com.pet_space.models.Pet;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PetStorageTestData {
    static Pet getPetTimon(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Timon")
                .owner(owner)
                .genusPet(new GenusPet("cat"))
                .weight(5d)
                .build();
    }

    static Pet getPetPers(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Pers")
                .owner(owner)
                .genusPet(new GenusPet("cat"))
                .weight(5d)
                .birthday(LocalDateTime.now())
                .build();
    }

    static Pet getPetLayma(UUID owner){
        return Pet.builder()
                .petId(UUID.randomUUID())
                .name("Layma")
                .owner(owner)
                .genusPet(new GenusPet("dog"))
                .build();
    }
}
