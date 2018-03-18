package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MineDusterPrimaryWindow {

    private Dimension windowSize;
    private JPanel PrimaryPanel;
    private JPanel ButtonPanel;
    private JLabel TimerLabel;
    private JLabel MineCountLabel;
    private JButton ResetButton;
    private JButton PauseButton;
    private MineFieldCanvas gameCanvas;
    private Minefield minefield;
    private Properties properties;

    private boolean gameStarted;
    private boolean gamePaused;

    MineDusterPrimaryWindow(int num_mines, int field_height, int field_width, Dimension windowSize) {
        this.windowSize = windowSize;

        //TODO: Wait to generate minefield until the first button is clicked.
        minefield = new Minefield(num_mines, field_height, field_width, 0, 0);
        gameCanvas.setParams(minefield);
        gameCanvas.setSize(this.windowSize);

        gamePaused = true;
        gameStarted = false;

        properties = new Properties();
        try {
            properties.load(new FileInputStream("res/strings.properties"));
        } catch (IOException e) {
            //Properties failed to load.
        }

        setActionListeners();

    }

    private void setActionListeners(){


        PauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newText;
                if(gamePaused){
                    newText = properties.getProperty("pauseButtonText");
                }else{
                    newText = properties.getProperty("pauseButtonTextResume");
                }
                PauseButton.setText(newText);
                gamePaused = !gamePaused;

            }
        });


        ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Wait to generate minefield until the first button is clicked.

                // TODO: add confirmation
                minefield.Reset(0, 0);
            }
        });
    }

    public JPanel getPrimaryPanel() {
        return PrimaryPanel;
    }

    private void createUIComponents() {
        gameCanvas = new MineFieldCanvas();
    }
}
