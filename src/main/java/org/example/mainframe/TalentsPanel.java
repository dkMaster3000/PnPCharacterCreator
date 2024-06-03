package org.example.mainframe;

import org.example.models.Talent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TalentsPanel extends JPanel {

    private final UpdatePanels updatePanels;
    //String[] talentsChoices;


    static List<String> usedTalents = new ArrayList<>();
    private int previousLvl = 0;
    List<String> usedTalentsCompareList = new ArrayList<>();

    List<TalentComboBox> talentComboBoxes = new ArrayList<>();

//    boolean updateThisFrame = false;

    public TalentsPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;
    }

    private boolean talendListChanged() {

        boolean isChanged = false;
        if (usedTalentsCompareList.size() != usedTalents.size()) {
            isChanged = true;
        } else {
            for (int i = 0; i <= usedTalents.size() - 1; i++) {
                if (!Objects.equals(usedTalentsCompareList.get(i), usedTalents.get(i))) {
                    isChanged = true;
                    break;
                }
            }
        }

        if (isChanged) {
            System.out.println("UNEQUAL: " + usedTalentsCompareList + "  " + usedTalents);
            usedTalentsCompareList = new ArrayList<>(usedTalents);
        }

        return isChanged;

//        if (!usedTalentsCompareList.equals(usedTalents)) {
//            usedTalentsCompareList = new ArrayList<>(usedTalents);
//            System.out.println("UNEQUAL: " + usedTalentsCompareList + "  " + usedTalents);
//            return true;
//        }
//        return false;
    }

    public void UpdateTalentsPanel() {

        //instantiate talentsChoices if not instantiated
//        if (talentsChoices == null) {
//            talentsChoices = MainFrame.talentMatrix.getTalents().stream().map(Talent::getName).toArray(String[]::new);
//        }


        int newLvl = MainFrame.character.getLvl();
        if (previousLvl == newLvl) {
            if (talendListChanged()) {
                for (TalentComboBox talentComboBox : talentComboBoxes) {
                    talentComboBox.updateTalentChoices(false);
                }
            }
            return;
        } else {
            previousLvl = newLvl;
            usedTalents = new ArrayList<>();
            usedTalentsCompareList = new ArrayList<>();
            talentComboBoxes = new ArrayList<>();

            generateTalentComboBoxes();
        }


        //updatePanels.updatePanels();
    }

    private void generateTalentComboBoxes() {
        System.out.println("Reprint");
//        updateThisFrame = false;

        removeAll();

        JLabel talentsLabel = new JLabel("Talente: ");
        add(talentsLabel);


        for (int i = 0; i < MainFrame.talentMatrix.calculateTalents(); i++) {

//            List<String> talentChoicesWithoutUsedTalents = new ArrayList<>(Arrays.stream(talentsChoices).filter(talentChoice -> {
//                boolean talentUnused = true;
//                for (String usedTalent : usedTalents) {
//                    talentUnused = !Objects.equals(talentChoice, usedTalent);
//                }
//                return talentUnused;
//            }).toList());
//
//            //add its own choice if exists
//            if (usedTalents.size() - 1 >= i) {
//                System.out.println("added used talent");
//                talentChoicesWithoutUsedTalents.addFirst(usedTalents.get(i));
//                System.out.println(usedTalents.get(i));
//            }
//
//            String[] talentChoicesAdjusted = talentChoicesWithoutUsedTalents.toArray(String[]::new);
//
//            //combobox itself
//            JComboBox<String> choiceComboBox = new JComboBox<>(talentChoicesAdjusted);
//            choiceComboBox.setSelectedIndex(0);
//            int chosenTalentNumber = i;
//            choiceComboBox.addActionListener(e -> updateCharacterTalent(chosenTalentNumber, (String) choiceComboBox.getSelectedItem(), true));
//            add(choiceComboBox);
//
//            updateCharacterTalent(chosenTalentNumber, (String) choiceComboBox.getSelectedItem(), false);

            TalentComboBox talentComboBox = new TalentComboBox(updatePanels, i);
            talentComboBoxes.add(talentComboBox);
            add(talentComboBox);
        }

        revalidate();
    }

//    private void updateCharacterTalent(int chosenTalentNumber, String talentName, boolean updateMainFrame) {
//        MainFrame.character.updateTalents(chosenTalentNumber, MainFrame.talentMatrix.getTalentByName(talentName));
//
//
//        if (usedTalents.size() - 1 >= chosenTalentNumber) {
//            usedTalents.set(chosenTalentNumber, talentName);
//        } else {
//            usedTalents.add(chosenTalentNumber, talentName);
//        }
//
//        updateThisFrame = updateMainFrame;
//        if (updateMainFrame) {
//            generateTalentComboBoxes();
//            updatePanels.updatePanels();
//        }
//
//        System.out.println(usedTalents);
//
//    }
}
