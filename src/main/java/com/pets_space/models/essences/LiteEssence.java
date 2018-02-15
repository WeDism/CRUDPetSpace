package com.pets_space.models.essences;

import java.util.UUID;

public class LiteEssence extends Essence {
    @Override
    public LiteEssence setUserEssenceId(UUID userEssenceId) {
        return (LiteEssence) super.setUserEssenceId(userEssenceId);
    }

    @Override
    public LiteEssence setNickname(String nickname) {
        return (LiteEssence) super.setNickname(nickname);
    }

    @Override
    public LiteEssence setName(String name) {
        return (LiteEssence) super.setName(name);
    }

    @Override
    public LiteEssence setSurname(String surname) {
        return (LiteEssence) super.setSurname(surname);
    }

    @Override
    public LiteEssence setPatronymic(String patronymic) {
        return (LiteEssence) super.setPatronymic(patronymic);
    }

    @Override
    public LiteEssence setRole(Role role) {
        return (LiteEssence) super.setRole(role);
    }

    @Override
    public LiteEssence setStatusEssence(StatusEssence statusEssence) {
        return (LiteEssence) super.setStatusEssence(statusEssence);
    }

    @Override
    public LiteEssence setAboutOfSelf(String text) {
        return (LiteEssence) super.setAboutOfSelf(text);
    }
}
