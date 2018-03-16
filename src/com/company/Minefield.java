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

        for(int i = 0; i < height; i++) {
            ArrayList<FieldSpace> temp = new ArrayList<>();
            for(int j = 0; j < width; j++) {
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
        while( mines < num_mines ) {
            //generate random space
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            //Check if space is not already a mine
            if(!field.get(y).get(x).isMine()) {
                field.get(y).get(x).setMine(true);
                AddWarnings(x,y);
                mines++;
            }
        }
    }

    private void AddWarnings(int x, int y) {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(isInBounds(x + i, y + j)) {
                    field.get(y+j).get(x+i).incrementWarnings();
                }
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean AddFlag(int x, int y) {
        if( !isInBounds(x, y) ) {
            return false;
        }
        else {
            boolean currentFlag = field.get(y).get(x).isFlag();
            field.get(x).get(y).setFlag(!currentFlag);
            return true;
        }
    }

    public boolean ClearSpace(int x, int y) {
        //check if space is already clear
            //if so, return
        return true;
    }


}
