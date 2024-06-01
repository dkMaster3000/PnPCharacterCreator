package org.example;

import javax.swing.*;
import java.util.Objects;

public class LvlPanel extends JPanel {

    private final UpdatePanels updatePanels;
    JLabel pointsToSpentLabel;

    JComboBox<String> lvlComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

    public LvlPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;


        JLabel lvlComboBoxLabel = new JLabel("Lvl: ");
        add(lvlComboBoxLabel);

        lvlComboBox.setSelectedIndex(0);
        lvlComboBox.addActionListener(e -> updateChracterLvl(Integer.parseInt((String) Objects.requireNonNull(lvlComboBox.getSelectedItem()))));
        add(lvlComboBox);

        pointsToSpentLabel = new JLabel("Punkte zu vergeben: " + MainFrame.character.getStatPoints());
        add(pointsToSpentLabel);

        JButton minusStrengthB = new JButton("-");
        add(minusStrengthB);
        JLabel StrengthLabel = new JLabel("Stärke");
        add(StrengthLabel);
        JButton plusStrengthB = new JButton("+");
        add(plusStrengthB);
    }

    private void updateChracterLvl(int level) {
        MainFrame.character.setLvl(level);
        pointsToSpentLabel.setText("Punkte zu vergeben: " + MainFrame.character.getStatPoints());

        updatePanels.updatePanels();
    }


}
