package org.example.mainframe;

import org.example.models.Talent;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TalentComboBox extends JComboBox<String> {

    private final UpdatePanels updatePanels;
    private final int talentNumber;


    public TalentComboBox(UpdatePanels updatePanels, int talentNumber) {
        this.updatePanels = updatePanels;

        this.talentNumber = talentNumber;

        updateTalentChoices(true);
    }

    public void updateTalentChoices(boolean triggerReUpdate) {
        System.out.println("Update Talent Combo Box: " + talentNumber);

//        if (TalentsPanel.usedTalents.size() - 1 >= talentNumber) {
//            if (Objects.equals(TalentsPanel.usedTalents.get(talentNumber), (String) getSelectedItem())) {
//                System.out.println("SKIPPED: Update Talent Combo Box: " + talentNumber);
//                return;
//            }
//        }

        DefaultComboBoxModel<String> talentNamesModel = new DefaultComboBoxModel<>(getTalents());
        this.setModel(talentNamesModel);

        setSelectedIndex(0);
        addActionListener(e -> updateCharacterTalent(true));

        if (triggerReUpdate) {
            System.out.println("Retrigger after creation!");
            updateCharacterTalent(talentNumber > 0);
        }
    }

    private void updateCharacterTalent(boolean triggerReUpdate) {

        String talentName = (String) getSelectedItem();
        MainFrame.character.updateTalents(talentNumber, MainFrame.talentMatrix.getTalentByName(talentName));

        System.out.println("----------------------------------------------");

        System.out.println("triggeReUpdate: " + triggerReUpdate);
        System.out.println("Before change: " + talentNumber + ": " + TalentsPanel.usedTalents);

        if (TalentsPanel.usedTalents.size() - 1 >= talentNumber) {
            TalentsPanel.usedTalents.set(talentNumber, talentName);
        } else {
            TalentsPanel.usedTalents.add(talentNumber, talentName);
        }

        System.out.println("After change: " + talentNumber + ": " + TalentsPanel.usedTalents);


        if (triggerReUpdate) {
            System.out.println("Theoretical Reupdate");
            updatePanels.updatePanels();
        }
        System.out.println("----------------------------------------------");
    }

    private String[] getTalents() {
        List<String> talentsChoices = MainFrame.talentMatrix.getTalents().stream().map(Talent::getName).toList();

        List<String> talentChoicesWithoutUsedTalents = talentsChoices.stream().filter(talentChoice -> {
            boolean talentUnused = true;
            for (String usedTalent : TalentsPanel.usedTalents) {
                talentUnused = !Objects.equals(talentChoice, usedTalent);
            }
            return talentUnused;
        }).collect(toList());

        //add its own choice if exists
        if (TalentsPanel.usedTalents.size() - 1 >= talentNumber) {
            talentChoicesWithoutUsedTalents.addFirst(TalentsPanel.usedTalents.get(talentNumber));
        }

        return talentChoicesWithoutUsedTalents.toArray(String[]::new);
    }


}
