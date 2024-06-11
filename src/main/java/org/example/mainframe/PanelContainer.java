package org.example.mainframe;

import javax.swing.*;
import java.awt.*;

public class PanelContainer extends JPanel {

    public void addSectionSeparator() {
        addSpace();
        addSeparator();
        addSpace();
    }

    public void addSeparator() {
        JSeparator newSeparator = new JSeparator();
        newSeparator.setOrientation(SwingConstants.HORIZONTAL);
        add(newSeparator);
    }

    public void addSpace() {
        add(Box.createRigidArea(new Dimension(5, 10)));
    }
}
