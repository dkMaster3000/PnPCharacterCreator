package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class RPGClassLevel {

    int lvl;
    private final List<Spell> spellList = new ArrayList<>();
    private final List<Passiv> passivList = new ArrayList<>();
    private final List<List<RPGClassChoosable>> choosables = new ArrayList<>();

    public RPGClassLevel(int lvl) {
        this.lvl = lvl;
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public List<Passiv> getPassivList() {
        return passivList;
    }

    public List<List<RPGClassChoosable>> getChoosables() {
        return choosables;
    }

    @Override
    public String toString() {
        return "\nlvl=" + lvl +
                ", \nlspellList=" + spellList +
                ", \nlpassivList=" + passivList +
                ", \nlchoosables=" + choosables;
    }
}
