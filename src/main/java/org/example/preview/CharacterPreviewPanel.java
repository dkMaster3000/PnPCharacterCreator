package org.example.preview;

import org.example.mainframe.MainFrame;
import org.example.models.*;
import org.example.models.Character;

import javax.swing.*;
import java.awt.*;
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

        race = character.getRace();

        DisplayNewValues();
    }

    private void DisplayNewValues() {
        removeAll();

        addSpace();

        addJLabel("Rasse: " + race.getName());

        addJLabel("Größe: " + race.getHeight());

        addJLabel("Gewicht: " + race.getWeight());

        int totalHP = character.getAddedHP() + Integer.parseInt(race.getHp());
        addJLabel("HP: " + totalHP);

        addJLabel("Stärke: " + character.getStrength());

        addJLabel("Intelligenz: " + character.getIntelligence());

        addJLabel("Geschick: " + character.getDexterity());

        addJLabel("Bewegungsreichweite: " + race.getMovement());

        List<String> allBuffs = character.getAllBuffs();
        if (!allBuffs.isEmpty()) {
            addSectionSeparator();

            addJLabel("Rassen Buffs:");

            for (String buff : allBuffs) {
                addJLabel(buff);
            }
        }

        if (!race.getDebuffs().isEmpty()) {
            addSectionSeparator();

            addJLabel("Rassen Debuffs:");

            for (String debuff : race.getDebuffs()) {
                addJLabel(debuff);
            }
        }

        if (!character.getTalents().isEmpty()) {
            addSectionSeparator();

            addJLabel("Talente:");

            addSpace();

            String[][] data = character.getTalents().stream()
                    .map(talent -> new String[]{talent.name(), talent.description()})
                    .toArray(String[][]::new);

            String[] columnNames = new String[]{"Name", "Effect"};

            addTable(data, columnNames);
        }

        List<Spell> allCharacterSpells = character.getAllCharacterSpells();
        if (!allCharacterSpells.isEmpty()) {
            addSectionSeparator();

            addJLabel("Zauber:");

            addSpace();

            String[][] data = allCharacterSpells.stream()
                    .map(spell -> new String[]{spell.getName(), spell.getTempo(), spell.getDifficulty(), spell.getEffect(), spell.getRange()})
                    .toArray(String[][]::new);

            String[] columnNames = new String[]{"Name", "Tempo", "Art", "Effect", "Reichweite"};

            addTable(data, columnNames);

        }


        List<Passiv> allCharacterPassivs = character.getAllCharacterPassivs();
        if (!allCharacterSpells.isEmpty()) {
            addSectionSeparator();

            addJLabel("Passivs:");

            addSpace();

            String[][] data = allCharacterPassivs.stream()
                    .map(passiv -> new String[]{passiv.getName(), passiv.getEffect(), passiv.getRange()})
                    .toArray(String[][]::new);

            String[] columnNames = new String[]{"Name", "Effect", "Reichweite"};

            addTable(data, columnNames);
        }


        revalidate();
    }

    public void addJLabel(String string) {
        JLabel newJLabel = new JLabel(string);
        add(newJLabel);
    }

    public void addTable(String[][] data, String[] columnNames) {
        JTable newTable = new JTable(data, columnNames);

        JScrollPane newScrollPane = new JScrollPane(newTable);
        newScrollPane.setMaximumSize(new Dimension(
                1000,
                newTable.getRowHeight() * (data.length + 2)
        ));
        add(newScrollPane);
    }

    public void addSectionSeparator() {
        addSpace();
        addSeparator();
        addSpace();
    }

    public void addSeparator() {
        JSeparator newSeparator = new JSeparator();
        newSeparator.setOrientation(SwingConstants.HORIZONTAL);
        add(newSeparator);
    }

    public void addSpace() {
        add(Box.createRigidArea(new Dimension(5, 10)));
    }
}
