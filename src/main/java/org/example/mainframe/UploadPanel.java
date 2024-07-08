package org.example.mainframe;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

public class UploadPanel extends JPanel implements ActionListener {

    public interface UploadPanelFunc {
        void onUpload(XSSFWorkbook workbook);
    }

    JLabel uploadedLabel;
    XSSFWorkbook workbook;
    UploadPanelFunc onUpload;

    UploadPanel(UploadPanelFunc onUpload) {
        this.onUpload = onUpload;

        Dimension thisSize = new Dimension(1000, 60);
        setMinimumSize(thisSize);
        setMaximumSize(thisSize);
        setPreferredSize(thisSize);

        setLayout(null);

        JButton loadButton = new JButton("Excel Datei Upload");
        loadButton.setBounds(10, 10, 150, 40);
        loadButton.addActionListener(this);
        add(loadButton);

        uploadedLabel = new JLabel("Excel Datei noch nicht hochgeladen");
        uploadedLabel.setBounds(170, 10, 210, 40);
        uploadedLabel.setForeground(Color.RED);
        add(uploadedLabel);

        // Create the toggle button for theme switch
        JToggleButton themeToggleButton = new JToggleButton("Darkmode", Main.getIsDarkTheme());
        themeToggleButton.setBounds(700, 10, 150, 40);
        themeToggleButton.addActionListener(_ -> Main.switchTheme());
        add(themeToggleButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        JFileChooser fileChooser = new JFileChooser(fsv.getHomeDirectory());
        fileChooser.setPreferredSize(new Dimension(1000, 600));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel file", "xls", "xlsx");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                File file = fileChooser.getSelectedFile();
                MainFrame.file = file;

                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);

                uploadedLabel.setText("Excel Datei hochgeladen");
                uploadedLabel.setForeground(UsedValues.DARK_GREEN);

                onUpload.onUpload(workbook);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
