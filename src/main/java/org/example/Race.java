package org.example;

import java.util.List;

public class Race {

    private String name;
    private String height;
    private String weight;
    private String movement;
    private List<String> buffs;
    private List<String> debuffs;
    private List<List<String>> choices;

    public Race(String name) {
        this.name = name;
    }

    public Race(String name, String height, String weight, String movement, List<String> buffs, List<String> debuffs, List<List<String>> choices) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.movement = movement;
        this.buffs = buffs;
        this.debuffs = debuffs;
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public List<String> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<String> buffs) {
        this.buffs = buffs;
    }

    public List<String> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(List<String> debuffs) {
        this.debuffs = debuffs;
    }

    public List<List<String>> getChoices() {
        return choices;
    }

    public void setChoices(List<List<String>> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Race{" +
                "name='" + name + '\'' +
                '}';
    }
}
