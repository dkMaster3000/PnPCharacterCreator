package org.example.export;

import org.apache.poi.xssf.usermodel.*;

import org.example.mainframe.MainFrame;
import org.example.mainframe.UpdatableByMainFrame;
import org.example.mainframe.UsedValues;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ExportPanel extends JPanel implements ActionListener, UpdatableByMainFrame {

    JLabel uploadedLabel;

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

    }

    @Override
    public void UpdateByMainFrame() {
        uploadedLabel.setText("Excel Datei noch nicht exportiert");
        uploadedLabel.setForeground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //generate new character excel-sheet in workbook
        new CharacterSheet();

        try {
            FileOutputStream outputStream = new FileOutputStream(MainFrame.file.getAbsolutePath());
            XSSFWorkbook workbook = MainFrame.workbook;
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

}
