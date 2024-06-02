package org.example.models;

import java.util.List;

public class TalentMatrix {

    List<Talent> talents;
    List<Integer> unlockLvls;

    public TalentMatrix(List<Talent> talents, List<Integer> unlockLvls) {
        this.talents = talents;
        this.unlockLvls = unlockLvls;
    }

    @Override
    public String toString() {
        return "TalentMatrix{" +
                "talents=" + talents +
                ", unlockLvls=" + unlockLvls +
                '}';
    }
}
