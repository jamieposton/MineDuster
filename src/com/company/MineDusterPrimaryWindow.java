package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineDusterPrimaryWindow {
    public MineDusterPrimaryWindow() {
        PauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.paramString());
            }
        });
    }

    public JPanel getPrimaryPanel() {
        return PrimaryPanel;
    }

    private JPanel PrimaryPanel;
    private JPanel GamePanel;
    private JPanel ButtonPanel;
    private JLabel TimerLabel;
    private JLabel MineCountLabel;
    private JButton ResetButton;
    private JButton PauseButton;
}
