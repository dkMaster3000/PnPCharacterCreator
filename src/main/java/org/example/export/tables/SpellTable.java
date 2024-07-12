package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.Spell;

import java.util.List;

public class SpellTable extends PaintedTable {

    public SpellTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {
        List<Spell> allCharacterSpells = MainFrame.character.getAllCharacterSpells();

        String[] spellHeader = new String[]{"Name", "Art", "Schnelligkeit", "Effekt", "Reichweite"};
        if (!allCharacterSpells.isEmpty()) {
            createRow("Zauber");
            createRow(spellHeader, true);
            for (Spell spell : allCharacterSpells) {
                createRow(new String[]{
                        spell.getName(),
                        spell.getDifficulty(),
                        spell.getTempo(),
                        spell.getEffect(),
                        spell.getRange()}, false);
            }
        }
    }
}
