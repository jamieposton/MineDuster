package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MineDusterPrimaryWindow {

    private JPanel PrimaryPanel;
    private JPanel GamePanel;
    private JPanel ButtonPanel;
    private JLabel TimerLabel;
    private JLabel MineCountLabel;
    private JButton ResetButton;
    private JButton PauseButton;
    private Minefield minefield;
    private Properties properties;
    private Dimension fieldSize;

    private boolean gameStarted;
    private boolean gamePaused;

    public MineDusterPrimaryWindow(int num_mines, int field_height, int field_width) {
        minefield = new Minefield(num_mines, field_height, field_width);
        gamePaused = true;
        gameStarted = false;


        properties = new Properties();
        try {
            properties.load(new FileInputStream("res/strings.properties"));
        } catch (IOException e) {
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
                System.out.println(e.paramString());
            }
        });
    }

    public JPanel getPrimaryPanel() {
        return PrimaryPanel;
    }

}
