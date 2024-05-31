package org.example;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    public static Workbook workbook = null;

    CharacterPreviewPanel characterPreviewPanel;
    JPanel characterPreviewBox;
    RaceComboBox raceJComboBox;
    ChoicesPanel choicesPanel;

    public static Character character = new Character();
    public static List<Race> races = new ArrayList<>();

    MainFrame() {

        setTitle("PnP Character Creator");
        setSize(1280, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(null);

        UploadPanel uploadPanel = new UploadPanel(this::onUpload);
        uploadPanel.setBounds(0, 0, 400, 60);
        uploadPanel.setBackground(Color.lightGray);
        add(uploadPanel);

        raceJComboBox = new RaceComboBox(this::updatePanels);
        raceJComboBox.setBounds(5, 60, 150, 40);
        raceJComboBox.setVisible(false);
        add(raceJComboBox);

        choicesPanel = new ChoicesPanel(this::updatePanels);
        choicesPanel.setBounds(0, 105, 300, 40);
        choicesPanel.setBackground(Color.orange);
        choicesPanel.setVisible(false);
        add(choicesPanel);


        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------
        characterPreviewBox = new JPanel();
        characterPreviewBox.setLayout(null);
        characterPreviewBox.setBounds(860, 0, 400, 750);
        characterPreviewBox.setBackground(Color.lightGray);
        add(characterPreviewBox);

        JLabel characterPreviewLabel = new JLabel("Charakter Vorschau");
        characterPreviewLabel.setBounds(5, 5, 400, 10);
        characterPreviewBox.add(characterPreviewLabel);

        characterPreviewPanel = new CharacterPreviewPanel(character);
        characterPreviewPanel.setBounds(5, 20, 390, 700);
        characterPreviewBox.add(characterPreviewPanel);

        characterPreviewBox.validate();

        repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    private void updatePanels() {
        updateMainFrame();
        updatePreviewPanel();
    }

    private void updateMainFrame() {
        choicesPanel.InstantiateChoicesComboBoxes();
    }

    private void updatePreviewPanel() {
        characterPreviewPanel.updateCharacter(character);
    }

    private void onUpload(Workbook workbook) {
        MainFrame.workbook = workbook;

        Sheet raceSheet = workbook.getSheet("Rassen");

        races = LoadRaces.getRacesFromMap(LoadRaces.getMap(raceSheet));

        chooseRace();
        System.out.println(races);
    }


    //load RaceComboBox and make it visible
    private void chooseRace() {
        raceJComboBox.updateRaceComboBox();
        raceJComboBox.setVisible(true);

        choicesPanel.InstantiateChoicesComboBoxes();
        choicesPanel.setVisible(true);

        raceJComboBox.repaint();
    }


}
