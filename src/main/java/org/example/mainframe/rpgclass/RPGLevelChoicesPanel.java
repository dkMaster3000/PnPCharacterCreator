package org.example.mainframe.rpgclass;

import org.example.mainframe.UpdatePanels;
import org.example.models.RPGClassChooseable;
import org.example.models.RPGClassLevel;

import javax.swing.*;
import java.util.List;

public class RPGLevelChoicesPanel extends JPanel {

    private final UpdatePanels updatePanels;

    //panel for ComboBoxesObject
    JPanel comboboxHolder;

    List<List<RPGClassChooseable>> choosables;

    public RPGLevelChoicesPanel(UpdatePanels updatePanels, RPGClassLevel rpgClassLevel) {
        this.updatePanels = updatePanels;
        choosables = rpgClassLevel.getChoosables();

        JLabel classSkillChoicesLabel = new JLabel("Lvl: " + rpgClassLevel.getLvl() + " Auswahl: ");
        classSkillChoicesLabel.setBounds(5, 5, 150, 40);
        add(classSkillChoicesLabel);

        comboboxHolder = new JPanel();
        add(comboboxHolder);

        for (List<RPGClassChooseable> choosable : choosables) {

            RPGLevelChoicesComboBox choiceComboBox = new RPGLevelChoicesComboBox(updatePanels, choosable);
            comboboxHolder.add(choiceComboBox);
        }
    }
}
