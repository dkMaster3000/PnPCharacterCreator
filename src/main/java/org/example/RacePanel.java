package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;
import java.util.List;

public class RacePanel extends JPanel {

    private Character character;
    private Race race;


    RacePanel(Character character) {
        super(true);
        this.character = character;
        this.race = character.getRace();

        setLayout(new GridLayout(0, 1, 10, 10));

        setNewRaceValues();

        setSize(getPreferredSize());
    }

    //parent passes the new race
    //here the data must be updated
    public void updateRace(Character character) {
        this.character = character;
        race = character.getRace();

        setNewRaceValues();
    }

    private void setNewRaceValues() {

        removeAll();

        JLabel raceName = new JLabel();
        raceName.setText("Rasse: " + race.getName());
        add(raceName);

        JLabel raceHeight = new JLabel();
        raceHeight.setText("Größe: " + race.getHeight());
        add(raceHeight);

        JLabel raceWeight = new JLabel();
        raceWeight.setText("Gewicht: " + race.getWeight());
        add(raceWeight);

        JLabel raceHP = new JLabel();
        raceHP.setText("HP: " + race.getHp());
        add(raceHP);

        JLabel raceMovement = new JLabel();
        raceMovement.setText("Bewegungsreichweite: " + race.getMovement());
        add(raceMovement);

        List<String> allBuffs = Stream.concat(race.getBuffs().stream(), character.getChosenBuffs().stream()).toList();

        if (!allBuffs.isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel buffNotifyerLabel = new JLabel("Buffs:");
            add(buffNotifyerLabel);

            for (String buff : allBuffs) {
                JLabel newBuff = new JLabel(buff);
                add(newBuff);
            }
        }

        if (!race.getDebuffs().isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel debuffNotifyerLabel = new JLabel("Debuffs:");
            add(debuffNotifyerLabel);
            for (String debuff : race.getDebuffs()) {
                JLabel newDebuff = new JLabel(debuff);
                add(newDebuff);
            }
        }

        revalidate();
    }
}
