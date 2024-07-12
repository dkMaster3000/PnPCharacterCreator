package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.SpellslotsMatrix;

public class SpellslotsTable extends PaintedTable {

    int lvlCellColumnIndex;
    int wisdomCellColumnIndex;
    int miscellaneousCellColumnIndex;
    int totalSlotsCellColumnIndex;

    SpellslotsMatrix spellslotsMatrix;

    public SpellslotsTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);

        spellslotsMatrix = MainFrame.spellslotsMatrix;

        int cellShift = sheet.getCellShift();
        lvlCellColumnIndex = cellShift + 1;
        wisdomCellColumnIndex = lvlCellColumnIndex + 1;
        miscellaneousCellColumnIndex = wisdomCellColumnIndex + 1;
        totalSlotsCellColumnIndex = miscellaneousCellColumnIndex + 1; //should be last,  previous order important for calculation
    }

    @Override
    public void fillTable() {

        String[] spellslotsHeader = new String[]{"Spellslots", "Level", "Wissen", "Sonstiges", "Gesamt"};
        createRow(spellslotsHeader, true);

        createRow("Einfache");

        createSpellslotRow(spellslotsMatrix.getSimpleKey(), spellslotsMatrix.getSimpleDivider());

        createRow("Fortgeschrittene");

        createSpellslotRow(spellslotsMatrix.getAdvancedkey(), spellslotsMatrix.getAdvancedDivider());

        createRow("Expert");

        createSpellslotRow(spellslotsMatrix.getExpertKey(), spellslotsMatrix.getExpertDivider());

        createRow("Legendäre");

        createSpellslotRow(spellslotsMatrix.getLegendaryKey(), spellslotsMatrix.getLegendaryDivider());
    }

    public void createSpellslotRow(String key, Integer dividend) {
        createLvlCell(key);

        createIntelligenceCell(dividend);

        // create cell for miscellanious
        @SuppressWarnings("unused")
        XSSFCell miscellaneousValueCell = createCellInRow(miscellaneousCellColumnIndex);

        createSpellslotsSumCell();
    }


    private void createLvlCell(String key) {
        //create formular cell for lvl
        XSSFCell lvlValueCell = createCellInRow(lvlCellColumnIndex);

        XSSFCell currentLevelCell = sheet.getCurrentLevelCell();
        String currentLvlCellPosition = getCellPosition(currentLevelCell);

        StringBuilder formularBuilder = new StringBuilder();
        for (Integer lvl : spellslotsMatrix.getSpellslotsMatrix().keySet()) {
            int currentSpellslotAmount = spellslotsMatrix.getSpellslotsMatrix().get(lvl).get(key);
            formularBuilder.append("IF(").append(currentLvlCellPosition).append("=").append(lvl).append(", ").append(currentSpellslotAmount).append(", ");
        }
        formularBuilder.append("0");
        for (Integer _ : spellslotsMatrix.getSpellslotsMatrix().keySet()) {
            formularBuilder.append(")");
        }

        setFormulaWithLockedStyle(lvlValueCell, formularBuilder.toString());
    }

    private void createIntelligenceCell(Integer dividend) {
        //create formalur cell for int
        XSSFCell wisdomValueCell = createCellInRow(wisdomCellColumnIndex);

        XSSFCell intelligenceTotalSumCell = sheet.getIntelligenceTotalSumCell();
        String intelligenceTotalSumCellPosition = getCellPosition(intelligenceTotalSumCell);

        setFormulaWithLockedStyle(wisdomValueCell, ("ROUNDDOWN(" + (intelligenceTotalSumCellPosition) + " / " + dividend + " , 0)"));
    }

    private void createSpellslotsSumCell() {
        // create formular cell for sum
        XSSFCell spellSlotSumValueCell = createCellInRow(totalSlotsCellColumnIndex);

        String startCalc = CellReference.convertNumToColString(lvlCellColumnIndex) + GetDisplayedExcelRow(spellSlotSumValueCell);
        String endCalc = CellReference.convertNumToColString(miscellaneousCellColumnIndex) + GetDisplayedExcelRow(spellSlotSumValueCell);

        setFormulaWithLockedStyle(spellSlotSumValueCell, ("Sum(" + startCalc + ":" + endCalc + ")"));
    }

    //--------------------HELPER--------------------

    private XSSFCell createCellInRow(int index) {
        return sheet.getRow(GetLastRow()).createCell(index);
    }

    private String getCellPosition(XSSFCell cell) {
        return CellReference.convertNumToColString(cell.getColumnIndex()) + GetDisplayedExcelRow(cell);

    }


}
