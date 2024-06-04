package org.example.mainframe;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.loaders.RPGClassLoader;
import org.example.loaders.RaceLoader;
import org.example.loaders.TalentLoader;
import org.example.models.Character;
import org.example.models.RPGClass;
import org.example.models.Race;
import org.example.models.TalentMatrix;
import org.example.preview.CharacterPreviewPanel;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    public static Workbook workbook = null;

    CharacterPreviewPanel characterPreviewPanel;
    JPanel characterPreviewBox;
    RaceComboBox raceJComboBox;
    LvlPanel lvlPanel;
    RaceChoicesPanel raceChoicesPanel;
    TalentsPanel talentsPanel;
    RPGClassPanel rpgClassPanel;

    public static Character character = new Character();
    public static List<Race> races = new ArrayList<>();
    public static TalentMatrix talentMatrix = null;
    public static List<RPGClass> rpgClasses = null;

    //STEP: 1
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

        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------

        characterPreviewBox = new JPanel();
        characterPreviewBox.setLayout(null);
        characterPreviewBox.setBounds(860, 0, 400, 750);
        characterPreviewBox.setBackground(Color.lightGray);
        add(characterPreviewBox);

        JLabel characterPreviewLabel = new JLabel("Charakter Vorschau");
        characterPreviewLabel.setBounds(5, 5, 400, 10);
        characterPreviewBox.add(characterPreviewLabel);

        characterPreviewPanel = new CharacterPreviewPanel();
        characterPreviewPanel.setBounds(5, 20, 390, 700);
        characterPreviewBox.add(characterPreviewPanel);

        characterPreviewBox.validate();

        repaint();
    }

    //STEP: 2
    //invoked by upload button
    private void onUpload(Workbook workbook) {
        MainFrame.workbook = workbook;

        races = RaceLoader.getRaces();
        talentMatrix = TalentLoader.getTalentMatrix();
        rpgClasses = RPGClassLoader.getRPGClass();

        createPanelsAfterUpload();
    }

    //STEP: 3
    private void createPanelsAfterUpload() {
        raceJComboBox = new RaceComboBox(this::updatePanels);
        raceJComboBox.setBounds(5, 65, 150, 40);
        raceJComboBox.setVisible(true);
        add(raceJComboBox);

        lvlPanel = new LvlPanel(this::updatePanels);
        lvlPanel.setBounds(0, 110, 855, 45);
        lvlPanel.setBackground(Color.magenta);
        lvlPanel.setVisible(true);
        add(lvlPanel);

        raceChoicesPanel = new RaceChoicesPanel(this::updatePanels);
        raceChoicesPanel.setBounds(0, 160, 855, 45);
        raceChoicesPanel.setBackground(Color.orange);
        raceChoicesPanel.setVisible(true);
        add(raceChoicesPanel);

        talentsPanel = new TalentsPanel(this::updatePanels);
        talentsPanel.setBounds(0, 210, 855, 45);
        talentsPanel.setBackground(Color.cyan);
        talentsPanel.setVisible(true);
        add(talentsPanel);

        rpgClassPanel = new RPGClassPanel(this::updatePanels);
        rpgClassPanel.setBounds(0, 260, 855, 45);
        rpgClassPanel.setBackground(Color.green);
        rpgClassPanel.setVisible(true);
        add(rpgClassPanel);

        repaint();

        updatePanels();
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    private void updatePanels() {
        updateMainFrame();
        updatePreviewPanel();
    }

    private void updateMainFrame() {
        raceChoicesPanel.InstantiateChoicesComboBoxes();
        lvlPanel.UpdateLvlPanel();
        talentsPanel.UpdateTalentsPanel();
    }

    private void updatePreviewPanel() {
        characterPreviewPanel.updateCharacter(character);
    }
}
