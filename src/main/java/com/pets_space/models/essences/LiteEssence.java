package com.pets_space.models.essences;

import java.util.UUID;

public class LiteEssence extends Essence {
    @Override
    public LiteEssence setUserEssenceId(UUID userEssenceId) {
        this.userEssenceId = userEssenceId;
        return this;
    }

    @Override
    public LiteEssence setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Override
    public LiteEssence setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public LiteEssence setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public LiteEssence setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    @Override
    public LiteEssence setRole(Role role) {
        this.role = role;
        return this;
    }
}
