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
        int talentsAmount = 0;
        for (Integer unlockLvl : unlockLvls) {
            if (unlockLvl <= MainFrame.character.getLvl()) {
                talentsAmount++;
            }
        }
        return talentsAmount;
    }

    public Talent getTalentByName(String talentName) {
        for (Talent talent : talents) {
            if (Objects.equals(talent.getName(), talentName)) {
                return talent;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "TalentMatrix{" +
                "talents=" + talents +
                ", unlockLvls=" + unlockLvls +
                '}';
    }
}
