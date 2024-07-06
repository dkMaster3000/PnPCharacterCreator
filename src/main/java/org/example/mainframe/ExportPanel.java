package org.example.mainframe;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

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

    ExportPanel() {
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

        Character character = MainFrame.character;

        UUID uuid = UUID.randomUUID();

        sheet = workbook.createSheet("Char" + character.getRace().getName() + character.getRpgClass().getName() + uuid);


        for (int i = cellShift - 1; i < 10; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        XSSFFormulaEvaluator formulaEvaluator =
                workbook.getCreationHelper().createFormulaEvaluator();

        createTabel(IndexedColors.GREY_25_PERCENT, () -> {
            createRow("Vorname");
            createRow("Nachname");
            createRow("Ruf");
            createRow("Alter");
            createRow("Sonstiges");

            createRow("Klasse", character.getRpgClass().getName());
            createRow("Rasse", character.getRace().getName());
            createRow("Gewicht");
            createRow("Größe");

            List<String> characterRaceBuffs = character.getAllBuffs();
            if (!characterRaceBuffs.isEmpty()) {
                createRow("Rassenbuffs");
                for (String buff : characterRaceBuffs) {
                    createRow("Buff", buff);
                }
            }

            List<String> characterRaceDebuffs = character.getRace().getDebuffs();
            if (!characterRaceDebuffs.isEmpty()) {
                createRow("RassenDebuffs");
                for (String debuff : characterRaceDebuffs) {
                    createRow("Debuff", debuff);
                }
            }

            return 2;
        });

        createTabel(IndexedColors.LIGHT_YELLOW, () -> {

            createRow("Level", character.getLvl());
            currentLevelCell = sheet.getRow(rowCount - 1).getCell(cellShift + 1);
            createRow("Punkte zu Vergeben", String.valueOf(character.getStatPoints()));

            String[] statsHeaderValues = new String[]{"Stats", "Punkte", "Skills", "Ausrüstung", "Insgesamt"};
            createRow(statsHeaderValues, true);

            //punkte + skill + ausrüstung = insgesammt
            Runnable addSumCell = () -> {
                XSSFCell formulaCell = sheet.getRow(rowCount - 1).createCell(cellShift + statsHeaderValues.length - 1);
                String startCountCell = CellReference.convertNumToColString(cellShift + 1) + rowCount;
                String endCountCell = CellReference.convertNumToColString(cellShift + 3) + rowCount;
                formulaCell.setCellFormula("SUM(" + startCountCell + ":" + endCountCell + ")");
                formulaCell.setCellStyle(lockedNumericStyle);
                formulaEvaluator.evaluateFormulaCell(formulaCell);
            };

            Supplier<XSSFCell> getCurrentArmorSumCell = () -> sheet.getRow(rowCount - 1).createCell(cellShift + 3);

            Supplier<XSSFCell> getCurrentTotalSumCell = () -> sheet.getRow(rowCount - 1).getCell(cellShift + statsHeaderValues.length - 1);

            int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
            createRow("HP", totalHP);
            addSumCell.run();
            hpEquipment = getCurrentArmorSumCell.get();
            equipmentCells.put(1, hpEquipment);

            createRow("Stärke", character.getStrength());
            addSumCell.run();
            strengthTotalSum = getCurrentTotalSumCell.get();
            strengthEquipment = getCurrentArmorSumCell.get();
            equipmentCells.put(2, strengthEquipment);

            createRow("Intelligenz", character.getIntelligence());
            addSumCell.run();
            intelligenceTotalSum = getCurrentTotalSumCell.get();
            intelligenceEquipment = getCurrentArmorSumCell.get();
            equipmentCells.put(3, intelligenceEquipment);

            createRow("Geschick", character.getDexterity());
            addSumCell.run();
            dexTotalSum = getCurrentTotalSumCell.get();
            dexEquipment = getCurrentArmorSumCell.get();
            equipmentCells.put(4, dexEquipment);

            //calculatable substats ------------------------------------------------------------------
            createRow("Substats");

            int valueCellNumber = cellShift + 1;

            createRow("Rüstung");
            XSSFCell armorValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);

            String strengthSumCell = CellReference.convertNumToColString(strengthTotalSum.getColumnIndex()) + GetDisplayedExcelRow(strengthTotalSum);

            armorValueCell.setCellFormula("(" + strengthSumCell + ")");
            armorValueCell.setCellStyle(lockedNumericStyle);
            formulaEvaluator.evaluateFormulaCell(armorValueCell);

            addSumCell.run();
            armorEquipment = getCurrentArmorSumCell.get();
            equipmentCells.put(5, armorEquipment);


            createRow("Bewegung");
            XSSFCell movementValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);
            String dexTotalSumCellID = CellReference.convertNumToColString(dexTotalSum.getColumnIndex()) + GetDisplayedExcelRow(dexTotalSum);
            movementValueCell.setCellFormula("ROUNDDOWN(" + (dexTotalSumCellID) + " / 10 + " + character.getRace().getMovement() + ", 0)");
            movementValueCell.setCellStyle(lockedNumericStyle);
            formulaEvaluator.evaluateFormulaCell(movementValueCell);
            addSumCell.run();


            createRow("Dodge in %");
            XSSFCell dodgeValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);
            dodgeValueCell.setCellFormula("(" + dexTotalSumCellID + ")");
            dodgeValueCell.setCellStyle(lockedNumericStyle);
            formulaEvaluator.evaluateFormulaCell(dodgeValueCell);
            addSumCell.run();

            createRow("Ini-Bonus");
            XSSFCell iniValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);
            iniValueCell.setCellFormula("(" + dexTotalSumCellID + ")");
            iniValueCell.setCellStyle(lockedNumericStyle);
            formulaEvaluator.evaluateFormulaCell(iniValueCell);
            addSumCell.run();

            createRow("Charisma");

            return statsHeaderValues.length;
        });


        createTabel(IndexedColors.LIGHT_CORNFLOWER_BLUE, () -> {
            List<Talent> talents = character.getTalents();
            String[] talentsHeader = new String[]{"Talente", "Name", "Effekt"};
            if (!talents.isEmpty()) {
                createRow(talentsHeader, true);
                for (int i = 0; i < talents.size(); i++) {
                    Talent talent = talents.get(i);
                    createRow(new String[]{
                            String.valueOf(i + 1),
                            talent.name(),
                            talent.description(),
                    }, false);
                }
            }

            return talentsHeader.length;
        });

        createTabel(IndexedColors.LIGHT_GREEN, () -> {
            List<Passiv> allCharacterPassivs = character.getAllCharacterPassivs();
            String[] passivsHeader = new String[]{"Passivs", "Name", "Effekt", "Reichweite"};
            if (!allCharacterPassivs.isEmpty()) {
                createRow(passivsHeader, true);

                for (int i = 0; i < allCharacterPassivs.size(); i++) {
                    Passiv passiv = allCharacterPassivs.get(i);
                    createRow(new String[]{
                            String.valueOf(i + 1),
                            passiv.getName(),
                            passiv.getEffect(),
                            passiv.getRange()}, false);
                }
            }

            return passivsHeader.length;
        });


        createTabel(IndexedColors.BLUE_GREY, () -> {
            List<Spell> allCharacterSpells = character.getAllCharacterSpells();
            String[] spellHeader = new String[]{"Zauber", "Name", "Art", "Schnelligkeit", "Effekt", "Reichweite"};
            if (!allCharacterSpells.isEmpty()) {
                createRow(spellHeader, true);

                for (int i = 0; i < allCharacterSpells.size(); i++) {
                    Spell spell = allCharacterSpells.get(i);
                    createRow(new String[]{
                            String.valueOf(i + 1),
                            spell.getName(),
                            spell.getDifficulty(),
                            spell.getTempo(),
                            spell.getEffect(),
                            spell.getRange()}, false);
                }
            }

            return spellHeader.length;
        });


        createTabel(IndexedColors.AQUA, () -> {
            int lvlCellColumn = cellShift + 1;
            int wisdomCellColumn = cellShift + 2;
            int miscellaneousCellColumn = cellShift + 3;
            int totalSlotsCellColumn = cellShift + 4;

            String[] spellslotsHeader = new String[]{"Spellslots", "Level", "Wissen", "Sonstiges", "Gesamt"};
            createRow(spellslotsHeader, true);

            SpellslotsMatrix spellslotsMatrix = MainFrame.spellslotsMatrix;
            BiConsumer<String, Integer> createSpellslotRow = (key, dividend) -> {
                //create formular cell for lvl
                XSSFCell lvlValueCell = sheet.getRow(GetLastRow()).createCell(lvlCellColumn);
                String currentLvlCellID = CellReference.convertNumToColString(currentLevelCell.getColumnIndex()) + GetDisplayedExcelRow(currentLevelCell);
                StringBuilder formularBuilder = new StringBuilder();
                for (Integer lvl : spellslotsMatrix.getSpellslotsMatrix().keySet()) {
                    formularBuilder.append("IF(").append(currentLvlCellID).append("=").append(lvl).append(", ").append(spellslotsMatrix.getSpellslotsMatrix().get(lvl).get(key)).append(", ");
                }
                formularBuilder.append("0");
                for (Integer _ : spellslotsMatrix.getSpellslotsMatrix().keySet()) {
                    formularBuilder.append(")");
                }

                lvlValueCell.setCellFormula(formularBuilder.toString());
                lvlValueCell.setCellStyle(lockedNumericStyle);
                formulaEvaluator.evaluateFormulaCell(lvlValueCell);

                //create formalur cell for int
                XSSFCell wisdomValueCell = sheet.getRow(GetLastRow()).createCell(wisdomCellColumn);
                String intelligenceTotalSumCellID = CellReference.convertNumToColString(intelligenceTotalSum.getColumnIndex()) + GetDisplayedExcelRow(intelligenceTotalSum);
                wisdomValueCell.setCellFormula("ROUNDDOWN(" + (intelligenceTotalSumCellID) + " / " + dividend + " , 0)");
                wisdomValueCell.setCellStyle(lockedNumericStyle);
                formulaEvaluator.evaluateFormulaCell(wisdomValueCell);

                // create cell for miscellanious
                @SuppressWarnings("unused")
                XSSFCell miscellaneousValueCell = sheet.getRow(GetLastRow()).createCell(miscellaneousCellColumn);

                // create formular cell for sum
                XSSFCell spellSlotSumValueCell = sheet.getRow(GetLastRow()).createCell(totalSlotsCellColumn);
                String startCalc = CellReference.convertNumToColString(lvlCellColumn) + GetDisplayedExcelRow(spellSlotSumValueCell);
                String endCalc = CellReference.convertNumToColString(miscellaneousCellColumn) + GetDisplayedExcelRow(spellSlotSumValueCell);
                spellSlotSumValueCell.setCellFormula("Sum(" + startCalc + ":" + endCalc + ")");
                spellSlotSumValueCell.setCellStyle(lockedNumericStyle);
                formulaEvaluator.evaluateFormulaCell(spellSlotSumValueCell);
            };

            createRow("Einfache");

            createSpellslotRow.accept(spellslotsMatrix.getSimpleKey(), spellslotsMatrix.getSimpleDivider());

            createRow("Fortgeschrittene");

            createSpellslotRow.accept(spellslotsMatrix.getAdvancedkey(), spellslotsMatrix.getAdvancedDivider());

            createRow("Expert");

            createSpellslotRow.accept(spellslotsMatrix.getExpertKey(), spellslotsMatrix.getExpertDivider());

            createRow("Legendäre");

            createSpellslotRow.accept(spellslotsMatrix.getLegendaryKey(), spellslotsMatrix.getLegendarDivider());

            return spellslotsHeader.length;
        });

        createTabel(IndexedColors.CORAL, () -> {
            String[] equipmentHeader = new String[]{"Ausrüstung", "HP", "Stärke", "Intelligenz", "Geschick", "Rüstung", "Magische Effekte"};
            createRow(equipmentHeader, true);
            createRow("Kopf");
            createRow("Brust");
            createRow("Arme");
            createRow("Beine");
            createRow("Ring");
            createRow("Amulett");

            //add sum formular for values in stats
            for (Integer key : equipmentCells.keySet()) {
                String startCountCell = CellReference.convertNumToColString(cellShift + key) + (rowCount - 5);
                String endCountCell = CellReference.convertNumToColString(cellShift + key) + rowCount;
                equipmentCells.get(key).setCellFormula("SUM(" + startCountCell + ":" + endCountCell + ")");
                equipmentCells.get(key).setCellStyle(lockedNumericStyle);
                formulaEvaluator.evaluateFormulaCell(hpEquipment);
            }

            return equipmentHeader.length;
        });

        createTabel(IndexedColors.LIGHT_YELLOW, () -> {
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

            return inventoryHeader.length;
        });


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
