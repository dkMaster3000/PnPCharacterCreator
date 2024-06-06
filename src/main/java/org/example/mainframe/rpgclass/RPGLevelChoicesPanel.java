package org.example.mainframe.rpgclass;

import org.example.mainframe.UpdatePanels;
import org.example.models.RPGClassChooseable;
import org.example.models.RPGClassLevel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RPGLevelChoicesPanel extends JPanel {

    //panel for ComboBoxesObject
    JPanel comboboxHolder;

    List<List<RPGClassChooseable>> choosables;

    public RPGLevelChoicesPanel(UpdatePanels updatePanels, RPGClassLevel rpgClassLevel) {
        choosables = rpgClassLevel.getChoosables();

        setMaximumSize(new Dimension(1000, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel classSkillChoicesLabel = new JLabel("Lvl: " + rpgClassLevel.getLvl() + " Auswahl: ");
        add(classSkillChoicesLabel);

        comboboxHolder = new JPanel();
        add(comboboxHolder);

        if (choosables.isEmpty()) {
            JLabel noChoiceLabel = new JLabel("Keine Auswahl verfügbar.");
            comboboxHolder.add(noChoiceLabel);
        } else {
            for (List<RPGClassChooseable> choosable : choosables) {
                RPGLevelChoicesComboBox choiceComboBox = new RPGLevelChoicesComboBox(updatePanels, choosable);
                comboboxHolder.add(choiceComboBox);
            }
        }
    }
}
