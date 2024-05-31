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

public class UploadPanel extends JPanel implements ActionListener {

    public interface UploadPanelFunc {
        void onUpload(Workbook workbook);
    }

    JLabel uploadedLabel;
    Workbook workbook;
    UploadPanelFunc onUpload;

    UploadPanel(UploadPanelFunc onUpload) {

        this.onUpload = onUpload;

        setLayout(null);

        JButton loadButton = new JButton("Excel Datei Upload");
        loadButton.setBounds(10, 10, 150, 40);
        loadButton.addActionListener(this);
        add(loadButton);

        uploadedLabel = new JLabel("Excel Datei noch nicht hochgeladen");
        uploadedLabel.setBounds(170, 10, 210, 40);
        uploadedLabel.setForeground(Color.RED);
        add(uploadedLabel);
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
                FileInputStream file = new FileInputStream(fileChooser.getSelectedFile());
                workbook = new XSSFWorkbook(file);

                uploadedLabel.setText("Excel Datei hochgeladen");
                uploadedLabel.setForeground(Color.GREEN);

                onUpload.onUpload(workbook);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
