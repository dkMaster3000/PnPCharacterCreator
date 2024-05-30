package org.example;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {

    JButton loadButton;
    JLabel uploadedLabel;
    JPanel characterPreview;

    Character character = new Character();
    List<Race> races = new ArrayList<>();

    MainFrame() {

        setTitle("PnP Character Creator");
        setSize(1280, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(null);

        loadButton = new JButton("Excel Datei Upload");
        loadButton.setBounds(10, 10, 150, 40);
        loadButton.addActionListener(this);
        add(loadButton);

        uploadedLabel = new JLabel("Excel Datei noch nicht hochgeladen");
        uploadedLabel.setBounds(170, 10, 250, 40);
        uploadedLabel.setForeground(Color.RED);
        add(uploadedLabel);

        characterPreview = new JPanel(new GridLayout(10, 1, 10, 10));
        characterPreview = new JPanel();
        characterPreview.setBounds(880, 0, 400, 800);
        characterPreview.setBackground(Color.lightGray);
        add(characterPreview);
        JLabel characterPreviewLabel = new JLabel("Charakter Vorschau");
        characterPreviewLabel.setBounds(10, 10, 250, 40);

        characterPreview.add(characterPreviewLabel);

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

                    uploadedLabel.setText("Excel Datei hochgeladen");
                    uploadedLabel.setForeground(Color.GREEN);


                    Sheet raceSheet = workbook.getSheet("Rassen");

                    races = LoadRaces.getRacesFromMap(LoadRaces.getMap(raceSheet));

                    chooseRace();
                    System.out.println(races);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
//      /*  else if(e.getSource() == raceJComboBox){
//
//        }*/

    }

    //after actionPerformed
    private void chooseRace() {
        String[] racesForComboBox = races.stream().map(Race::getName).toArray(String[]::new);
        JComboBox<String> raceJComboBox = new JComboBox<>(racesForComboBox);
        raceJComboBox.setBounds(10, 60, 150, 40);
        raceJComboBox.setSelectedIndex(0);
        raceJComboBox.setVisible(true);
        raceJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(raceJComboBox);
        raceJComboBox.repaint();
    }
}
