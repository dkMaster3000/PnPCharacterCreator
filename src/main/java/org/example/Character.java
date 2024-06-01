package org.example;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private int lvl;
    private int addedHP = 0;
    private int strength = 0;
    private int intelligence = 0;
    private int dexterity = 0;
    private int statPoints = 0;

    private Race race = new Race("");
    private List<String> chosenBuffs = new ArrayList<>();

    public Character() {
        lvl = 1;
        statPoints = calculateStatPoints();
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
        removeChosenBuffs();
    }

    public List<String> getChosenBuffs() {
        return chosenBuffs;
    }

    public void updateChosenBuffs(int choiceNumber, String newChosenBuff) {
        if (!chosenBuffs.isEmpty() && chosenBuffs.size() - 1 >= choiceNumber) {
            chosenBuffs.set(choiceNumber, newChosenBuff);
        } else {
            chosenBuffs.add(choiceNumber, newChosenBuff);
        }
    }

    public void removeChosenBuffs() {
        chosenBuffs = new ArrayList<>();
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;

        setAddedHP(0);
        setDexterity(0);
        setIntelligence(0);
        setStrength(0);

        setStatPoints(calculateStatPoints());
    }

    public int getAddedHP() {
        return addedHP;
    }

    public void setAddedHP(int addedHP) {
        this.addedHP = addedHP;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setChosenBuffs(List<String> chosenBuffs) {
        this.chosenBuffs = chosenBuffs;
    }

    private int calculateStatPoints() {
        return 2 + (lvl * 3);
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }
}
