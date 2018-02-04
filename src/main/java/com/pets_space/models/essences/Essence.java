package com.pets_space.models.essences;

import java.util.UUID;

public abstract class Essence implements IEssence {
    protected UUID userEssenceId;
    protected String nickname;
    protected String name;
    protected String surname;
    protected String patronymic;
    protected Role role;
    protected StatusEssence statusEssence;

    public UUID getUserEssenceId() {
        return this.userEssenceId;
    }

    public Essence setUserEssenceId(UUID userEssenceId) {
        this.userEssenceId = userEssenceId;
        return this;
    }

    public String getNickname() {
        return this.nickname;
    }

    public Essence setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Essence setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return this.surname;
    }

    public Essence setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public Essence setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public Role getRole() {
        return this.role;
    }

    public Essence setRole(Role role) {
        this.role = role;
        return this;
    }

    public Essence setStatusEssence(StatusEssence statusEssence) {
        this.statusEssence = statusEssence;
        return this;
    }

    public StatusEssence getStatusEssence() {
        return this.statusEssence;
    }
}
