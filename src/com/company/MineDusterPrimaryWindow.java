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


    MineDusterPrimaryWindow(int num_mines, int field_height, int field_width, Dimension windowSize) {
        this.windowSize = windowSize;

        //TODO: Wait to generate minefield until the first button is clicked.
        minefield = new Minefield(num_mines, field_height, field_width);
        gameCanvas.setParams(minefield);
        gameCanvas.setSize(this.windowSize);

        properties = new Properties();
        try {
            properties.load(new FileInputStream("res/strings.properties"));
        } catch (IOException e) {
            //Properties failed to load.
        }

        setActionListeners();

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabels();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void setActionListeners(){


        PauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newText;
                if(minefield.gamePaused){
                    newText = properties.getProperty("pauseButtonText");
                }else{
                    newText = properties.getProperty("pauseButtonTextResume");
                }
                PauseButton.setText(newText);
                minefield.pause();
                gameCanvas.repaint();

            }
        });


        ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add confirmation
                minefield.reset();
                gameCanvas.repaint();
            }
        });
    }

    private void updateLabels(){
        updateMineCountLabel();
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        String base = properties.getProperty("timerBaseLabel");
        String text = "";
        if(minefield.gamePaused && minefield.gameState == Minefield.GAME_STATE.GAME_ONGOING){
            text = "PAUSED";
        }else if(!minefield.gameStarted){
            text = base + "--:--";
        }else{
            long time = minefield.getTime();
            int seconds = (int) (time / 1000) % 60;
            int minutes = (int) (time / (60*1000));

            String timeString = String.format("%02d:%02d",minutes, seconds);
            text = base + timeString;
        }
        TimerLabel.setText(text);
    }

    private void updateMineCountLabel(){
        String base = properties.getProperty("minesBaseLabel");
        int numFlagged = minefield.getTotalFlags();
        int numMines = minefield.getNumMines();
        MineCountLabel.setText(base + numFlagged + "/" + numMines);
    }

    public JPanel getPrimaryPanel() {
        return PrimaryPanel;
    }

    private void createUIComponents() {
        gameCanvas = new MineFieldCanvas();
    }
}
