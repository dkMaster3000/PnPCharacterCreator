package org.example.preview;

import org.example.mainframe.MainFrame;
import org.example.models.*;
import org.example.models.Character;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;
import java.util.List;

public class CharacterPreviewPanel extends JPanel {

    private Character character;
    private Race race;

    public CharacterPreviewPanel() {
        super(true);
        this.character = MainFrame.character;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    //parent passes the new race
    //here the data must be updated
    public void UpdateCharacterPreviewPanel() {
        this.character = MainFrame.character;
        if (character.getRace() == null) return;

        race = character.getRace();

        DisplayNewValues();
    }

    private void DisplayNewValues() {
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

        List<String> allBuffs = Stream.concat(race.getBuffs().stream(), character.getChosenRaceBuffs().stream()).toList();

        if (!allBuffs.isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel buffNotifyerLabel = new JLabel("Rassen Buffs:");
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

            JLabel debuffNotifyerLabel = new JLabel("Rassen Debuffs:");
            add(debuffNotifyerLabel);
            for (String debuff : race.getDebuffs()) {
                JLabel newDebuff = new JLabel(debuff);
                add(newDebuff);
            }
        }

        if (!character.getTalents().isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel debuffNotifyerLabel = new JLabel("Talente:");
            add(debuffNotifyerLabel);

            for (Talent talent : character.getTalents()) {
                JLabel newDebuff = new JLabel(talent.getName() + ": " + talent.getDescription());
                add(newDebuff);
            }
        }

        List<Spell> allCharacterSpells = character.getAllCharacterSpells();
        if (!allCharacterSpells.isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel spellNotifyerLabel = new JLabel("Zauber:");
            add(spellNotifyerLabel);

//            String[][] data = allCharacterSpells.stream()
//                    .map(spell -> new String[]{spell.getName(), spell.getTempo(), spell.getDifficulty(), spell.getEffect(), spell.getRange()})
//                    .toArray(String[][]::new);
//
//            String[] columnNames = new String[]{"Name", "Tempo", "Schwierigkeit", "Effect", "Reichweite"};
//
//            JLabel spaceLabel1 = new JLabel(" ");
//            add(spaceLabel1);
//
//            JTable j = new JTable(data, columnNames);
//
//            j.setPreferredScrollableViewportSize(
//                    new Dimension(
//                            j.getPreferredSize().width,
//                            j.getRowHeight() * (data.length + 1)));
//
//            JScrollPane scrollPane = new JScrollPane(j);
//            Dimension d = j.getPreferredSize();
//            scrollPane.setPreferredSize(
//                    new Dimension(d.width, j.getRowHeight() * (data.length + 1)));
//            add(scrollPane);

            for (Spell spell : allCharacterSpells) {
                JLabel newSpell = new JLabel(spell.getName() + " | " +
                        spell.getTempo() + " | " +
                        spell.getDifficulty() + " | " +
                        spell.getEffect() + " | " +
                        spell.getRange());
                add(newSpell);
            }
        }


        List<Passiv> allCharacterPassivs = character.getAllCharacterPassivs();
        if (!allCharacterSpells.isEmpty()) {
            JLabel spaceLabel = new JLabel(" ");
            add(spaceLabel);

            JLabel seperatorLabel = new JLabel("----------------------");
            add(seperatorLabel);

            JLabel passiveNotifyerLabel = new JLabel("Passivs:");
            add(passiveNotifyerLabel);

            for (Passiv passiv : allCharacterPassivs) {
                JLabel newPassiv = new JLabel(passiv.getName() + " | " +
                        passiv.getEffect() + " | " +
                        passiv.getRange());
                add(newPassiv);
            }
        }

        revalidate();
    }


}
