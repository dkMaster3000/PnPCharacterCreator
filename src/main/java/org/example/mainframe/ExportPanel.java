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
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExportPanel extends JPanel implements ActionListener {

    JLabel uploadedLabel;
    Workbook workbook;
    Sheet sheet;

    //startpoints
    int rowCount = 2; //also rowTracker
    int cellShift = 2;

    int startPaintRow = 0;

    int startPaintCell = cellShift - 1;

    CellStyle headerStyle;
    CellStyle valueStyle;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Character character = MainFrame.character;

        UUID uuid = UUID.randomUUID();

        sheet = workbook.createSheet("Char" + character.getRace().getName() + character.getRpgClass().getName() + uuid);

        for (int i = cellShift - 1; i < 10; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

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
            createRow("Stufe", String.valueOf(character.getLvl()));

            String[] statsHeaderValues = new String[]{"Stats", "Punkte", "Skills", "Ausrüstung", "Insgesamt"};
            createRow(statsHeaderValues, true);

            int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
            createRow("HP", totalHP);
            createRow("Stärke", character.getStrength());
            createRow("Intelligenz", character.getIntelligence());
            createRow("Geschick", character.getDexterity());
            createRow("Bewegung", character.getRace().getMovement());
            createRow("Rüstung");
            createRow("Dodge");

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
            String[] spellslotsHeader = new String[]{"Spellslots", "Level", "Wissen", "Gesamt"};
            createRow(spellslotsHeader, true);
            createRow("Einfache");
            createRow("Fortgeschrittene");
            createRow("Expert");
            createRow("Legendäre");

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
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //update text
        uploadedLabel.setText("Excel Datei exportiert");
        uploadedLabel.setForeground(Color.GREEN);

        repaint();

    }

    //---------------------------------------------------------- Row Creation -----------------------------

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
}
