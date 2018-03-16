package com.company;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Minefield {
    private ArrayList<ArrayList<FieldSpace>> field;
    private int num_mines;
    private int height;
    private int width;

    public Minefield(int number_mines, int new_height, int new_width) {
        num_mines = number_mines;
        height = new_height;
        width = new_width;

        field = new ArrayList<>();

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
                if(IsInBounds(x + i, y + j)) {
                    field.get(y+j).get(x+i).incrementWarnings();
                }
            }
        }
    }

    private boolean IsInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean AddFlag(int x, int y) {
        if( !IsInBounds(x, y) ) {
            return false;
        }
        else {
            boolean currentFlag = field.get(y).get(x).isFlag();
            field.get(y).get(x).setFlag(!currentFlag);
            return true;
        }
    }

    public boolean ClearSpace(int x, int y) {
        if(field.get(y).get(x).isMine()) {
            return false;
        }
        else {
            Point currentPoint = new Point(x, y);
            Stack<Point> ToCheck = new Stack<>();
            ToCheck.push(currentPoint);
            while(!ToCheck.empty()) {
                currentPoint = ToCheck.pop();
                field.get(currentPoint.y).get(currentPoint.x).setCleared(true);
                if(field.get(currentPoint.y).get(currentPoint.x).getWarnings() <= 0) {
                    for(int i = -1; i < 2; i++) {
                        for(int j = -1; j < 2; j++) {
                            if(!field.get(currentPoint.y+j).get(currentPoint.x+i).isCleared()) {
                                Point addedPoint = new Point(currentPoint.x+1, currentPoint.y+j);
                                ToCheck.push(addedPoint);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }


}
