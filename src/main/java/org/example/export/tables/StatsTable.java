package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.Character;

import java.util.function.Supplier;

public class StatsTable extends PaintedTable {

    public StatsTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {
        Character character = MainFrame.character;

        int cellShift = sheet.getCellShift();

        createRow("Level", character.getLvl());
        sheet.setCurrentLevelCell(sheet.getRow(sheet.getRowCount() - 1).getCell(cellShift + 1));

        createRow("Punkte zu Vergeben", String.valueOf(character.getStatPoints()));

        String[] statsHeaderValues = new String[]{"Stats", "Punkte", "Skills", "Ausrüstung", "Insgesamt"};
        createRow(statsHeaderValues, true);

        //punkte + skill + ausrüstung = insgesammt
        Runnable addSumCell = () -> {
            int rowCount = sheet.getRowCount();
            XSSFCell formulaCell = sheet.getRow(rowCount - 1).createCell(cellShift + statsHeaderValues.length - 1);
            String startCountCell = CellReference.convertNumToColString(cellShift + 1) + rowCount;
            String endCountCell = CellReference.convertNumToColString(cellShift + 3) + rowCount;
            formulaCell.setCellFormula("SUM(" + startCountCell + ":" + endCountCell + ")");
            formulaCell.setCellStyle(sheet.getLockedNumericStyle());
            sheet.getFormulaEvaluator().evaluateFormulaCell(formulaCell);
        };

        Supplier<XSSFCell> getCurrentArmorSumCell = () -> sheet.getRow(sheet.getRowCount() - 1).createCell(cellShift + 3);

        Supplier<XSSFCell> getCurrentTotalSumCell = () -> sheet.getRow(sheet.getRowCount() - 1).getCell(cellShift + statsHeaderValues.length - 1);

        int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
        createRow("HP", totalHP);
        addSumCell.run();
        //--?
        sheet.setHpEquipmentCell(getCurrentArmorSumCell.get());
        sheet.getEquipmentCells().put(1, sheet.getHpEquipmentCell());

        createRow("Stärke", character.getStrength());
        addSumCell.run();
        sheet.setStrengthTotalSumCell(getCurrentTotalSumCell.get()); //remember for substats
        sheet.setStrengthEquipmentCell(getCurrentArmorSumCell.get()); //TODO: maybe refactor
        sheet.getEquipmentCells().put(2, sheet.getStrengthEquipmentCell());

        createRow("Intelligenz", character.getIntelligence());
        addSumCell.run();
        sheet.setIntelligenceTotalSumCell(getCurrentTotalSumCell.get()); //remember for substats
        sheet.setIntelligenceEquipmentCell(getCurrentArmorSumCell.get()); //TODO: maybe refactor
        sheet.getEquipmentCells().put(3, sheet.getIntelligenceEquipmentCell());

        createRow("Geschick", character.getDexterity());
        addSumCell.run();
        sheet.setDexTotalSumCell(getCurrentTotalSumCell.get()); //remember for substats
        sheet.setDexEquipmentCell(getCurrentArmorSumCell.get()); //TODO: maybe refactor
        sheet.getEquipmentCells().put(4, sheet.getDexEquipmentCell());

        //calculatable substats ------------------------------------------------------------------
        createRow("Substats");

        int valueCellNumber = cellShift + 1;

        //--
        createRow("Rüstung");
        XSSFCell armorValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);

        XSSFCell strengthSumCell = sheet.getStrengthTotalSumCell();
        String strengthSumCellPosition = CellReference.convertNumToColString(strengthSumCell.getColumnIndex()) + GetDisplayedExcelRow(strengthSumCell);

        armorValueCell.setCellFormula("(" + strengthSumCellPosition + ")");
        armorValueCell.setCellStyle(sheet.getLockedNumericStyle());
        sheet.getFormulaEvaluator().evaluateFormulaCell(armorValueCell);
        addSumCell.run();

        sheet.setArmorEquipmentCell(getCurrentArmorSumCell.get());
        sheet.getEquipmentCells().put(5, sheet.getArmorEquipmentCell());


        //--
        createRow("Bewegung");
        XSSFCell movementValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);

        XSSFCell dexSumCell = sheet.getDexTotalSumCell();
        String dexTotalSumCellPosition = CellReference.convertNumToColString(dexSumCell.getColumnIndex()) + GetDisplayedExcelRow(dexSumCell);
        movementValueCell.setCellFormula("ROUNDDOWN(" + (dexTotalSumCellPosition) + " / 10 + " + character.getRace().getMovement() + ", 0)");
        movementValueCell.setCellStyle(sheet.getLockedNumericStyle());
        sheet.getFormulaEvaluator().evaluateFormulaCell(movementValueCell);
        addSumCell.run();


        createRow("Dodge in %");
        XSSFCell dodgeValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);

        dodgeValueCell.setCellFormula("(" + dexTotalSumCellPosition + ")");
        dodgeValueCell.setCellStyle(sheet.getLockedNumericStyle());
        sheet.getFormulaEvaluator().evaluateFormulaCell(dodgeValueCell);
        addSumCell.run();

        createRow("Ini-Bonus");
        XSSFCell iniValueCell = sheet.getRow(GetLastRow()).createCell(valueCellNumber);
        iniValueCell.setCellFormula("(" + dexTotalSumCellPosition + ")");
        iniValueCell.setCellStyle(sheet.getLockedNumericStyle());
        sheet.getFormulaEvaluator().evaluateFormulaCell(iniValueCell);
        addSumCell.run();

        createRow("Charisma");


    }
}
