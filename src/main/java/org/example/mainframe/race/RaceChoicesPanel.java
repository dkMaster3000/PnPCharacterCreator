package org.example.mainframe.race;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatableByMainFrame;
import org.example.mainframe.UpdatePanels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class RaceChoicesPanel extends JPanel implements UpdatableByMainFrame {

    private final UpdatePanels updatePanels;

    //update checker
    private String previousRaceName = "";

    JPanel comboboxHolder;

    public RaceChoicesPanel(UpdatePanels updatePanels) {
        this.updatePanels = updatePanels;

        setMaximumSize(new Dimension(1000, 45));

        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        JLabel talentsLabel = new JLabel("Auswahl Rassen Talente: ");
        add(talentsLabel);

        comboboxHolder = new JPanel();
        add(comboboxHolder);

        InstantiateChoicesComboBoxes();
    }

    @Override
    public void UpdateByMainFrame() {
        InstantiateChoicesComboBoxes();
    }

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
            choiceComboBox.addActionListener(_ -> {
                //modify character
                updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem());
                //tells MainFrame to update panels
                updatePanels.updatePanels();
            });
            comboboxHolder.add(choiceComboBox);

            updateChosenBuffsOfCharacter(chosenBuffNumber, (String) choiceComboBox.getSelectedItem());
        }

        comboboxHolder.revalidate();
    }

    //modify character chosenBuffs, tells mainframe to update the other frames
    private void updateChosenBuffsOfCharacter(int chosenBuffNumber, String buff) {
        MainFrame.character.updateChosenRaceBuffs(chosenBuffNumber, buff);
    }


}
