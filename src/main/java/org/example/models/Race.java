package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private String name;
    private String height = "";
    private String weight = "";
    private String hp = "";
    private int movement = 0;
    private final List<String> buffs = new ArrayList<>();
    private final List<String> debuffs = new ArrayList<>();
    private final List<List<String>> choices = new ArrayList<>();

    public Race(String name) {
        this.name = name;
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

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public List<String> getBuffs() {
        return buffs;
    }

    public void addBuffs(String buff) {
        buffs.add(buff);
    }

    public List<String> getDebuffs() {
        return debuffs;
    }

    public void addDebuffs(String debuff) {
        debuffs.add(debuff);
    }

    public List<List<String>> getChoices() {
        return choices;
    }

    public void addChoices(List<String> choice) {
        choices.add(choice);
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        return "\n\n Race: " + name +
                "\n height=" + height +
                "\n weight=" + weight +
                "\n HP=" + hp +
                "\n movement=" + movement +
                "\n buffs=" + buffs +
                "\n debuffs=" + debuffs +
                "\n choices=" + choices;
    }
}
