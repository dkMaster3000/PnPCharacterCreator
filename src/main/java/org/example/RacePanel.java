package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.List;

public class RacePanel extends JPanel {

    private Character character;
    private Race race;


    RacePanel() {
        super(true);
        this.character = MainFrame.character;
        this.race = character.getRace();

        setLayout(new GridLayout(0, 1, 10, 10));
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

        JLabel characterHP = new JLabel();
        int totalHP = character.getAddedHP() + Integer.parseInt(race.getHp());
        characterHP.setText("HP: " + totalHP);
        add(characterHP);

        JLabel characterStrength = new JLabel();
        characterStrength.setText("Stärke: " + character.getStrength());
        add(characterStrength);

        JLabel characterIntelligence = new JLabel();
        characterIntelligence.setText("Intelligenz: " + character.getIntelligence());
        add(characterIntelligence);

        JLabel characterDex = new JLabel();
        characterDex.setText("Geschick: " + character.getDexterity());
        add(characterDex);

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
