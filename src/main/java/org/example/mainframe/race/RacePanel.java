package org.example.mainframe.race;

import org.example.mainframe.UpdatePanels;

import javax.swing.*;
import java.awt.*;


public class RacePanel extends JPanel {

    JLabel uploadedLabel;

    public RacePanel(UpdatePanels updatePanels) {
        setMaximumSize(new Dimension(1000, 45));

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        uploadedLabel = new JLabel("Rassenauswahl: ");
        uploadedLabel.setBounds(10, 10, 100, 40);
        add(uploadedLabel);

        RaceComboBox raceJComboBox = new RaceComboBox(updatePanels);
        raceJComboBox.setBounds(110, 10, 150, 40);
        add(raceJComboBox);
    }
}
