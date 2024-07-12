package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.example.export.CharacterSheet;

import java.util.HashMap;

public class EquipmentTable extends PaintedTable {

    public EquipmentTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {

        String[] equipmentHeader = new String[]{"Ausrüstung", "HP", "Stärke", "Intelligenz", "Geschick", "Rüstung", "Magische Effekte"};
        createRow(equipmentHeader, true);

        createRow("Kopf");
        int startEquipmentRow = sheet.getRowCount(); //offset by 1 because excel starts with 1
        createRow("Brust");
        createRow("Arme");
        createRow("Beine");
        createRow("Ring");
        createRow("Amulett");
        int endEquipmentRow = sheet.getRowCount(); //offset by 1 because excel starts with 1

        HashMap<Integer, XSSFCell> equipmentCells = sheet.getEquipmentCells();
        int cellShift = sheet.getCellShift();

        //add sum formular for values in stats
        for (Integer key : equipmentCells.keySet()) {
            String startCountCell = CellReference.convertNumToColString(cellShift + key) + startEquipmentRow;
            String endCountCell = CellReference.convertNumToColString(cellShift + key) + endEquipmentRow;

            XSSFCell equipmentStatSumCell = equipmentCells.get(key); //-> in stat table;

            setFormulaWithLockedStyle(equipmentStatSumCell, ("SUM(" + startCountCell + ":" + endCountCell + ")"));
        }

    }
}
