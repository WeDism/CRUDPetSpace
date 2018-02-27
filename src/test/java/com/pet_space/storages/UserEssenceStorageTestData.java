package com.pet_space.storages;

import com.pet_space.models.essences.Role;
import com.pet_space.models.essences.StatusEssence;
import com.pet_space.models.essences.UserEssence;

import java.util.UUID;

public interface UserEssenceStorageTestData {
    static UserEssence getUserEssenceSteven() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User")
                .name("Steven")
                .surname("Signal")
                .role(Role.ADMIN)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email@email")
                .password("password")
                .patronymic("son of William")
                .build();
    }

    static UserEssence getUserEssenceAndrey() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User1")
                .name("Andrey")
                .surname("Richardson")
                .role(Role.ADMIN)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email@email")
                .password("password")
                .patronymic("son of Gerald")
                .build();
    }

    static UserEssence getUserEssenceRichard() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User2")
                .name("Richard")
                .surname("Wilson")
                .role(Role.ROOT)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email1@email1")
                .password("password")
                .build();
    }
}
