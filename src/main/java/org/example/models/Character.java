package org.example.models;

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

    public enum STATNAMES {
        HP,
        STRENGTH,
        INTELLIGENCE,
        DEXTERITY
    }

    public interface SetStat {

        void setStat(int stat);
    }

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

    public void modifyAddedHPValue(int modifyValue) {
        setAddedHP(modifyStatValue(addedHP, modifyValue));
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void modifyStrengthValue(int modifyValue) {
        setStrength(modifyStatValue(strength, modifyValue));
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void modifyIntelligenceValue(int modifyValue) {
        setIntelligence(modifyStatValue(intelligence, modifyValue));
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void modifyDexterityValue(int modifyValue) {
        setDexterity(modifyStatValue(dexterity, modifyValue));
    }

    private int modifyStatValue(int statToModify, int modifyValue) {

        if (modifyValue > 0) {
            if (statPoints > 0) {
                statToModify += modifyValue;
                statPoints -= 1;
            }
        }

        if (modifyValue < 0) {
            if (statToModify > 0) {
                statToModify += modifyValue;
                statPoints += 1;
            }
        }

        return statToModify;
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }

    private int calculateStatPoints() {
        return 2 + (lvl * 3);
    }

    public String getStringToEnum(STATNAMES statname) {
        return switch (statname) {
            case STATNAMES.HP -> "HP";
            case STATNAMES.STRENGTH -> "Stärke";
            case STATNAMES.DEXTERITY -> "Geschick";
            case STATNAMES.INTELLIGENCE -> "Intelligence";
        };
    }

    public SetStat getSetterByEnum(STATNAMES statname) {
        return switch (statname) {
            case STATNAMES.HP -> this::modifyAddedHPValue;
            case STATNAMES.STRENGTH -> this::modifyStrengthValue;
            case STATNAMES.DEXTERITY -> this::modifyDexterityValue;
            case STATNAMES.INTELLIGENCE -> this::modifyIntelligenceValue;
        };
    }

    public int getModifyValueByEnum(STATNAMES statname) {
        return switch (statname) {
            case STATNAMES.HP -> 10;
            case STATNAMES.STRENGTH, STATNAMES.DEXTERITY, STATNAMES.INTELLIGENCE -> 1;
        };
    }
}
