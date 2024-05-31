package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ChoicesPanel extends JPanel {

    private UpdatePreview updatePreview;

    public ChoicesPanel(UpdatePreview updatePreview) {
        this.updatePreview = updatePreview;
    }


    public void InstantiateChoicesComboBoxes(Character character) {
        removeAll();

        List<List<String>> choices = character.getRace().getChoices();

        for (int i = 0; i < choices.size(); i++) {
            String[] choicePossibilities = choices.get(i).toArray(String[]::new);
            JComboBox<String> choiceComboBox = new JComboBox<>(choicePossibilities);
            choiceComboBox.setSelectedIndex(0);
            choiceComboBox.addActionListener(new NumberedActionListener(i) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    character.updateChosenBuffs(number, (String) choiceComboBox.getSelectedItem());
                    updatePreview.updatePreview();
                }
            });
            add(choiceComboBox);
            character.updateChosenBuffs(i, (String) choiceComboBox.getSelectedItem());
            updatePreview.updatePreview();
        }

        revalidate();
    }
}
