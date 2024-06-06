package org.example.models;

import org.example.mainframe.MainFrame;

import java.util.List;
import java.util.Objects;

public class TalentMatrix {

    List<Talent> talents;
    List<Integer> unlockLvls;

    public TalentMatrix(List<Talent> talents, List<Integer> unlockLvls) {
        this.talents = talents;
        this.unlockLvls = unlockLvls;
    }

    public List<Talent> getTalents() {
        return talents;
    }

    public int calculateTalents() {
        return calculateTalents(MainFrame.character.getLvl());
    }

    public int calculateTalents(int characterLvl) {
        int talentsAmount = 0;
        for (Integer unlockLvl : unlockLvls) {
            if (unlockLvl <= characterLvl) {
                talentsAmount++;
            }
        }
        return talentsAmount;
    }

    public Talent getTalentByName(String talentName) {
        for (Talent talent : talents) {
            if (Objects.equals(talent.name(), talentName)) {
                return talent;
            }
        }
        return null;
    }
}
