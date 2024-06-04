package org.example.mainframe;

import org.example.models.RPGClass;
import org.example.models.Talent;

import javax.swing.*;
import java.util.Objects;

public class RPGClassPanel extends JPanel {

    private final UpdatePanels updatePanels;

    JComboBox<String> rpgClassComboBox;

    public RPGClassPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

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
