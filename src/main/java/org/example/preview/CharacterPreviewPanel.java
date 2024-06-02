package org.example.preview;

import org.example.models.Character;

import javax.swing.*;
import java.awt.*;

public class CharacterPreviewPanel extends JPanel {

    private Character characterToDisplay;
    private RacePanel racePanel;

    public CharacterPreviewPanel() {
        super(true);
        this.characterToDisplay = characterToDisplay;

        setLayout(new GridLayout(0, 1, 10, 10));

        racePanel = new RacePanel();
        add(racePanel);

        setBackground(Color.ORANGE);

        validate();
    }

    public void updateCharacter(Character newCharacter) {
        characterToDisplay = newCharacter;

        racePanel.updateRace(characterToDisplay);

        repaint();
    }

}
