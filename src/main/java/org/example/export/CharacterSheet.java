package org.example.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.example.export.tables.*;
import org.example.mainframe.MainFrame;

import java.util.HashMap;
import java.util.UUID;

public class CharacterSheet {

    XSSFSheet sheet;

    //startpoints
    private int rowCount = 2; //row startpoint, also rowTracker
    @SuppressWarnings("FieldCanBeLocal")
    private final int cellShift = 2;


    XSSFWorkbook workbook;
    private final CellStyle headerStyle;
    private final CellStyle valueStyle;
    private final XSSFCellStyle lockedNumericStyle;

    //cells to remember
    //level cell
    XSSFCell currentLevelCell;
    //total sum of intelligence stat
    XSSFCell intelligenceTotalSumCell;
    //stats equipmentcells
    HashMap<Integer, XSSFCell> equipmentCells = new HashMap<>();

    XSSFFormulaEvaluator formulaEvaluator;

    public CharacterSheet() {
        workbook = MainFrame.workbook;

        UUID uuid = UUID.randomUUID();
        sheet = workbook.createSheet("Char" + MainFrame.character.getRace().getName() + MainFrame.character.getRpgClass().getName() + uuid);

        headerStyle = workbook.createCellStyle();
        valueStyle = workbook.createCellStyle();

        XSSFFont fontBold = createFont(true);
        XSSFFont font = createFont(false);

        headerStyle.setFont(fontBold);
        valueStyle.setFont(font);

        lockedNumericStyle = workbook.createCellStyle();
        lockedNumericStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        lockedNumericStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        lockedNumericStyle.setLocked(true);

        for (int i = cellShift - 1; i < 10; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        formulaEvaluator =
                workbook.getCreationHelper().createFormulaEvaluator();

        //generate-------------------

        new GeneralTable(this, IndexedColors.GREY_25_PERCENT).generateTable();

        new StatsTable(this, IndexedColors.LIGHT_YELLOW).generateTable();

        new TalentTable(this, IndexedColors.LIGHT_CORNFLOWER_BLUE).generateTable();

        new PassiveTable(this, IndexedColors.LIGHT_GREEN).generateTable();

        new SpellTable(this, IndexedColors.BLUE_GREY).generateTable();

        new SpellslotsTable(this, IndexedColors.AQUA).generateTable();

        new EquipmentTable(this, IndexedColors.CORAL).generateTable();

        new InventoryTable(this, IndexedColors.LIGHT_YELLOW).generateTable();
    }

    //---------------------------------WRAPPER START----------------------------------
    public XSSFSheet getSheet() {
        return sheet;
    }

    public XSSFRow createRow(int index) {
        return sheet.createRow(index);
    }

    public XSSFRow getRow(int index) {
        return sheet.getRow(index);
    }

    //---------------------------------WRAPPER END----------------------------------

    //---------------------------------STYLE----------------------------------

    private XSSFFont createFont(boolean bold) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(bold);

        return font;
    }

    public CellStyle getValueStyle() {
        return valueStyle;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public XSSFCellStyle getLockedNumericStyle() {
        return lockedNumericStyle;
    }

    public XSSFFormulaEvaluator getFormulaEvaluator() {
        return formulaEvaluator;
    }

    //---------------------------------ROW----------------------------------

    public int getRowCount() {
        return rowCount;
    }

    public void increaseRowCount() {
        rowCount++;
    }

    public int getCellShift() {
        return cellShift;
    }

    //---------------------------------SAVED CELLS----------------------------------

    public XSSFCell getCurrentLevelCell() {
        return currentLevelCell;
    }

    public void setCurrentLevelCell(XSSFCell currentLevelCell) {
        this.currentLevelCell = currentLevelCell;
    }


    public XSSFCell getIntelligenceTotalSumCell() {
        return intelligenceTotalSumCell;
    }

    public void setIntelligenceTotalSumCell(XSSFCell intelligenceTotalSumCell) {
        this.intelligenceTotalSumCell = intelligenceTotalSumCell;
    }

    public HashMap<Integer, XSSFCell> getEquipmentCells() {
        return equipmentCells;
    }


}
