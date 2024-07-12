package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.Character;

public class StatsTable extends PaintedTable {

    int cellShift;
    int armorCellColumnIndex = 0;
    int sumCellColumnIndex = 0;

    public StatsTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);

        cellShift = sheet.getCellShift();
    }

    @Override
    public void fillTable() {
        Character character = MainFrame.character;

        createRow("Level", character.getLvl());
        sheet.setCurrentLevelCell(sheet.getRow(GetLastRowIndex()).getCell(cellShift + 1));

        createRow("Punkte zu Vergeben", String.valueOf(character.getStatPoints()));

        String[] statsHeaderValues = new String[]{"Stats", "Punkte", "Skills", "Ausrüstung", "Insgesamt"};
        armorCellColumnIndex = 3; //safe the column index for "Ausrüstung"
        sumCellColumnIndex = armorCellColumnIndex + 1; //safe the column index for "Insgesamt"
        createRow(statsHeaderValues, true);

        int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
        createStatRow("HP", totalHP, 1);

        createStatRow("Stärke", character.getStrength(), 2);
        XSSFCell strengthTotalSumCell = getCurrentTotalSumCell(); //remember for substats

        createStatRow("Intelligenz", character.getIntelligence(), 3);
        sheet.setIntelligenceTotalSumCell(getCurrentTotalSumCell()); //remember for spellslots

        createStatRow("Geschick", character.getDexterity(), 4);
        XSSFCell dexSumTotalCell = getCurrentTotalSumCell(); //remember for substats

        //calculatable substats ------------------------------------------------------------------
        String[] subStatsHeaderValues = new String[]{"Substats", "---", "---", "---", "---"};
        createRow(subStatsHeaderValues, true);

        //---positions---
        String strengthSumCellPosition = getCellPosition(strengthTotalSumCell);
        String dexTotalSumCellPosition = getCellPosition(dexSumTotalCell);

        //---rows---
        String formulaArmor = "(" + strengthSumCellPosition + ")";
        createSubstatRow("Rüstung", formulaArmor);
        sheet.getEquipmentCells().put(5, getCurrentArmorSumCell()); //also column in equipment exist

        String formulaMovement = "ROUNDDOWN(" + (dexTotalSumCellPosition) + " / 10 + " + character.getRace().getMovement() + ", 0)";
        createSubstatRow("Bewegung", formulaMovement);

        String formulaDodge = "(" + dexTotalSumCellPosition + ")";
        createSubstatRow("Dodge in %", formulaDodge);

        String formulaIni = "(" + dexTotalSumCellPosition + ")";
        createSubstatRow("Ini-Bonus", formulaIni);

        createRow("Charisma");
        addSumCell();
    }

    private void createStatRow(String header, int pointsValue, int key) {
        createRow(header, pointsValue);
        addSumCell();
        //key is the column of equipment
        sheet.getEquipmentCells().put(key, getCurrentArmorSumCell());
    }

    private void createSubstatRow(String header, String formula) {
        createRow(header);

        XSSFCell armorValueCell = createCellInLastRow(cellShift + 1);
        setFormulaWithLockedStyle(armorValueCell, formula);

        addSumCell();
    }

    //creates a cell at the end of the row
    //punkte + skill + ausrüstung = insgesammt
    private void addSumCell() {
        int rowCount = sheet.getRowCount();
        XSSFCell formulaCell = sheet.getRow(GetLastRowIndex()).createCell(cellShift + sumCellColumnIndex);

        String startCountCell = CellReference.convertNumToColString(cellShift + 1) + rowCount;
        String endCountCell = CellReference.convertNumToColString(cellShift + armorCellColumnIndex) + rowCount;

        setFormulaWithLockedStyle(formulaCell, ("SUM(" + startCountCell + ":" + endCountCell + ")"));
    }

    private XSSFCell getCurrentArmorSumCell() {
        return sheet.getRow(GetLastRowIndex()).createCell(cellShift + armorCellColumnIndex);
    }

    private XSSFCell getCurrentTotalSumCell() {
        return sheet.getRow(GetLastRowIndex()).getCell(cellShift + sumCellColumnIndex);
    }
}
