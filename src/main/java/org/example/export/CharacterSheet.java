package org.example.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.example.mainframe.MainFrame;

import java.util.HashMap;
import java.util.UUID;

public class CharacterSheet {

    XSSFSheet sheet;

    //startpoints
    private int rowCount = 2; //row startpoint, also rowTracker
    private final int cellShift = 2;


    XSSFWorkbook workbook;
    private CellStyle headerStyle;
    private CellStyle valueStyle;
    private XSSFCellStyle lockedNumericStyle;

    //cells to remember
    //level cell
    XSSFCell currentLevelCell;
    //stats equipmentcells
    XSSFCell hpEquipmentCell;
    XSSFCell strengthEquipmentCell;
    XSSFCell intelligenceEquipmentCell;
    XSSFCell dexEquipmentCell;
    XSSFCell armorEquipmentCell;
    //total sum of specific stats
    XSSFCell strengthTotalSumCell;
    XSSFCell intelligenceTotalSumCell;
    XSSFCell dexTotalSumCell;

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

        formulaEvaluator =
                workbook.getCreationHelper().createFormulaEvaluator();
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

    private XSSFFont createFont(boolean bold) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(bold);

        return font;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void increaseRowCount() {
        rowCount++;
    }

    public int getCellShift() {
        return cellShift;
    }

    public XSSFCellStyle getLockedNumericStyle() {
        return lockedNumericStyle;
    }

    public CellStyle getValueStyle() {
        return valueStyle;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public XSSFCell getCurrentLevelCell() {
        return currentLevelCell;
    }

    public void setCurrentLevelCell(XSSFCell currentLevelCell) {
        this.currentLevelCell = currentLevelCell;
    }

    public XSSFFormulaEvaluator getFormulaEvaluator() {
        return formulaEvaluator;
    }

    public XSSFCell getHpEquipmentCell() {
        return hpEquipmentCell;
    }

    public void setHpEquipmentCell(XSSFCell hpEquipmentCell) {
        this.hpEquipmentCell = hpEquipmentCell;
    }

    public XSSFCell getStrengthEquipmentCell() {
        return strengthEquipmentCell;
    }

    public void setStrengthEquipmentCell(XSSFCell strengthEquipmentCell) {
        this.strengthEquipmentCell = strengthEquipmentCell;
    }

    public XSSFCell getIntelligenceEquipmentCell() {
        return intelligenceEquipmentCell;
    }

    public void setIntelligenceEquipmentCell(XSSFCell intelligenceEquipmentCell) {
        this.intelligenceEquipmentCell = intelligenceEquipmentCell;
    }

    public XSSFCell getDexEquipmentCell() {
        return dexEquipmentCell;
    }

    public void setDexEquipmentCell(XSSFCell dexEquipmentCell) {
        this.dexEquipmentCell = dexEquipmentCell;
    }

    public XSSFCell getArmorEquipmentCell() {
        return armorEquipmentCell;
    }

    public void setArmorEquipmentCell(XSSFCell armorEquipmentCell) {
        this.armorEquipmentCell = armorEquipmentCell;
    }

    public XSSFCell getStrengthTotalSumCell() {
        return strengthTotalSumCell;
    }

    public void setStrengthTotalSumCell(XSSFCell strengthTotalSumCell) {
        this.strengthTotalSumCell = strengthTotalSumCell;
    }

    public XSSFCell getIntelligenceTotalSumCell() {
        return intelligenceTotalSumCell;
    }

    public void setIntelligenceTotalSumCell(XSSFCell intelligenceTotalSumCell) {
        this.intelligenceTotalSumCell = intelligenceTotalSumCell;
    }

    public XSSFCell getDexTotalSumCell() {
        return dexTotalSumCell;
    }

    public void setDexTotalSumCell(XSSFCell dexTotalSumCell) {
        this.dexTotalSumCell = dexTotalSumCell;
    }

    public HashMap<Integer, XSSFCell> getEquipmentCells() {
        return equipmentCells;
    }


}
