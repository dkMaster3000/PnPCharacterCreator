package org.example.mainframe;

import org.example.models.Talent;
import org.example.models.TalentMatrix;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class TalentComboBox extends JComboBox<String> {

    private final UpdatePanels updatePanels;
    private final int talentNumber;

    //important, because al must be removed if we modify the list of choices
    //because if we modify the list it will be triggered
    ActionListener updateCharacterTalentAL = _ -> updateCharacterTalent();

    public TalentComboBox(UpdatePanels updatePanels, int talentNumber) {
        this.updatePanels = updatePanels;
        this.talentNumber = talentNumber;

        updateTalentChoices();

        updateCharacterTalent();
    }

    //invoked by other TalentComboBoxes
    public void updateTalentChoices() {
        removeActionListener(updateCharacterTalentAL);

        DefaultComboBoxModel<String> talentNamesModel = new DefaultComboBoxModel<>(getTalents());
        this.setModel(talentNamesModel);
        setSelectedIndex(0);

        addActionListener(updateCharacterTalentAL);
    }

    //modify character talents, modify all TalentComboBoxes, tells mainframe to update the other frames
    private void updateCharacterTalent() {
        String talentName = (String) getSelectedItem();

        //modify character talents
        MainFrame.character.updateTalents(talentNumber, MainFrame.talentMatrix.getTalentByName(talentName));

        //update list that contains all used talents
        TalentsPanel.setUsedTalentName(talentNumber, talentName);

        //update the TalentComboBoxes (also itself because of uniqueness attribute)
        for (int i = 0; i < TalentsPanel.talentComboBoxes.size(); i++) {
            TalentsPanel.talentComboBoxes.get(i).updateTalentChoices();
        }

        //tells mainframe to update the other frames
        updatePanels.updatePanels();
    }

    private String[] getTalents() {
        //determine if a unique talent is already selected
        boolean uniqueTalentSelected = TalentsPanel.usedTalents
                .stream()
                .map(usedTalentName -> MainFrame.talentMatrix.getTalentByName(usedTalentName)).anyMatch(Talent::isUnique);

        //get own choice if exists
        String ownChoice = TalentsPanel.getUsedTalentName(talentNumber);

        //determine wether own choice is unique or not
        boolean ownChoiceIsUnique;
        if (ownChoice != null) {
            ownChoiceIsUnique = MainFrame.talentMatrix.getTalentByName(ownChoice).isUnique();
        } else {
            ownChoiceIsUnique = false;
        }

        //get talents from talentMatrix, filtered by uniqueness
        List<String> talentsChoices = MainFrame.talentMatrix.getTalents()
                .stream()
                .filter(talent -> ownChoiceIsUnique || !uniqueTalentSelected || !talent.isUnique())
                .map(Talent::getName)
                .toList();

        //filter out all usedTalents
        List<String> talentChoicesWithoutUsedTalents =
                talentsChoices
                        .stream()
                        .filter(talentChoice -> !TalentsPanel.usedTalents.contains(talentChoice))
                        .collect(Collectors.toList());

        //add its own choice if exists
        if (ownChoice != null) talentChoicesWithoutUsedTalents.addFirst(ownChoice);

        return talentChoicesWithoutUsedTalents.toArray(String[]::new);
    }


}
