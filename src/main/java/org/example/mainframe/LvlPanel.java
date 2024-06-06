package org.example.mainframe;

import org.example.models.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LvlPanel extends JPanel {

    JLabel pointsToSpentLabel;

    JComboBox<String> lvlComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

    public LvlPanel(UpdatePanels updatePanels) {

        setMaximumSize(new Dimension(1000, 45));

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel lvlComboBoxLabel = new JLabel("Lvl: ");
        add(lvlComboBoxLabel);

        lvlComboBox.setSelectedIndex(0);
        lvlComboBox.addActionListener(_ -> {
            updateChracterLvl(Integer.parseInt((String) Objects.requireNonNull(lvlComboBox.getSelectedItem())));

            updatePanels.updatePanels();
        });
        add(lvlComboBox);

        pointsToSpentLabel = new JLabel();
        UpdateLvlPanel();
        add(pointsToSpentLabel);

        for (Character.STATNAMES statname : Character.STATNAMES.values()) {
            StatModifierButtons statModifierButtons = new StatModifierButtons(updatePanels, statname);
            add(statModifierButtons);
        }

        updateChracterLvl(Integer.parseInt((String) Objects.requireNonNull(lvlComboBox.getSelectedItem())));
    }

    //invoked by a function in MainFrame, if the chracter has been modified
    public void UpdateLvlPanel() {
        pointsToSpentLabel.setText("Punkte zu vergeben: " + MainFrame.character.getStatPoints());
    }

    //modify character, tells mainframe to update the other frames
    private void updateChracterLvl(int level) {
        MainFrame.character.setLvl(level);
    }
}
