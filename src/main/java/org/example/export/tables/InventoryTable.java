package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.example.export.CharacterSheet;

public class InventoryTable extends PaintedTable {

    public InventoryTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {
        String[] inventoryHeader = new String[]{"Inventar", "Plätze", "5"};
        createRow(inventoryHeader, true);
        createRow("1.");
        createRow("2.");
        createRow("3.");
        createRow("4.");
        createRow("5.");
        createRow("Gold");
        createRow("Waffe 1");
        createRow("Waffe 2");
    }
}
