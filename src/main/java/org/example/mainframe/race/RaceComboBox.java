package org.example.mainframe.race;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.Race;

import javax.swing.*;

import java.util.Objects;

public class RaceComboBox extends JComboBox<String> {

    RaceComboBox(UpdatePanels updatePanels) {

        String[] raceNames = MainFrame.races.stream().map(Race::getName).toArray(String[]::new);
        DefaultComboBoxModel<String> raceNamesModel = new DefaultComboBoxModel<>(raceNames);
        this.setModel(raceNamesModel);
        setSelectedIndex(0);

        addActionListener(_ -> {
            //modify character
            updateRaceOfCharacter();
            //tell MainFrame to update the panels
            updatePanels.updatePanels();
        });

        updateRaceOfCharacter();
    }


    private void updateRaceOfCharacter() {
        for (Race race : MainFrame.races) {
            if (Objects.equals(race.getName(), getSelectedItem())) {
                MainFrame.character.setRace(race);
            }
        }
    }
}
