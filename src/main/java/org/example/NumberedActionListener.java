package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberedActionListener implements ActionListener {

    public int number;

    public NumberedActionListener(int number) {
        this.number = number;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
