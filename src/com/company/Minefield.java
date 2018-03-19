package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Minefield {
    private ArrayList<ArrayList<FieldSpace>> field;
    private int numMines;
    private int height;
    private int width;

    Minefield(int number_mines, int new_height, int new_width) {
        numMines = number_mines;
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
    }

    public void GenerateField(int initX, int initY) {
        int mines = 0;
        Random rand = new Random();

        //Initial click is cleared
        field.get(initY).get(initX).setCleared(true);

        //until we've run out of mines
        while( mines < numMines) {
            //generate random space
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            //Check if space is not already a mine
            if(!field.get(y).get(x).isMine() && !field.get(y).get(x).isCleared()) {
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

    private int getNumFlags(int x, int y) {
        int totalFlags = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if( IsInBounds(x+i, y+j) && x+i != x && y+j != y && field.get(y+j).get(x+i).isFlag()) {
                    totalFlags++;
                }
            }
        }
        return totalFlags;
    }

    public boolean ClearSpace(int x, int y) {
        if(!IsInBounds(x, y)) {
            return true;
        }
        else if(field.get(y).get(x).isMine()) {
            return false;
        }
        else if(field.get(y).get(x).isCleared() && field.get(y).get(x).getWarnings() == getNumFlags(x, y)) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if( IsInBounds(x+i, y+j) && x+i != x && y+j != y && !field.get(y+j).get(x+i).isCleared()) {
                        ClearSpace(x+i, y+j);
                    }
                }
            }
        }
        else {
            Point currentPoint = new Point(x, y);
            Stack<Point> ToCheck = new Stack<>();
            ToCheck.push(currentPoint);
            while(!ToCheck.empty()) {
                currentPoint = ToCheck.pop();
                field.get(currentPoint.y).get(currentPoint.x).setCleared(true);

                if (field.get(currentPoint.y).get(currentPoint.x).getWarnings() <= 0) {

                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {

                            if (IsInBounds(currentPoint.x+i, currentPoint.y+j) && !field.get(currentPoint.y + j).get(currentPoint.x + i).isCleared()) {
                                Point addedPoint = new Point(currentPoint.x + i, currentPoint.y + j);
                                ToCheck.push(addedPoint);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void Reset() {
        for( int i = 0; i < width; i++ ) {
            for( int j = 0; j < height; j++ ) {
                field.get(j).get(i).setFlag(false);
                field.get(j).get(i).setMine(false);
                field.get(j).get(i).setCleared(false);
                field.get(j).get(i).setWarnings(0);
            }
        }
    }

    public void PrintField()
    {
        char flag = 'F';
        char mine = 'M';
        char cleared = 'C';

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                FieldSpace tempSpace = field.get(i).get(j);
                int warnings = tempSpace.getWarnings();
                String finalString = Integer.toString(warnings);
                if(tempSpace.isCleared()) {
                    finalString += cleared;
                }
                if(tempSpace.isFlag()) {
                    finalString += flag;
                }
                if(tempSpace.isMine()) {
                    finalString += mine;
                }
                System.out.print(finalString + "\t");
            }
            System.out.println("");
        }
    }

    public ArrayList<ArrayList<FieldSpace>> getField() {
        return field;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
