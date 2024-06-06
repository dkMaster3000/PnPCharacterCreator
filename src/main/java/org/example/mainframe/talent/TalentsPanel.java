package org.example.mainframe.talent;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TalentsPanel extends JPanel {

    private final UpdatePanels updatePanels;

    //update checker
    private int previousTalentsAmount = -1;

    //panel for ComboBoxesObject
    JPanel comboboxHolder;

    //stores talents that are chosen by ComboBoxes
    public static List<String> usedTalents = new ArrayList<>();

    //stores all ComboBoxesObject to be able to update them
    public static List<TalentComboBox> talentComboBoxes = new ArrayList<>();

    public TalentsPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        setMaximumSize(new Dimension(1000, 45));

        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        JLabel talentsLabel = new JLabel("Talente: ");
        add(talentsLabel);

        comboboxHolder = new JPanel();
        add(comboboxHolder);

        UpdateTalentsPanel();
    }

    //invoked by a function in MainFrame, if the chracter has been modified
    public void UpdateTalentsPanel() {
        int newTalentsAmount = MainFrame.talentMatrix.calculateTalents();
        if (previousTalentsAmount != newTalentsAmount) {
            previousTalentsAmount = newTalentsAmount;
            usedTalents = new ArrayList<>();
            talentComboBoxes = new ArrayList<>();

            comboboxHolder.removeAll();

            //generate ComboBoxes that can modify the character
            for (int i = 0; i < newTalentsAmount; i++) {
                TalentComboBox talentComboBox = new TalentComboBox(updatePanels, i);
                talentComboBoxes.add(talentComboBox);
                comboboxHolder.add(talentComboBox);
            }

            comboboxHolder.revalidate();
        }
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
