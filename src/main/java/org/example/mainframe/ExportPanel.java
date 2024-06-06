package org.example.mainframe;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.example.models.Character;
import org.example.models.Passiv;
import org.example.models.Spell;
import org.example.models.Talent;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class ExportPanel extends JPanel implements ActionListener {

    JLabel uploadedLabel;
    Workbook workbook;
    Sheet sheet;
    int rowCount = 2;
    int cellShift = 2;

    CellStyle headerStyle;
    CellStyle valueStyle;

    IndexedColors indexedColors = IndexedColors.LIGHT_BLUE;

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

        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        valueStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        valueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    private void createNewHeaderStyle() {
        XSSFFont font = createFont(true);
        CellStyle style = workbook.createCellStyle();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

//----------------------------------------------------Excel Sheet---------------------------------
        Character character = MainFrame.character;

        String dateTime = new Date().toString().replaceAll(":", "_");

        sheet = workbook.createSheet("Char" + character.getRace().getName() + character.getRpgClass().getName() + dateTime);

        for (int i = cellShift; i < 9; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        createRow("Vorname");
        createRow("Nachname");
        createRow("Ruf");
        createRow("Alter");
        createRow("Sonstiges");

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
                createRow("Buff", debuff);
            }
        }

        createBreak();

        setNewBackgroundColor(IndexedColors.LIGHT_YELLOW);

        createRow("Stuffe", String.valueOf(character.getLvl()));

        createRow(new String[]{"Stats", "Punkte", "Skills", "Ausrüstung", "Insgesamt"}, true);

        int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
        createRow("HP", totalHP);
        createRow("Stärke", character.getStrength());
        createRow("Intelligenz", character.getIntelligence());
        createRow("Geschick", character.getDexterity());
        createRow("Bewegung", character.getRace().getMovement());
        createRow("Rüstung");
        createRow("Dodge");

        createBreak();

        List<Talent> talente = character.getTalents();
        if (!talente.isEmpty()) {
            createRow(new String[]{"Talente", "Name", "Effekt"}, true);
            for (int i = 0; i < talente.size(); i++) {
                Talent talent = talente.get(i);
                createRow(new String[]{
                        String.valueOf(i + 1),
                        talent.name(),
                        talent.description(),
                }, false);
            }
        }

        createBreak();

        createRow("Klasse", character.getRpgClass().getName());

        List<Passiv> allCharacterPassivs = character.getAllCharacterPassivs();
        if (!allCharacterPassivs.isEmpty()) {
            createRow(new String[]{"Passivs", "Name", "Effekt", "Reichweite"}, true);

            for (int i = 0; i < allCharacterPassivs.size(); i++) {
                Passiv passiv = allCharacterPassivs.get(i);
                createRow(new String[]{
                        String.valueOf(i + 1),
                        passiv.getName(),
                        passiv.getEffect(),
                        passiv.getRange()}, false);
            }
        }

        createBreak();

        List<Spell> allCharacterSpells = character.getAllCharacterSpells();
        if (!allCharacterSpells.isEmpty()) {
            createRow(new String[]{"Zauber", "Name", "Art", "Schnelligkeit", "Effekt", "Reichweite"}, true);

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

        createBreak();

        createRow(new String[]{"Spellslots", "Level", "Wissen", "Gesamt"}, true);
        createRow("Einfache");
        createRow("Fortgeschrittene");
        createRow("Expert");
        createRow("Legendäre");

        createBreak();

        createRow(new String[]{"Ausrüstung", "HP", "Stärke", "Intelligenz", "Geschick", "Rüstung", "Magische Effekte"}, true);
        createRow("Kopf");
        createRow("Brust");
        createRow("Arme");
        createRow("Beine");
        createRow("Ring");
        createRow("Amulett");

        createBreak();

        createRow(new String[]{"Inventar", "Plätze", "5"}, true);
        createRow("1.");
        createRow("2.");
        createRow("3.");
        createRow("4.");
        createRow("5.");
        createRow("Gold");
        createRow("Waffe 1");
        createRow("Waffe 2");

        try {
            FileOutputStream outputStream = new FileOutputStream(MainFrame.file.getAbsolutePath());
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //update text
        uploadedLabel.setText("Excel Datei exportiert");
        uploadedLabel.setForeground(Color.GREEN);

        repaint();

    }

    private void createBreak() {
        createRow("");
        createRow("");
    }

    private void createRow(String header) {
        createRow(header, new String[]{});
    }

    private void createRow(String header, int value) {
        createRow(header, String.valueOf(value));
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
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(bold);

        return font;
    }

    private void setNewBackgroundColor(IndexedColors indexedColors) {
        headerStyle.setFillForegroundColor(indexedColors.getIndex());
        valueStyle.setFillForegroundColor(indexedColors.getIndex());
    }

}
