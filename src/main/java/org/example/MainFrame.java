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
import java.util.Objects;

public class MainFrame extends JFrame implements ActionListener {

    JButton loadButton;
    JLabel uploadedLabel;
    CharacterPreviewPanel characterPreviewPanel;
    JPanel characterPreviewBox;
    RaceComboBox raceJComboBox = new RaceComboBox();


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

        raceJComboBox = new RaceComboBox();
        raceJComboBox.setBounds(10, 60, 150, 40);
        raceJComboBox.setVisible(false);
        add(raceJComboBox);

        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------
        characterPreviewBox = new JPanel();
        characterPreviewBox.setLayout(null);
        characterPreviewBox.setBounds(880, 0, 400, 800);
        characterPreviewBox.setBackground(Color.lightGray);
        add(characterPreviewBox);

        JLabel characterPreviewLabel = new JLabel("Charakter Vorschau");
        characterPreviewLabel.setBounds(5, 5, 400, 10);
        characterPreviewBox.add(characterPreviewLabel);

        characterPreviewPanel = new CharacterPreviewPanel(character);
        characterPreviewPanel.setBounds(5, 20, 400, 700);
        characterPreviewBox.add(characterPreviewPanel);

        characterPreviewBox.validate();
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
    }


    private void updatePreviewPanel() {
        characterPreviewPanel.updateCharacter(character);
    }


    //load RaceComboBox and make it visible
    private void chooseRace() {
        String[] racesForComboBox = races.stream().map(Race::getName).toArray(String[]::new);

        raceJComboBox.updateRaceComboBox(racesForComboBox, this::onRaceComboBoxChange);
        raceJComboBox.setVisible(true);

        raceJComboBox.repaint();
    }

    private void onRaceComboBoxChange(String newRaceName) {
        for (Race race : races) {
            if (Objects.equals(race.getName(), newRaceName)) {
                character.setRace(race);
            }
        }
        updatePreviewPanel();
    }

}
