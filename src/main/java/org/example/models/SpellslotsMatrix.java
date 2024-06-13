package org.example.models;

import java.util.HashMap;

public class SpellslotsMatrix {

    HashMap<Integer, HashMap<String, Integer>> spellslotsMatrix;

    private final String simpleKey;
    private final String advancedkey;
    private final String expertKey;
    private final String legendaryKey;

    private final int simpleDivider;
    private final int advancedDivider;
    private final int expertDivider;
    private final int legendarDivider;

    public SpellslotsMatrix(HashMap<Integer, HashMap<String, Integer>> spellslotsMatrix,
                            String simpleKey, String advancedkey, String expertKey, String legendaryKey,
                            int simpleDivider, int advancedDivider, int expertDivider, int legendarDivider) {
        this.spellslotsMatrix = spellslotsMatrix;
        this.simpleKey = simpleKey;
        this.advancedkey = advancedkey;
        this.expertKey = expertKey;
        this.legendaryKey = legendaryKey;
        this.simpleDivider = simpleDivider;
        this.advancedDivider = advancedDivider;
        this.expertDivider = expertDivider;
        this.legendarDivider = legendarDivider;
    }

    public HashMap<Integer, HashMap<String, Integer>> getSpellslotsMatrix() {
        return spellslotsMatrix;
    }

    public String getSimpleKey() {
        return simpleKey;
    }

    public String getAdvancedkey() {
        return advancedkey;
    }

    public String getLegendaryKey() {
        return legendaryKey;
    }

    public String getExpertKey() {
        return expertKey;
    }

    public int getSimpleDivider() {
        return simpleDivider;
    }

    public int getAdvancedDivider() {
        return advancedDivider;
    }

    public int getExpertDivider() {
        return expertDivider;
    }

    public int getLegendarDivider() {
        return legendarDivider;
    }

    @Override
    public String toString() {
        return "SpellslotsMatrix{" +
                "spellslotsMatrix=" + spellslotsMatrix +
                ", simpleKey='" + simpleKey + '\'' +
                ", advancedkey='" + advancedkey + '\'' +
                ", expertKey='" + expertKey + '\'' +
                ", legendaryKey='" + legendaryKey + '\'' +
                ", simpleDivider=" + simpleDivider +
                ", advancedDivider=" + advancedDivider +
                ", expertDivider=" + expertDivider +
                ", legendarDivider=" + legendarDivider +
                '}';
    }
}
