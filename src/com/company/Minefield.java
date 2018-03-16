package com.company;

import java.util.ArrayList;

public class Minefield {
    private ArrayList<ArrayList<FieldSpace>> field;
    private int num_mines;

    public Minefield(int number_mines, int height, int width) {
        this.num_mines = number_mines;

        for(int i = 0; i < height; i++) {
            ArrayList<FieldSpace> temp = new ArrayList<>();
            for(int j = 0; j < width; j++) {
                FieldSpace tempSpace = new FieldSpace(false, false, false, 0);
                temp.add(tempSpace);
            }
            this.field.add(temp);
        }
    }
}
