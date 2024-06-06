package org.example.mainframe.rpgclass;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.Passiv;
import org.example.models.RPGClassChooseable;
import org.example.models.Spell;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

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

        List<Spell> chosenSpells = MainFrame.character.getChosenSpells();
        List<Passiv> chosenPassivs = MainFrame.character.getChosenPassivs();

        //remove previous chooseable
        modifyChooseableFromCharacter(unselectedIndex,
                unselectedChoosable -> chosenSpells.remove((Spell) unselectedChoosable),
                unselectedChoosable -> chosenPassivs.remove((Passiv) unselectedChoosable)
        );

        //add new chooseable
        modifyChooseableFromCharacter(selectedIndex,
                selectedChoosable -> chosenSpells.add((Spell) selectedChoosable),
                selectedChoosable -> chosenPassivs.add((Passiv) selectedChoosable)
        );
    }

    private interface ModifyCharacterChooseables {

        void modify(RPGClassChooseable selectedChoosable);
    }

    private void modifyChooseableFromCharacter(int selectedIndex, ModifyCharacterChooseables spellModifier, ModifyCharacterChooseables passivModifier) {
        RPGClassChooseable selectedChoosable = getChooseableByName(choicePossibilities[selectedIndex]);

        switch (selectedChoosable) {
            case Spell _ -> spellModifier.modify(selectedChoosable);
            case Passiv _ -> passivModifier.modify(selectedChoosable);
            default -> System.out.println("Choosable TypeMissMatch");
        }
    }

    private RPGClassChooseable getChooseableByName(String chooseableName) {
        return chooseables.stream()
                .filter(x -> Objects.equals(x.getName(), chooseableName))
                .findAny()
                .orElse(null); //should never happen
    }
}
