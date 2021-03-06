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
    public boolean gameStarted;
    public boolean gamePaused;
    public GAME_STATE gameState;
    private long totalTime;
    private long startTime;
    private int totalFlags;

    public enum GAME_STATE{GAME_WON, GAME_LOST, GAME_ONGOING}

    //Constructor for the minefield.
    //DOES NOT generate all the mines and stuff
    //The function generate field needs to be called separately to make the mines and warnings.
    Minefield(int number_mines, int new_height, int new_width) {
        numMines = number_mines;
        height = new_height;
        width = new_width;

        gamePaused = false;
        gameStarted = false;
        gameState = GAME_STATE.GAME_ONGOING;

        totalTime = 0;
        totalFlags = 0;

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

    //Generates all the mines and warnings.
    //The initial point passed in will automatically be set to clear.
    public void generateField(int initX, int initY) {
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
                addWarnings(x,y);
                mines++;
            }
        }
        startTime = System.currentTimeMillis();
    }

    //Increments all warnings adjacent to the parameter point
    private void addWarnings(int x, int y) {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(isInBounds(x + i, y + j)) {
                    field.get(y+j).get(x+i).incrementWarnings();
                }
            }
        }
    }

    //Checks if the point is in the bounds of the field or not.
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    //Adds a flag to a point on the field
    //Increments/Decrements total flags
    public boolean addFlag(int x, int y) {
        if( !isInBounds(x, y) ) {
            return false;
        }
        else {
            boolean currentFlag = field.get(y).get(x).isFlag();
            if( currentFlag ) {
                totalFlags--;
            }
            else {
                totalFlags++;
            }
            field.get(y).get(x).setFlag(!currentFlag);
            return true;
        }
    }

    //Gets the amount of flags adjacent to a point
    private int getNumNeighborFlags(int x, int y) {
        int totalFlags = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if( isInBounds(x+i, y+j) && !(i == 0 && j == 0) && field.get(y+j).get(x+i).isFlag()) {
                    totalFlags++;
                }
            }
        }
        return totalFlags;
    }

    //Clears a space
    public void clearSpace(int x, int y) {
        //If the space was a mine, switch game state to lost
        if(field.get(y).get(x).isMine()) {
            pause();
            gameState = GAME_STATE.GAME_LOST;
            return;
        }

        //If the space was already cleared and has the correct flags, clear all uncleared neighbors
        if(field.get(y).get(x).isCleared() && field.get(y).get(x).getWarnings() == getNumNeighborFlags(x, y)) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if( isInBounds(x+i, y+j) && !(i == 0 && j == 0) && !field.get(y+j).get(x+i).isCleared()) {
                        clearSpace(x+i, y+j);
                    }
                }
            }
        }

        else {
            //Clear the space
            Point currentPoint = new Point(x, y);
            Stack<Point> ToCheck = new Stack<>();
            ToCheck.push(currentPoint);
            while(!ToCheck.empty()) {
                currentPoint = ToCheck.pop();
                field.get(currentPoint.y).get(currentPoint.x).setCleared(true);

                //If the space was a 0, flood fill everything touching the 0
                if (field.get(currentPoint.y).get(currentPoint.x).getWarnings() <= 0) {

                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {

                            if (isInBounds(currentPoint.x+i, currentPoint.y+j) && !field.get(currentPoint.y + j).get(currentPoint.x + i).isCleared()) {
                                Point addedPoint = new Point(currentPoint.x + i, currentPoint.y + j);
                                ToCheck.push(addedPoint);
                            }
                        }
                    }
                }
            }
        }
        //And after all that, check to see if we've won or not
        gameState = isWon();
    }

    //Checks to see if all the non mines are cleared or not
    //TODO CLEAN-UP: This function is calling pause, might cause issues later.
    private GAME_STATE isWon() {
        for( int i = 0; i < width; i++ ) {
            for( int j = 0; j < height; j++) {
                if( !field.get(j).get(i).isCleared() && !field.get(j).get(i).isMine() ) {
                    return GAME_STATE.GAME_ONGOING;
                }
            }
        }
        //If everything possible is cleared, we've won
        pause();
        return GAME_STATE.GAME_WON;
    }

    //Resets the field to get ready to generate a new one.
    public void reset() {
        for( int i = 0; i < width; i++ ) {
            for( int j = 0; j < height; j++ ) {
                field.get(j).get(i).setFlag(false);
                field.get(j).get(i).setMine(false);
                field.get(j).get(i).setCleared(false);
                field.get(j).get(i).setWarnings(0);
            }
        }
        totalTime = 0;
        totalFlags = 0;

        gameState = GAME_STATE.GAME_ONGOING;
        gamePaused = false;
        gameStarted = false;
    }

    //Pauses the time counter and saves the total time.
    public void pause() {
        if( gameState == GAME_STATE.GAME_ONGOING )
        {
            if(gamePaused) {
                gamePaused = false;
                startTime = System.currentTimeMillis();
            }
            else {
                gamePaused = true;
                long currentTime = System.currentTimeMillis();
                totalTime += currentTime - startTime;
            }
        }
    }

    //Prints out the whole field to the terminal for debugging.
    public void printField()
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

    //Getter function for the field itself.
    //Generally used for painting the canvas.
    public ArrayList<ArrayList<FieldSpace>> getField() {
        return field;
    }

    //Getter function for the number of mines in the field.
    public int getNumMines() {
        return numMines;
    }

    //Getter function for the height of the field.
    public int getHeight() {
        return height;
    }

    //Getter function for the width of the field.
    public int getWidth() {
        return width;
    }

    //Getter function for the current total of flags of the field.
    public int getTotalFlags() {
        return totalFlags;
    }

    //Getter function for the current time.
    //Has different functionality for if the game is paused or not
    public long getTime() {
        if( gamePaused || gameState != GAME_STATE.GAME_ONGOING) {
            return totalTime;
        }
        else {
            return totalTime + ( System.currentTimeMillis() - startTime );
        }
    }
}
