package org.example.models;

public class Talent {

    private String name;
    private String description;
    private boolean unique;

    public Talent(String name, String description, boolean unique) {
        this.name = name;
        this.description = description;
        this.unique = unique;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "\nTalent{" +
                "\nname='" + name + '\'' +
                ", \ndescription='" + description + '\'' +
                ", \nunique=" + unique +
                '}';
    }
}
