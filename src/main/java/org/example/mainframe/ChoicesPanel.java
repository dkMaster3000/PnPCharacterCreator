package org.example.mainframe;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class ChoicesPanel extends JPanel {

    private final UpdatePanels updatePanels;
    private String previousRaceName = "";

    public ChoicesPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;
    }

    public void InstantiateChoicesComboBoxes() {

        //update checker to prevent constantly updating
        String newRaceName = MainFrame.character.getRace().getName();
        if (Objects.equals(newRaceName, previousRaceName)) {
            return;
        } else {
            previousRaceName = newRaceName;
        }

        removeAll();

        List<List<String>> choices = MainFrame.character.getRace().getChoices();

        for (int i = 0; i < choices.size(); i++) {
            String[] choicePossibilities = choices.get(i).toArray(String[]::new);
            JComboBox<String> choiceComboBox = new JComboBox<>(choicePossibilities);
            choiceComboBox.setSelectedIndex(0);

            int chosenBuffNumber = i;
            choiceComboBox.addActionListener(e -> updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem()));
            add(choiceComboBox);

            updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem());
        }

        revalidate();
    }

    private void updateChosenBuffsOfCharacter(int chosenBuffNumber, String buff) {
        MainFrame.character.updateChosenBuffs(chosenBuffNumber, buff);
        updatePanels.updatePanels();
    }
}
