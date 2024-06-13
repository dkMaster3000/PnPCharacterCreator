package org.example.mainframe.lvl;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatableByMainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.mainframe.UsedValues;
import org.example.models.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LvlPanel extends JPanel implements UpdatableByMainFrame {

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

    @Override
    public void UpdateByMainFrame() {
        UpdateLvlPanel();
    }

    public void UpdateLvlPanel() {
        int characterStatPoints = MainFrame.character.getStatPoints();
        pointsToSpentLabel.setText("Punkte zu vergeben: " + characterStatPoints);
        pointsToSpentLabel.setForeground(characterStatPoints > 0 ? Color.RED : UsedValues.DARK_GREEN);
    }

    //modify character, tells mainframe to update the other frames
    private void updateChracterLvl(int level) {
        MainFrame.character.setLvl(level);
    }


}
