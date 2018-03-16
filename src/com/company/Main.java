package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        int num_mines = 10;
        int field_height = 20;
        int field_width = 20;

        JFrame frame = new JFrame("MineDusterPrimaryWindow");
        frame.setContentPane(new MineDusterPrimaryWindow(num_mines, field_height, field_width).getPrimaryPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
