package org.example.mainframe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TalentsPanel extends JPanel {

    private final UpdatePanels updatePanels;

    //update checker
    private int previousLvl = 0;

    //panel for ComboBoxesObject
    JPanel onlyTalentsPanel;

    //stores talents that are chosen by ComboBoxes
    public static List<String> usedTalents = new ArrayList<>();

    //stores all ComboBoxesObject to be able to update them
    public static List<TalentComboBox> talentComboBoxes = new ArrayList<>();

    public TalentsPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        JLabel talentsLabel = new JLabel("Talente: ");
        add(talentsLabel);

        onlyTalentsPanel = new JPanel();
        add(onlyTalentsPanel);
    }

    //invoked by a function in MainFrame, if the chracter has been modified
    public void UpdateTalentsPanel() {

        int newLvl = MainFrame.character.getLvl();
        if (previousLvl != newLvl) {
            previousLvl = newLvl;
            usedTalents = new ArrayList<>();
            talentComboBoxes = new ArrayList<>();

            generateTalentComboBoxes();
        }
    }

    //generate ComboBoxes that can modify the character
    private void generateTalentComboBoxes() {
        onlyTalentsPanel.removeAll();

        for (int i = 0; i < MainFrame.talentMatrix.calculateTalents(); i++) {
            TalentComboBox talentComboBox = new TalentComboBox(updatePanels, i);
            talentComboBoxes.add(talentComboBox);
            onlyTalentsPanel.add(talentComboBox);
        }

        onlyTalentsPanel.revalidate();
    }

    public static String getUsedTalentName(int talentNumber) {
        if (usedTalents.size() - 1 >= talentNumber) {
            return usedTalents.get(talentNumber);
        } else {
            return null;
        }
    }

    public static void setUsedTalentName(int talentNumber, String talentName) {
        if (usedTalents.size() - 1 >= talentNumber) {
            usedTalents.set(talentNumber, talentName);
        } else {
            usedTalents.add(talentNumber, talentName);
        }
    }
}
