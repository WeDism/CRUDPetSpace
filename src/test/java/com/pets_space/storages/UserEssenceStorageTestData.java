package com.pets_space.storages;

import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.StatusEssence;
import com.pets_space.models.essences.UserEssence;

import java.util.UUID;

public interface UserEssenceStorageTestData {
    static UserEssence getUseerEssence() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User")
                .name("Steven")
                .surname("Signal")
                .role(Role.ADMIN)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email@email")
                .password("password")
                .patronymic("Padre")
                .build();
    }

    static UserEssence getUseerEssence1() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User1")
                .name("Andrey")
                .surname("Portnov")
                .role(Role.ADMIN)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email@email")
                .password("password")
                .patronymic("Padre")
                .build();
    }

    static UserEssence getUseerEssence2() {
        return UserEssence.builder()
                .userEssenceId(UUID.randomUUID())
                .nickname("Test User2")
                .name("Dmitry")
                .surname("Bykov")
                .role(Role.ROOT)
                .statusEssence(StatusEssence.ACTIVE)
                .email("email1@email1")
                .password("password")
                .build();
    }
}
