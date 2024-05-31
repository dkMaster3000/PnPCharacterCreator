package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceComboBox extends JComboBox<String> {

    public interface RaceComboBoxFunc {

        void chooseRace(String raceName);
    }

    RaceComboBox() {
    }

    public void updateRaceComboBox(String[] raceNames, RaceComboBoxFunc raceComboBoxFunc) {
        DefaultComboBoxModel<String> raceNamesModel = new DefaultComboBoxModel<>(raceNames);
        this.setModel(raceNamesModel);
        setSelectedIndex(0);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                raceComboBoxFunc.chooseRace((String) getSelectedItem());
            }
        });

        raceComboBoxFunc.chooseRace((String) getSelectedItem());
    }
}
