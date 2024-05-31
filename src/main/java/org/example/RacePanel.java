package org.example;

import javax.swing.*;

public class RacePanel extends JPanel {

    private Race raceToDisplay;
    private JLabel raceName;

    RacePanel(Race raceToDisplay) {
        super(true);
        this.raceToDisplay = raceToDisplay;

        raceName = new JLabel("Rasse: " + raceToDisplay.getName());
        raceName.setSize(raceName.getPreferredSize());
        raceName.setLocation(10, 10);

        add(raceName);

        setSize(getPreferredSize());

    }

    public Race getRaceToDisplay() {
        return raceToDisplay;
    }

    public void updateRace(Race newRaceToDisplay) {
        raceToDisplay = newRaceToDisplay;

        raceName.setText("Rasse: " + raceToDisplay.getName());

    }
}
