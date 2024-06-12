package org.example.mainframe;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    public static XSSFWorkbook workbook = null;
    public static File file = null;

    CharacterPreviewPanel characterPreviewPanel;
    JPanel characterPreviewContainer;
    LvlPanel lvlPanel;
    RaceChoicesPanel raceChoicesPanel;
    TalentsPanel talentsPanel;
    RPGClassPanel rpgClassPanel;
    RPGClassSkillChoicesPanel rpgClassSkillChoicesPanel;
    PanelContainer panelContainer;
    ExportPanel exportPanel;

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

        panelContainer = new PanelContainer();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        panelContainer.setBounds(5, 5, 850, 750);
        add(panelContainer);

        UploadPanel uploadPanel = new UploadPanel(this::onUpload);
        panelContainer.add(uploadPanel);

        panelContainer.addSectionSeparator();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                System.exit(0);
            }
        });

        revalidate();
        repaint();
    }

    //STEP: 2
    //invoked by upload button
    private void onUpload(XSSFWorkbook workbook) {
        MainFrame.workbook = workbook;

        races = RaceLoader.getRaces();
        talentMatrix = TalentLoader.getTalentMatrix();
        rpgClasses = RPGClassLoader.getRPGClass();

        createPanelsAfterUpload();
    }

    //STEP: 3
    private void createPanelsAfterUpload() {
        RacePanel racePanel = new RacePanel(this::updatePanels);
        panelContainer.add(racePanel);

        panelContainer.addSectionSeparator();

        lvlPanel = new LvlPanel(this::updatePanels);
        lvlPanel.setVisible(true);
        panelContainer.add(lvlPanel);

        panelContainer.addSectionSeparator();

        raceChoicesPanel = new RaceChoicesPanel(this::updatePanels);
        raceChoicesPanel.setVisible(true);
        panelContainer.add(raceChoicesPanel);

        panelContainer.addSectionSeparator();

        talentsPanel = new TalentsPanel(this::updatePanels);
        talentsPanel.setVisible(true);
        panelContainer.add(talentsPanel);

        panelContainer.addSectionSeparator();

        rpgClassPanel = new RPGClassPanel(this::updatePanels);
        rpgClassPanel.setVisible(true);
        panelContainer.add(rpgClassPanel);

        panelContainer.addSectionSeparator();

        rpgClassSkillChoicesPanel = new RPGClassSkillChoicesPanel(this::updatePanels);
        rpgClassSkillChoicesPanel.setVisible(true);
        panelContainer.add(rpgClassSkillChoicesPanel);

        panelContainer.add(Box.createVerticalBox());

        panelContainer.addSectionSeparator();

        exportPanel = new ExportPanel();
        panelContainer.add(exportPanel);

        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------

        characterPreviewContainer = new JPanel();
        characterPreviewContainer.setBounds(860, 5, 550, 750);
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
        exportPanel.OnUpdate();
    }

    private void updatePreviewPanel() {
        characterPreviewPanel.UpdateCharacterPreviewPanel();
    }
}
