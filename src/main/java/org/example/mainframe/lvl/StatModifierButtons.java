package org.example.mainframe.lvl;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatePanels;
import org.example.models.Character;

import javax.swing.*;

public class StatModifierButtons extends JPanel {

    private final UpdatePanels updatePanels;
    private final Character.STATNAMES attribute;

    public StatModifierButtons(UpdatePanels updatePanels, Character.STATNAMES attribute) {
        this.updatePanels = updatePanels;

        Character character = MainFrame.character;

        this.attribute = attribute;
        int modifyValue = character.getModifyValueByEnum(attribute);

        JButton minusButton = new JButton("-");
        minusButton.addActionListener(e -> modifyCharacterValue(-modifyValue));
        add(minusButton);

        JLabel buttonsLabel = new JLabel(MainFrame.character.getStringToEnum(attribute));
        add(buttonsLabel);

        JButton plusButton = new JButton("+");
        plusButton.addActionListener(e -> modifyCharacterValue(modifyValue));
        add(plusButton);
    }

    private void modifyCharacterValue(int modifyValue) {
        MainFrame.character.getSetterByEnum(attribute).setStat(modifyValue);

        updatePanels.updatePanels();
    }
}
