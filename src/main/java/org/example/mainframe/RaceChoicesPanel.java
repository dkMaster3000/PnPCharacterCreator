package org.example.mainframe;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class RaceChoicesPanel extends JPanel {

    private final UpdatePanels updatePanels;

    //update checker
    private String previousRaceName = "";

    JPanel comboboxHolder;

    public RaceChoicesPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        JLabel talentsLabel = new JLabel("Auswahl: ");
        add(talentsLabel);

        comboboxHolder = new JPanel();
        add(comboboxHolder);
    }

    //invoked by a function in MainFrame, if the chracter has been modified
    public void InstantiateChoicesComboBoxes() {

        //update checker to prevent constantly updating
        String newRaceName = MainFrame.character.getRace().getName();
        if (Objects.equals(newRaceName, previousRaceName)) {
            return;
        } else {
            previousRaceName = newRaceName;
        }

        comboboxHolder.removeAll();

        List<List<String>> choices = MainFrame.character.getRace().getChoices();

        for (int i = 0; i < choices.size(); i++) {
            String[] choicePossibilities = choices.get(i).toArray(String[]::new);
            JComboBox<String> choiceComboBox = new JComboBox<>(choicePossibilities);
            choiceComboBox.setSelectedIndex(0);

            int chosenBuffNumber = i;
            choiceComboBox.addActionListener(e -> updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem()));
            comboboxHolder.add(choiceComboBox);

            updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem());
        }

        comboboxHolder.revalidate();
    }

    //modify character chosenBuffs, tells mainframe to update the other frames
    private void updateChosenBuffsOfCharacter(int chosenBuffNumber, String buff) {
        MainFrame.character.updateChosenRaceBuffs(chosenBuffNumber, buff);

        updatePanels.updatePanels();
    }
}
