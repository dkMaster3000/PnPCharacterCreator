package org.example.models;

public record Talent(String name, String description, boolean unique) {

    @Override
    public String toString() {
        return "\nTalent{" +
                "\nname='" + name + '\'' +
                ", \ndescription='" + description + '\'' +
                ", \nunique=" + unique +
                '}';
    }
}
