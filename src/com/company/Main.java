package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        int num_mines = 10;
        int fieldHeight = 20;
        int fieldWidth = 20;

        int fieldSpaceSize = 30;
        int gameAreaHeight = fieldHeight * fieldSpaceSize;
        int gameAreaWidth = fieldWidth * fieldSpaceSize;

        //TODO remove these magic numbers
        int windowHeight = gameAreaHeight-9;
        int buttonPanelWidth = 100;
        int windowWidth = gameAreaWidth + buttonPanelWidth-19;

        Dimension windowSize = new Dimension(windowWidth, windowHeight);

        JFrame frame = new JFrame("MineDusterPrimaryWindow");
        frame.setPreferredSize(windowSize);
        frame.setContentPane(new MineDusterPrimaryWindow(num_mines, fieldHeight, fieldWidth, windowSize).getPrimaryPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
