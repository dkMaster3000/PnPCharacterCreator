package org.example;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private String name;
    private String height = "";
    private String weight = "";
    private String hp = "";
    private String movement = "";
    private List<String> buffs = new ArrayList<>();
    private List<String> debuffs = new ArrayList<>();
    private List<List<String>> choices = new ArrayList<>();

    public Race(String name) {
        this.name = name;
    }

    public Race(String name, String height, String weight, String hp, String movement, List<String> buffs, List<String> debuffs, List<List<String>> choices) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.hp = hp;
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

    public void addBuffs(String buff) {
        buffs.add(buff);
    }

    public List<String> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(List<String> debuffs) {
        this.debuffs = debuffs;
    }

    public void addDebuffs(String debuff) {
        debuffs.add(debuff);
    }

    public List<List<String>> getChoices() {
        return choices;
    }

    public void setChoices(List<List<String>> choices) {
        this.choices = choices;
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
