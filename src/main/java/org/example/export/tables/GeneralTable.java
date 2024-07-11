package org.example.export.tables;

import org.apache.poi.ss.usermodel.*;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;

import java.util.List;

public class GeneralTable extends PaintedTable {

    public GeneralTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {

        createRow("Vorname");
        createRow("Nachname");
        createRow("Ruf");
        createRow("Alter");
        createRow("Sonstiges");

        createRow("Klasse", MainFrame.character.getRpgClass().getName());
        createRow("Rasse", MainFrame.character.getRace().getName());
        createRow("Gewicht");
        createRow("Größe");

        List<String> characterRaceBuffs = MainFrame.character.getAllBuffs();
        if (!characterRaceBuffs.isEmpty()) {
            createRow("Rassenbuffs");
            for (String buff : characterRaceBuffs) {
                createRow("Buff", buff);
            }
        }

        List<String> characterRaceDebuffs = MainFrame.character.getRace().getDebuffs();
        if (!characterRaceDebuffs.isEmpty()) {
            createRow("RassenDebuffs");
            for (String debuff : characterRaceDebuffs) {
                createRow("Debuff", debuff);
            }
        }
    }

}
