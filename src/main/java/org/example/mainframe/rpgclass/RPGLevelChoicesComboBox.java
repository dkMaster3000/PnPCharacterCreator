package org.example.mainframe.rpgclass;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.Passiv;
import org.example.models.RPGClassChooseable;
import org.example.models.Spell;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//will only work if you have to select between two option
public class RPGLevelChoicesComboBox extends JComboBox<String> {
    List<RPGClassChooseable> chooseables;
    String[] choicePossibilities;

    public RPGLevelChoicesComboBox(UpdatePanels updatePanels, List<RPGClassChooseable> chooseables) {
        this.chooseables = chooseables;

        choicePossibilities = chooseables.stream().map(RPGClassChooseable::getName).toArray(String[]::new);
        DefaultComboBoxModel<String> raceNamesModel = new DefaultComboBoxModel<>(choicePossibilities);
        setModel(raceNamesModel);
        setSelectedIndex(0);

        addActionListener(_ -> {
            //modify character
            updateCharacterChosenSkills();
            //tell MainFrame to update the panels
            updatePanels.updatePanels();
        });

        updateCharacterChosenSkills();
    }

    private void updateCharacterChosenSkills() {
        int selectedIndex = getSelectedIndex();
        int unselectedIndex = selectedIndex == 0 ? 1 : 0;

        removeUnselectedChoosableFromCharacter(unselectedIndex);
        addSelectedChoosableToCharacter(selectedIndex);

//        modifyChooseableFromCharacter(unselectedIndex,
//                unselectedChoosable -> MainFrame.character.getChosenSpells().remove((Spell) unselectedChoosable),
//                unselectedChoosable -> MainFrame.character.getChosenPassivs().remove((Passiv) unselectedChoosable)
//        );

    }

//    private interface Modify {
//
//        void modify(RPGClassChooseable selectedChoosable);
//    }

//    private void modifyChooseableFromCharacter(int selectedIndex, Modify spellModifier, Modify passivModifier) {
//        RPGClassChooseable selectedChoosable = getChooseableByName(choicePossibilities[selectedIndex]);
//
//        if (selectedChoosable instanceof Spell) {
//            spellModifier.modify(selectedChoosable);
//        } else if (selectedChoosable instanceof Passiv) {
//            passivModifier.modify(selectedChoosable);
//        } else {
//            System.out.println("hoosable TypeMissMatch");
//        }
//    }

    private void removeUnselectedChoosableFromCharacter(int unselectedIndex) {
        RPGClassChooseable unselectedChoosable = getChooseableByName(choicePossibilities[unselectedIndex]);

        if (unselectedChoosable instanceof Spell) {
            MainFrame.character.getChosenSpells().remove(unselectedChoosable);
        } else if (unselectedChoosable instanceof Passiv) {
            MainFrame.character.getChosenPassivs().remove(unselectedChoosable);
        } else {
            System.out.println("Unselected Choosable TypeMissMatch");
        }
    }

    private void addSelectedChoosableToCharacter(int selectedIndex) {
        RPGClassChooseable selectedChoosable = getChooseableByName(choicePossibilities[selectedIndex]);

        if (selectedChoosable instanceof Spell) {
            MainFrame.character.getChosenSpells().add((Spell) selectedChoosable);
        } else if (selectedChoosable instanceof Passiv) {
            MainFrame.character.getChosenPassivs().add((Passiv) selectedChoosable);
        } else {
            System.out.println("Unselected Choosable TypeMissMatch");
        }
    }

    private RPGClassChooseable getChooseableByName(String chooseableName) {
        return chooseables.stream()
                .filter(x -> Objects.equals(x.getName(), chooseableName))
                .findAny()
                .orElse(null); //should never happen
    }
}
