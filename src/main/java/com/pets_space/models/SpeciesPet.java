package com.pets_space.models;

import java.util.Objects;

public class SpeciesPet {
    private String name;

    public SpeciesPet(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpeciesPet)) return false;
        SpeciesPet species = (SpeciesPet) o;
        return Objects.equals(getName(), species.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
