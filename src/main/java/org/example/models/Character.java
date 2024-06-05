package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Character {

    private int lvl;
    private int addedHP = 0;
    private int strength = 0;
    private int intelligence = 0;
    private int dexterity = 0;
    private int statPoints = 0;

    private Race race = null;
    private List<String> chosenRaceBuffs = new ArrayList<>();

    private RPGClass rpgClass = new RPGClass("");
    private List<Spell> chosenSpells = new ArrayList<>();
    private List<Passiv> chosenPassivs = new ArrayList<>();

    private List<Talent> talents = new ArrayList<>();

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
        lvl = 0;
        statPoints = calculateStatPoints();
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

        talents = new ArrayList<>();

        removeChoseSpells();
        removeChosenPassivs();
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

    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }

    private int calculateStatPoints() {
        return 2 + (lvl * 3);
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
        chosenRaceBuffs = new ArrayList<>();
    }

    public List<String> getChosenRaceBuffs() {
        return chosenRaceBuffs;
    }

    public void updateChosenRaceBuffs(int choiceNumber, String newChosenBuff) {
        if (!chosenRaceBuffs.isEmpty() && chosenRaceBuffs.size() - 1 >= choiceNumber) {
            chosenRaceBuffs.set(choiceNumber, newChosenBuff);
        } else {
            chosenRaceBuffs.add(choiceNumber, newChosenBuff);
        }
    }

    public List<String> getAllBuffs() {
        return Stream.concat(race.getBuffs().stream(), getChosenRaceBuffs().stream()).toList();
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }

    public void setRpgClass(RPGClass rpgClass) {
        this.rpgClass = rpgClass;
        removeChoseSpells();
        removeChosenPassivs();
    }

    public List<Spell> getChosenSpells() {
        return chosenSpells;
    }

    public void removeChoseSpells() {
        this.chosenSpells = new ArrayList<>();
    }

    public List<Spell> getAllCharacterSpells() {
        List<Spell> spellsFromRPGClassLvl = new ArrayList<>();

        for (RPGClassLevel rpgClassLevel : rpgClass.getClassLvls()) {
            if (rpgClassLevel.getLvl() <= lvl) {
                spellsFromRPGClassLvl = Stream.concat(spellsFromRPGClassLvl.stream(), rpgClassLevel.getSpellList().stream()).toList();
            }
        }

        spellsFromRPGClassLvl = Stream.concat(spellsFromRPGClassLvl.stream(), chosenSpells.stream()).toList();

        return spellsFromRPGClassLvl;
    }

    public List<Passiv> getChosenPassivs() {
        return chosenPassivs;
    }

    public void removeChosenPassivs() {
        this.chosenPassivs = new ArrayList<>();
    }

    public List<Passiv> getAllCharacterPassivs() {
        List<Passiv> passivsFromRPGClassLvl = new ArrayList<>();

        for (RPGClassLevel rpgClassLevel : rpgClass.getClassLvls()) {
            if (rpgClassLevel.getLvl() <= lvl) {
                passivsFromRPGClassLvl = Stream.concat(passivsFromRPGClassLvl.stream(), rpgClassLevel.getPassivList().stream()).toList();
            }
        }

        passivsFromRPGClassLvl = Stream.concat(passivsFromRPGClassLvl.stream(), chosenPassivs.stream()).toList();

        return passivsFromRPGClassLvl;
    }

    public List<Talent> getTalents() {
        return talents;
    }

    public void updateTalents(int choiceNumber, Talent newTalent) {
        if (!talents.isEmpty() && talents.size() - 1 >= choiceNumber) {
            talents.set(choiceNumber, newTalent);
        } else {
            talents.add(choiceNumber, newTalent);
        }
    }

    //----------------------------- UTIL HELPER ------------------------------------------
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
