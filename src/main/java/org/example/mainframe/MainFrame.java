package org.example.mainframe;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.loaders.RPGClassLoader;
import org.example.loaders.RaceLoader;
import org.example.loaders.TalentLoader;
import org.example.mainframe.lvl.LvlPanel;
import org.example.mainframe.race.RaceChoicesPanel;
import org.example.mainframe.race.RacePanel;
import org.example.mainframe.rpgclass.RPGClassPanel;
import org.example.mainframe.rpgclass.RPGClassSkillChoicesPanel;
import org.example.mainframe.talent.TalentsPanel;
import org.example.models.Character;
import org.example.models.RPGClass;
import org.example.models.Race;
import org.example.models.TalentMatrix;
import org.example.preview.CharacterPreviewPanel;

import javax.swing.*;

import java.awt.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    public static Workbook workbook = null;
    public static File file = null;

    CharacterPreviewPanel characterPreviewPanel;
    JPanel characterPreviewContainer;
    LvlPanel lvlPanel;
    RaceChoicesPanel raceChoicesPanel;
    TalentsPanel talentsPanel;
    RPGClassPanel rpgClassPanel;
    RPGClassSkillChoicesPanel rpgClassSkillChoicesPanel;
    JPanel editorContainer;

    public static Character character = new Character();
    public static List<Race> races = new ArrayList<>();
    public static TalentMatrix talentMatrix = null;
    public static List<RPGClass> rpgClasses = null;

    //STEP: 1
    MainFrame() {
        setTitle("PnP Character Creator");
        setSize(1430, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(null);

        editorContainer = new JPanel();
        editorContainer.setLayout(new BoxLayout(editorContainer, BoxLayout.Y_AXIS));
        editorContainer.setBounds(5, 5, 850, 750);
        add(editorContainer);

        UploadPanel uploadPanel = new UploadPanel(this::onUpload);
        uploadPanel.setBackground(Color.lightGray);
        editorContainer.add(uploadPanel);

        revalidate();
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
        RacePanel racePanel = new RacePanel(this::updatePanels);
        editorContainer.add(racePanel);

        lvlPanel = new LvlPanel(this::updatePanels);
        lvlPanel.setBackground(Color.magenta);
        lvlPanel.setVisible(true);
        editorContainer.add(lvlPanel);

        raceChoicesPanel = new RaceChoicesPanel(this::updatePanels);
        raceChoicesPanel.setBackground(Color.orange);
        raceChoicesPanel.setVisible(true);
        editorContainer.add(raceChoicesPanel);

        talentsPanel = new TalentsPanel(this::updatePanels);
        talentsPanel.setBackground(Color.cyan);
        talentsPanel.setVisible(true);
        editorContainer.add(talentsPanel);

        rpgClassPanel = new RPGClassPanel(this::updatePanels);
        rpgClassPanel.setBackground(Color.green);
        rpgClassPanel.setVisible(true);
        editorContainer.add(rpgClassPanel);

        rpgClassSkillChoicesPanel = new RPGClassSkillChoicesPanel(this::updatePanels);
        rpgClassSkillChoicesPanel.setBackground(Color.pink);
        rpgClassSkillChoicesPanel.setVisible(true);
        editorContainer.add(rpgClassSkillChoicesPanel);

        editorContainer.add(Box.createVerticalBox());

        ExportPanel exportPanel = new ExportPanel();
        editorContainer.add(exportPanel);

        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------

        characterPreviewContainer = new JPanel();
        characterPreviewContainer.setBounds(860, 0, 550, 750);
        characterPreviewContainer.setBackground(Color.lightGray);
        characterPreviewContainer.setLayout(new BoxLayout(characterPreviewContainer, BoxLayout.Y_AXIS));
        add(characterPreviewContainer);

        JLabel characterPreviewLabel = new JLabel("Charakter Vorschau");
        characterPreviewLabel.setBounds(5, 5, 400, 10);
        characterPreviewContainer.add(characterPreviewLabel);

        characterPreviewPanel = new CharacterPreviewPanel();
        characterPreviewPanel.setBounds(5, 20, 390, 700);

        JScrollPane scrollPane = new JScrollPane(characterPreviewPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        characterPreviewContainer.add(scrollPane);

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
        rpgClassSkillChoicesPanel.UpdateRPGClassSkillChoicesPanel();
    }

    private void updatePreviewPanel() {
        characterPreviewPanel.UpdateCharacterPreviewPanel();
    }
}
