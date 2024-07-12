package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.Passiv;

import java.util.List;

public class PassiveTable extends PaintedTable {

    public PassiveTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {

        List<Passiv> allCharacterPassivs = MainFrame.character.getAllCharacterPassivs();

        String[] passivsHeader = new String[]{"Name", "Effekt", "Reichweite"};
        if (!allCharacterPassivs.isEmpty()) {
            createRow("Passivs");
            createRow(passivsHeader, true);
            for (Passiv passiv : allCharacterPassivs) {
                createRow(new String[]{
                        passiv.getName(),
                        passiv.getEffect(),
                        passiv.getRange()}, false);
            }
        }
    }
}
