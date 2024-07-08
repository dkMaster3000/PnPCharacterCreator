package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.mainframe.MainFrame;

import javax.swing.*;

public class Main {

    private static boolean isDarkTheme = true;

    private static MainFrame mainFrame = null;

    public static void main(String[] args) {
        setTheme();
    }

    public static void switchTheme() {
        isDarkTheme = !isDarkTheme;
        setTheme();
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(isDarkTheme ? new FlatDarkLaf() : new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace(System.err);
        }

        //create once a mainFrame -> so it could be updated
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }

        // Update the look and feel of all existing components
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    public static boolean getIsDarkTheme() {
        return isDarkTheme;
    }
}
