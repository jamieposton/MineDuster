package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Minefield {
    private ArrayList<ArrayList<FieldSpace>> field;
    private int num_mines;
    private int height;
    private int width;

    public Minefield(int number_mines, int new_height, int new_width) {
        this.num_mines = number_mines;
        this.height = new_height;
        this.width = new_width;

        for(int i = 0; i < this.height; i++) {
            ArrayList<FieldSpace> temp = new ArrayList<>();
            for(int j = 0; j < this.width; j++) {
                FieldSpace tempSpace = new FieldSpace(false, false, false, 0);
                temp.add(tempSpace);
            }
            this.field.add(temp);
        }

        GenerateMines();
    }

    private void GenerateMines() {
        int mines = 0;
        Random rand = new Random();
        //until we've run out of mines
        while( mines < this.num_mines ) {
            //generate random space
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            //Check if space is not already a mine
            if(!this.field.get(x).get(y).isMine()) {
                this.field.get(x).get(y).setMine(true);
                AddWarnings(x,y);
                mines++;
            }
        }
    }

    private void AddWarnings(int x, int y) {
        //for every adjacent space
            //if it's not outside the bounds
                //increment the warnings
    }

    public boolean AddFlag(int x, int y) {
        //check if x and y are in the right bounds
            //if not return false
        //check if a flag is already there
            //if yes return false
        //place flag
        //return true
        return true;
    }

    public boolean ClearSpace(int x, int y) {
        //check if space is already clear
            //if so, return
        return true;
    }


}
