package org.example.mainframe;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.example.models.Character;
import org.example.models.Talent;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.List;

public class ExportPanel extends JPanel implements ActionListener {

    JLabel uploadedLabel;
    Workbook workbook;
    Sheet sheet;
    int rowCount = 0;

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
        valueStyle.setWrapText(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

//----------------------------------------------------Excel Sheet---------------------------------
        Character character = MainFrame.character;

        String dateTime = new Date().toString().replaceAll(":", "_");

        sheet = workbook.createSheet("Char" + dateTime);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

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

        createRow("");

        createRow("Stuffe", String.valueOf(character.getLvl()));

        createRow("Stats", new String[]{"Punkte", "Skills", "Ausrüstung", "Insgesamt"});

        int totalHP = character.getAddedHP() + Integer.parseInt(character.getRace().getHp());
        createRow("HP", totalHP);
        createRow("Stärke", character.getStrength());
        createRow("Intelligenz", character.getIntelligence());
        createRow("Geschick", character.getDexterity());
        createRow("Bewegung", character.getRace().getMovement());
        createRow("Rüstung");
        createRow("Dodge");

        createRow("");

        createRow("Talente");
        for (Talent talent : character.getTalents()) {
            createRow(talent.name(), talent.description());
        }


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

    public void createRow(String header) {
        createRow(header, new String[]{});
    }

    public void createRow(String header, int value) {
        createRow(header, String.valueOf(value));
    }

    public void createRow(String header, String value) {
        createRow(header, new String[]{value});
    }

    public void createRow(String header, String[] values) {
        Row newRow = sheet.createRow(rowCount);

        Cell headerCell = newRow.createCell(0);
        headerCell.setCellValue(header);
        headerCell.setCellStyle(headerStyle);

        for (int i = 0; i < values.length; i++) {
            Cell cell = newRow.createCell(i + 1);
            cell.setCellValue(values[i]);
            cell.setCellStyle(valueStyle);
        }

        rowCount++;
    }

    public XSSFFont createFont(boolean bold) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) (bold ? 10 : 9));
        font.setBold(bold);

        return font;
    }

}
