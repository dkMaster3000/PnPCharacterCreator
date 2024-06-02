package org.example.mainframe;

import org.example.models.Race;

import javax.swing.*;
import java.util.Objects;

public class RaceComboBox extends JComboBox<String> {

    private final UpdatePanels updatePanels;

    RaceComboBox(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;
    }

    public void updateRaceComboBox() {
        String[] raceNames = MainFrame.races.stream().map(Race::getName).toArray(String[]::new);
        DefaultComboBoxModel<String> raceNamesModel = new DefaultComboBoxModel<>(raceNames);
        this.setModel(raceNamesModel);
        setSelectedIndex(0);

        addActionListener(e -> updateRaceOfCharacter());

        updateRaceOfCharacter();
    }

    private void updateRaceOfCharacter() {
        for (Race race : MainFrame.races) {
            if (Objects.equals(race.getName(), getSelectedItem())) {
                MainFrame.character.setRace(race);
            }
        }
        updatePanels.updatePanels();
    }
}
