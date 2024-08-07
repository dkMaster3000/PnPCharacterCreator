package org.example.mainframe;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.export.ExportPanel;
import org.example.loaders.RPGClassLoader;
import org.example.loaders.RaceLoader;
import org.example.loaders.SpellslotsLoader;
import org.example.loaders.TalentLoader;
import org.example.mainframe.lvl.LvlPanel;
import org.example.mainframe.race.RaceChoicesPanel;
import org.example.mainframe.race.RacePanel;
import org.example.mainframe.rpgclass.RPGClassPanel;
import org.example.mainframe.rpgclass.RPGClassSkillChoicesPanel;
import org.example.mainframe.talent.TalentsPanel;
import org.example.models.*;
import org.example.models.Character;
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
    List<UpdatableByMainFrame> updatablesByMainFrame = new ArrayList<>();

    public static Character character = new Character();
    public static List<Race> races = new ArrayList<>();
    public static TalentMatrix talentMatrix = null;
    public static List<RPGClass> rpgClasses = null;
    public static SpellslotsMatrix spellslotsMatrix = null;

    //STEP: 1
    public MainFrame() {
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
        spellslotsMatrix = SpellslotsLoader.getSpellslotsMatrix();

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
        updatablesByMainFrame.add(lvlPanel);

        panelContainer.addSectionSeparator();

        raceChoicesPanel = new RaceChoicesPanel(this::updatePanels);
        raceChoicesPanel.setVisible(true);
        panelContainer.add(raceChoicesPanel);
        updatablesByMainFrame.add(raceChoicesPanel);

        panelContainer.addSectionSeparator();

        talentsPanel = new TalentsPanel(this::updatePanels);
        talentsPanel.setVisible(true);
        panelContainer.add(talentsPanel);
        updatablesByMainFrame.add(talentsPanel);

        panelContainer.addSectionSeparator();

        rpgClassPanel = new RPGClassPanel(this::updatePanels);
        rpgClassPanel.setVisible(true);
        panelContainer.add(rpgClassPanel);

        panelContainer.addSectionSeparator();

        rpgClassSkillChoicesPanel = new RPGClassSkillChoicesPanel(this::updatePanels);
        rpgClassSkillChoicesPanel.setVisible(true);
        panelContainer.add(rpgClassSkillChoicesPanel);
        updatablesByMainFrame.add(rpgClassSkillChoicesPanel);

        panelContainer.add(Box.createVerticalBox());

        panelContainer.addSectionSeparator();

        exportPanel = new ExportPanel();
        panelContainer.add(exportPanel);
        updatablesByMainFrame.add(exportPanel);

        // ----------------------------------------------------------- RIGHT HALF -----------------------------------------------------------

        characterPreviewContainer = new JPanel();
        characterPreviewContainer.setLayout(new BorderLayout());
        characterPreviewContainer.setBounds(860, 5, 550, 750);
        add(characterPreviewContainer);

        characterPreviewPanel = new CharacterPreviewPanel();
        characterPreviewPanel.setBounds(5, 20, 500, 700);
        characterPreviewPanel.setPreferredSize(new Dimension(500, 1000));

        JScrollPane scrollPane = new JScrollPane(characterPreviewPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        characterPreviewContainer.add(scrollPane, BorderLayout.CENTER);

        repaint();

        updatePanels();
    }

    private void updatePanels() {
        updateMainFrame();
        updatePreviewPanel();
    }

    private void updateMainFrame() {
        for (UpdatableByMainFrame updatable : updatablesByMainFrame) {
            updatable.UpdateByMainFrame();
        }
    }

    private void updatePreviewPanel() {
        characterPreviewPanel.UpdateCharacterPreviewPanel();
    }
}
