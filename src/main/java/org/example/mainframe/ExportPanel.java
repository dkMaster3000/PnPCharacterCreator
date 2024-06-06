package org.example.mainframe;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class ExportPanel extends JPanel implements ActionListener {

    public interface UploadPanelFunc {
        void onUpload(Workbook workbook);
    }

    JLabel uploadedLabel;
    Workbook workbook;
    UploadPanelFunc onUpload;

    ExportPanel(UploadPanelFunc onUpload) {
        this.onUpload = onUpload;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        workbook = MainFrame.workbook;

        String dateTime = new Date().toString().replaceAll(":", "_");

        Sheet sheet = workbook.createSheet("Persons" + dateTime);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        uploadedLabel.setText("Excel Datei exportiert");
        uploadedLabel.setForeground(Color.GREEN);

        repaint();

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);

        try {
            FileOutputStream outputStream = new FileOutputStream(MainFrame.file.getAbsolutePath());
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

}
