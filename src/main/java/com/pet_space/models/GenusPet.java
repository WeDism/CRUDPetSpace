package com.pet_space.models;

import java.util.Objects;

public class GenusPet {
    private String name;

    public GenusPet(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenusPet)) return false;
        GenusPet genusPet = (GenusPet) o;
        return Objects.equals(getName(), genusPet.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
