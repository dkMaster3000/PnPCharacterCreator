package org.example;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private Race race = new Race("");
    private List<String> chosenBuffs = new ArrayList<>();

    public Character() {
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
}
