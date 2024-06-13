package org.example.mainframe.rpgclass;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatableByMainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.RPGClassLevel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RPGClassSkillChoicesPanel extends JPanel implements UpdatableByMainFrame {
    private final UpdatePanels updatePanels;

    //update checker
    private int previousLvl = -1;
    private String previosRPGClassName = null;

    //panel for ComboBoxesObject
    JPanel comboboxHolder;

    public RPGClassSkillChoicesPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        setMaximumSize(new Dimension(1000, 450));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel classSkillChoicesLabel = new JLabel("Klassen Skill Auswahl: ");

        JPanel choiceLabelPanel = new JPanel();
        choiceLabelPanel.add(classSkillChoicesLabel);
        choiceLabelPanel.setMaximumSize(new Dimension(1000, 30));
        choiceLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        choiceLabelPanel.add(classSkillChoicesLabel);
        add(choiceLabelPanel);

        comboboxHolder = new JPanel();
        comboboxHolder.setLayout(new BoxLayout(comboboxHolder, BoxLayout.Y_AXIS));
        add(comboboxHolder);

        UpdateRPGClassSkillChoicesPanel();
    }

    @Override
    public void UpdateByMainFrame() {
        UpdateRPGClassSkillChoicesPanel();
    }

    public void UpdateRPGClassSkillChoicesPanel() {
        int newLvl = MainFrame.character.getLvl();
        String newRPGClassName = MainFrame.character.getRpgClass().getName();
        if (previousLvl != newLvl) {
            previousLvl = newLvl;
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
            RPGLevelChoicesPanel rpgLevelChoicesPanel = new RPGLevelChoicesPanel(updatePanels, classLvl);
            comboboxHolder.add(rpgLevelChoicesPanel);
        }
    }

}
