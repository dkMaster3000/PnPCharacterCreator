package org.example.mainframe.rpgclass;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.RPGClass;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RPGClassPanel extends JPanel {

    JComboBox<String> rpgClassComboBox;

    public RPGClassPanel(UpdatePanels updatePanels) {

        setMaximumSize(new Dimension(1000, 45));

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel talentsLabel = new JLabel("Klassenauswahl: ");
        add(talentsLabel);

        rpgClassComboBox = new JComboBox<>();
        DefaultComboBoxModel<String> talentNamesModel = new DefaultComboBoxModel<>(getRPGClassNames());
        rpgClassComboBox.setModel(talentNamesModel);
        rpgClassComboBox.setSelectedIndex(0);
        rpgClassComboBox.addActionListener(_ -> {
            //modify character
            updateCharacterRPGClass();
            //tell MainFrame to update panels
            updatePanels.updatePanels();
        });
        add(rpgClassComboBox);

        updateCharacterRPGClass();
    }

    private void updateCharacterRPGClass() {
        String selectedRPGClass = (String) rpgClassComboBox.getSelectedItem();
        MainFrame.character.setRpgClass(
                MainFrame.rpgClasses
                        .stream()
                        .filter(rpgClass -> Objects.equals(rpgClass.getName(), selectedRPGClass)).findFirst().orElse(null));
    }


    private String[] getRPGClassNames() {
        return MainFrame.rpgClasses
                .stream()
                .map(RPGClass::getName)
                .toArray(String[]::new);
    }
}
