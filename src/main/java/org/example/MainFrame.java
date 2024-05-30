package org.example;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {

    JButton loadButton;

    List<Race> races = new ArrayList<>();

    MainFrame() {

        setTitle("PnP Character Creator");
        setSize(1280, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        loadButton = new JButton("Excel Datei Upload");
        loadButton.setBounds(10, 10, 150, 40);
        loadButton.addActionListener(this);
        add(loadButton);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadButton) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            JFileChooser fileChooser = new JFileChooser(fsv.getHomeDirectory());
            fileChooser.setPreferredSize(new Dimension(1000, 600));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Excel file", "xls", "xlsx");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                try {
                    FileInputStream file = new FileInputStream(fileChooser.getSelectedFile());
                    Workbook workbook = new XSSFWorkbook(file);
                    Sheet raceSheet = workbook.getSheet("Rassen");

                    races = LoadRaces.getRacesFromMap(LoadRaces.getMap(raceSheet));

                    System.out.println(races);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        }


    }
}
