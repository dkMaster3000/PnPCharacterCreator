package org.example.export.tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;

import java.util.function.Consumer;

public abstract class PaintedTable {

    protected int startPaintRowIndex = 0;
    protected int endPaintRowIndex = 0;
    protected int startPaintCellIndex = 0;
    protected int endPaintCellIndex = 0;

    CharacterSheet sheet;

    CellStyle paintStyle;

    public PaintedTable(CharacterSheet sheet, IndexedColors indexedColors) {
        this.sheet = sheet;

        paintStyle = MainFrame.workbook.createCellStyle();
        paintStyle.setFillForegroundColor(indexedColors.getIndex());
        paintStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public void generateTable() {
        startTable();

        fillTable();

        endTable();
    }

    protected abstract void fillTable();

    private void startTable() {
        createRow("");
        startPaintRowIndex = sheet.getRowCount() - 1;
        startPaintCellIndex = sheet.getCellShift() - 1;
    }

    private void endTable() {
        endPaintRowIndex = sheet.getRowCount();
        endPaintCellIndex += 1;

        paintTable();
    }

    private void paintTable() {

        for (int i = startPaintRowIndex; i <= endPaintRowIndex; i++) {
            Consumer<Row> paintWholeRow = row -> {
                for (int j = startPaintCellIndex; j <= endPaintCellIndex; j++) {
                    Cell newPaintCell = row.createCell(j);
                    newPaintCell.setCellStyle(paintStyle);
                }
            };

            if (i == startPaintRowIndex) {
                paintWholeRow.accept(sheet.getRow(startPaintRowIndex));

            } else if (i == endPaintRowIndex) {
                createRow(paintWholeRow);

            } else {
                Row currentRow = sheet.getRow(i);
                currentRow.createCell(startPaintCellIndex).setCellStyle(paintStyle);
                currentRow.createCell(endPaintCellIndex).setCellStyle(paintStyle);
            }
        }
    }

    //---------------------------------------------------------- Row Creation -----------------------------

    protected void createRow(String header, int totalHP) {
        createRow((Row newRow) -> {

            Cell headerCell = createCell(newRow, sheet.getCellShift());
            headerCell.setCellValue(header);
            headerCell.setCellStyle(sheet.getHeaderStyle());

            Cell cell = createCell(newRow, 1 + sheet.getCellShift());
            cell.setCellValue(totalHP);
            cell.setCellStyle(sheet.getValueStyle());

        });
    }

    protected void createRow(String header) {
        createRow(header, new String[]{});
    }

    protected void createRow(String header, String value) {
        createRow(header, new String[]{value});
    }

    protected void createRow(String header, String[] values) {
        createRow((Row newRow) -> {

            Cell headerCell = createCell(newRow, sheet.getCellShift());
            headerCell.setCellValue(header);
            headerCell.setCellStyle(sheet.getHeaderStyle());

            for (int i = 0; i < values.length; i++) {
                Cell cell = createCell(newRow, i + 1 + sheet.getCellShift());
                cell.setCellValue(values[i]);
                cell.setCellStyle(sheet.getValueStyle());
            }

        });
    }


    protected void createRow(String[] values, boolean isHeader) {
        createRow((Row newRow) -> {

            for (int i = 0; i < values.length; i++) {
                Cell headerCell = createCell(newRow, i + sheet.getCellShift());
                headerCell.setCellValue(values[i]);
                headerCell.setCellStyle(isHeader ? sheet.getHeaderStyle() : sheet.getValueStyle());
            }

        });
    }

    protected void createRow(Consumer<Row> fn) {
        Row newRow = sheet.createRow(sheet.getRowCount());

        fn.accept(newRow);

        sheet.increaseRowCount();
    }

    private Cell createCell(Row row, int index) {
        endPaintCellIndex = Math.max(endPaintCellIndex, index); //-> auto check where is the end point in width

        return row.createCell(index);
    }

    //-----------------HELPER

    protected int GetDisplayedExcelRow(XSSFCell cell) {
        return cell.getRowIndex() + 1;
    }

    protected int GetLastRowIndex() {
        return sheet.getRowCount() - 1;
    }

    protected XSSFCell createCellInLastRow(int index) {
        return sheet.getRow(GetLastRowIndex()).createCell(index);
    }

    protected String getCellPosition(XSSFCell cell) {
        return CellReference.convertNumToColString(cell.getColumnIndex()) + GetDisplayedExcelRow(cell);

    }

    protected void setFormulaWithLockedStyle(XSSFCell cell, String formula) {
        cell.setCellFormula(formula);
        cell.setCellStyle(sheet.getLockedNumericStyle());
        sheet.getFormulaEvaluator().evaluateFormulaCell(cell);
    }

}
