package org.example.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import org.example.export.tables.*;
import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatableByMainFrame;
import org.example.mainframe.UsedValues;
import org.example.models.*;
import org.example.models.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExportPanel extends JPanel implements ActionListener, UpdatableByMainFrame {

    JLabel uploadedLabel;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    //startpoints
    private final int rowStart = 2;
    private int rowCount = rowStart; //also rowTracker
    private final int cellShift = 2;

    private int startPaintRow = 0;

    private final int startPaintCell = cellShift - 1;

    CellStyle headerStyle;
    CellStyle valueStyle;
    XSSFCellStyle lockedNumericStyle;

    //cells to remember
    //level cell
    XSSFCell currentLevelCell;
    //stats equipmentcells
    XSSFCell hpEquipment;
    XSSFCell strengthEquipment;
    XSSFCell intelligenceEquipment;
    XSSFCell dexEquipment;
    XSSFCell armorEquipment;
    //total sum of specific stats
    XSSFCell strengthTotalSum;
    XSSFCell intelligenceTotalSum;
    XSSFCell dexTotalSum;

    HashMap<Integer, XSSFCell> equipmentCells = new HashMap<>();

    public ExportPanel() {
        Dimension thisSize = new Dimension(1000, 60);
        setMinimumSize(thisSize);
        setMaximumSize(thisSize);
        setPreferredSize(thisSize);

        setLayout(null);

        JButton loadButton = new JButton("Export Character");
        loadButton.setBounds(10, 10, 150, 40);
        loadButton.addActionListener(this);
        add(loadButton);

        uploadedLabel = new JLabel("Excel Datei noch nicht exportiert");
        uploadedLabel.setBounds(170, 10, 210, 40);
        uploadedLabel.setForeground(Color.RED);
        add(uploadedLabel);

        workbook = MainFrame.workbook;
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
    }

    @Override
    public void UpdateByMainFrame() {
        uploadedLabel.setText("Excel Datei noch nicht exportiert");
        uploadedLabel.setForeground(Color.RED);

        rowCount = rowStart;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        CharacterSheet CUSTOM_SHEET = new CharacterSheet();


        new GeneralTable(CUSTOM_SHEET, IndexedColors.GREY_25_PERCENT).generateTable();

        new StatsTable(CUSTOM_SHEET, IndexedColors.LIGHT_YELLOW).generateTable();

        new TalentTable(CUSTOM_SHEET, IndexedColors.LIGHT_CORNFLOWER_BLUE).generateTable();

        new PassiveTable(CUSTOM_SHEET, IndexedColors.LIGHT_GREEN).generateTable();

        new SpellTable(CUSTOM_SHEET, IndexedColors.BLUE_GREY).generateTable();

        new SpellslotsTable(CUSTOM_SHEET, IndexedColors.AQUA).generateTable();


//
//        createTabel(IndexedColors.CORAL, () -> {
//            String[] equipmentHeader = new String[]{"Ausrüstung", "HP", "Stärke", "Intelligenz", "Geschick", "Rüstung", "Magische Effekte"};
//            createRow(equipmentHeader, true);
//            createRow("Kopf");
//            createRow("Brust");
//            createRow("Arme");
//            createRow("Beine");
//            createRow("Ring");
//            createRow("Amulett");
//
//            //add sum formular for values in stats
//            for (Integer key : equipmentCells.keySet()) {
//                String startCountCell = CellReference.convertNumToColString(cellShift + key) + (rowCount - 5);
//                String endCountCell = CellReference.convertNumToColString(cellShift + key) + rowCount;
//                equipmentCells.get(key).setCellFormula("SUM(" + startCountCell + ":" + endCountCell + ")");
//                equipmentCells.get(key).setCellStyle(lockedNumericStyle);
//                formulaEvaluator.evaluateFormulaCell(hpEquipment);
//            }
//
//            return equipmentHeader.length;
//        });
//
//        createTabel(IndexedColors.LIGHT_YELLOW, () -> {
//            String[] inventoryHeader = new String[]{"Inventar", "Plätze", "5"};
//            createRow(inventoryHeader, true);
//            createRow("1.");
//            createRow("2.");
//            createRow("3.");
//            createRow("4.");
//            createRow("5.");
//            createRow("Gold");
//            createRow("Waffe 1");
//            createRow("Waffe 2");
//
//            return inventoryHeader.length;
//        });


        try {
            FileOutputStream outputStream = new FileOutputStream(MainFrame.file.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //update text
        uploadedLabel.setText("Excel Datei exportiert");
        uploadedLabel.setForeground(UsedValues.DARK_GREEN);

        repaint();

    }


    //---------------------------------------------------------- Row Creation -----------------------------

    private void createRow(String header, int totalHP) {
        createRow((Row newRow) -> {

            Cell headerCell = newRow.createCell(cellShift);
            headerCell.setCellValue(header);
            headerCell.setCellStyle(headerStyle);

            Cell cell = newRow.createCell(1 + cellShift);
            cell.setCellValue(totalHP);
            cell.setCellStyle(valueStyle);


        });
    }

    private void createRow(String header) {
        createRow(header, new String[]{});
    }

    private void createRow(String header, String value) {
        createRow(header, new String[]{value});
    }

    private void createRow(String header, String[] values) {
        createRow((Row newRow) -> {

            Cell headerCell = newRow.createCell(cellShift);
            headerCell.setCellValue(header);
            headerCell.setCellStyle(headerStyle);

            for (int i = 0; i < values.length; i++) {
                Cell cell = newRow.createCell(i + 1 + cellShift);
                cell.setCellValue(values[i]);
                cell.setCellStyle(valueStyle);
            }

        });
    }

    private void createRow(String[] values, boolean isHeader) {
        createRow((Row newRow) -> {

            for (int i = 0; i < values.length; i++) {
                Cell headerCell = newRow.createCell(i + cellShift);
                headerCell.setCellValue(values[i]);
                headerCell.setCellStyle(isHeader ? headerStyle : valueStyle);
            }

        });
    }

    private void createRow(Consumer<Row> fn) {
        Row newRow = sheet.createRow(rowCount);

        fn.accept(newRow);

        rowCount++;
    }

    private XSSFFont createFont(boolean bold) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(bold);

        return font;
    }

    //---------------------------------------------------------- Table Creation -----------------------------
    private void createTabel(IndexedColors indexedColors, Supplier<Integer> table) {
        beginnNewTable();

        int itemsAmount = table.get();

        paintTable(itemsAmount, indexedColors);
    }

    private void beginnNewTable() {
        createRow("");
        startPaintRow = rowCount - 1;
    }

    private void paintTable(int itemsAmount, IndexedColors indexedColors) {

        CellStyle paintStyle = workbook.createCellStyle();
        paintStyle.setFillForegroundColor(indexedColors.getIndex());
        paintStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int tabelEndRow = rowCount;
        int endPaintCell = startPaintCell + itemsAmount + 1;


        for (int i = startPaintRow; i <= tabelEndRow; i++) {
            Consumer<Row> paintWholeRow = row -> {
                for (int j = startPaintCell; j <= endPaintCell; j++) {
                    Cell newPaintCell = row.createCell(j);
                    newPaintCell.setCellStyle(paintStyle);
                }
            };

            if (i == startPaintRow) {
                paintWholeRow.accept(sheet.getRow(startPaintRow));

            } else if (i == tabelEndRow) {
                createRow(paintWholeRow);

            } else {
                Row currentRow = sheet.getRow(i);
                currentRow.createCell(startPaintCell).setCellStyle(paintStyle);
                currentRow.createCell(endPaintCell).setCellStyle(paintStyle);
            }
        }
    }

    //---------------------------------------------------------- Utils -----------------------------
    private int GetDisplayedExcelRow(XSSFCell cell) {
        return cell.getRowIndex() + 1;
    }

    private int GetLastRow() {
        return rowCount - 1;
    }


}