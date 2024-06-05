package org.example.mainframe.rpgclass;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.RPGClassLevel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RPGClassSkillChoicesPanel extends JPanel {
    private final UpdatePanels updatePanels;

    //update checker
    private int previousLvl = -1;
    private String previosRPGClassName = null;

    //panel for ComboBoxesObject
    JPanel comboboxHolder;

    public RPGClassSkillChoicesPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        setLayout(null);

        JLabel classSkillChoicesLabel = new JLabel("Klassen Skill Auswahl: ");
        classSkillChoicesLabel.setBounds(5, 5, 150, 40);
        add(classSkillChoicesLabel);

        comboboxHolder = new JPanel();
        comboboxHolder.setBounds(0, 50, 855, 400);
        comboboxHolder.setLayout(new GridLayout(0, 1, 5, 5));
        add(comboboxHolder);

        UpdateRPGClassSkillChoicesPanel();
    }

    //invoked by a function in MainFrame, if the chracter has been modified
    public void UpdateRPGClassSkillChoicesPanel() {
        int newLvl = MainFrame.character.getLvl();
        String newRPGClassName = MainFrame.character.getRpgClass().getName();
        if (previousLvl != newLvl) {
            previousLvl = newLvl;
//            usedTalents = new ArrayList<>();
//            talentComboBoxes = new ArrayList<>();

            generateChoicesPanels();
            return;
        }

        if (!Objects.equals(previosRPGClassName, newRPGClassName)) {
            previosRPGClassName = newRPGClassName;
            generateChoicesPanels();
        }
    }

    //generate ChoicesPanels that can modify the character
    private void generateChoicesPanels() {
        comboboxHolder.removeAll();

        for (int i = 0; i < MainFrame.character.getLvl(); i++) {
            RPGClassLevel classLvl = MainFrame.character.getRpgClass().getClassLvls().get(i);
            if (classLvl.getChoosables().isEmpty()) {
                continue;
            }
            RPGLevelChoicesPanel rpgLevelChoicesPanel = new RPGLevelChoicesPanel(updatePanels, classLvl);
            comboboxHolder.add(rpgLevelChoicesPanel);
        }

        comboboxHolder.revalidate();
    }
}
