package org.example;

import javax.swing.*;
import java.awt.*;

public class CharacterPreviewPanel extends JPanel {

    private Character characterToDisplay;
    private RacePanel racePanel;

    CharacterPreviewPanel(Character characterToDisplay) {
        super(true);
        this.characterToDisplay = characterToDisplay;

        setLayout(new GridLayout(0, 1, 10, 10));

        racePanel = new RacePanel(characterToDisplay.getRace());
        add(racePanel);

        setBackground(Color.ORANGE);

        validate();
    }

    public void updateCharacter(Character newCharacter) {
        characterToDisplay = newCharacter;

        updateRacePanel(characterToDisplay.getRace());

        repaint();
    }

    private void updateRacePanel(Race newRace) {
        racePanel.updateRace(newRace);
    }

}
