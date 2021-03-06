package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MineFieldCanvas extends JPanel implements MouseListener{
    private Image flagImage;
    private Image mineImage;
    private Image explosionImage;
    private int numColumns, numRows;
    private int cellSizeX, cellSizeY;
    private Minefield minefield;
    MineFieldCanvas(){
        super(true);
        this.addMouseListener(this);
        try {
            flagImage = ImageIO.read(new FileInputStream("res/flag.png"));
            mineImage = ImageIO.read(new FileInputStream("res/mine.png"));
            explosionImage = ImageIO.read(new FileInputStream("res/explosion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParams(Minefield newMinefield){
        this.minefield = newMinefield;
        this.numColumns = minefield.getWidth();
        this.numRows = minefield.getHeight();
        cellSizeY = getHeight()/numRows;
        cellSizeX = getHeight()/numColumns;
    }


    public void paint(Graphics g){
        g.clearRect(0,0, getHeight(), getWidth());
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getHeight(), getWidth());
        cellSizeY = getHeight()/numRows;
        cellSizeX = getHeight()/numColumns;
        drawGrid(g);
        ArrayList<ArrayList<FieldSpace>> field = minefield.getField();
        ArrayList<FieldSpace> row;
        FieldSpace space;
        
        if(minefield != null) {
            for (int rowNum = 0; rowNum < numRows; rowNum++) {
                for (int colNum = 0; colNum < numColumns; colNum++) {
                    row = field.get(rowNum);
                    space = row.get(colNum);
                    drawCell(g, space, colNum, rowNum);
                }
            }
        }
    }

    private void drawGrid(Graphics g){
        g.setColor(Color.BLACK);

        //Draw Horizontal
        for(int y = 0; y<=getHeight(); y+=cellSizeY) {
            g.drawLine(0, y, getWidth(), y);
        }

        //Draw Vertical
        for(int x = 0; x<=getHeight(); x+=cellSizeX) {
            g.drawLine(x, 0, x, getHeight());
        }
    }

    private void drawCell(Graphics g, FieldSpace space, int colNum, int rowNum){
        g.setFont(g.getFont().deriveFont(0.8f*cellSizeY));

        // Rect that specifies the bounds on the cell.
        Rectangle rect = new Rectangle(colNum*cellSizeX + 1, rowNum*cellSizeY + 1, cellSizeX - 1, cellSizeY - 1);
        if(minefield.gameState == Minefield.GAME_STATE.GAME_ONGOING) {
            if (!minefield.gamePaused) {
                if (space.isCleared()) {
                    drawNumbers(g, space, rect);
                } else if (space.isFlag()) {
                    // Draw Flag
                    drawImageRect(g, flagImage, rect, Color.GRAY);
                }
            }
        }else{
            //TODO: draw explosion on mines that caused game to end.
            if(space.isMine()){
                drawImageRect(g, mineImage, rect, Color.BLACK);
            }else{
                drawNumbers(g, space, rect);
            }
        }
    }

    private void drawNumbers(Graphics g, FieldSpace space, Rectangle rect){
        int numWarnings = space.getWarnings();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        if (numWarnings != 0) {
            // Draw numbered cell
            g.setColor(getNumberColor(numWarnings));
            drawCenteredString(g, String.valueOf(numWarnings), rect, g.getFont());
        }
    }

    private void drawImageRect(Graphics g, Image img, Rectangle rect, Color bgColor){
        g.drawImage(img, rect.x, rect.y, rect.width, rect.height, bgColor, null);
    }

    private Color getNumberColor(int n){
        switch(n){
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.MAGENTA;
            case 5:
                return Color.PINK;
            case 6:
                return Color.yellow;
            case 7:
                return Color.cyan;
            case 8:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = e.getY() / cellSizeY;
        int col = e.getX() / cellSizeX;
        if(minefield.gameState == Minefield.GAME_STATE.GAME_ONGOING) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (!minefield.gameStarted) {
                    minefield.generateField(col, row);
                    minefield.gameStarted = true;
                }
                minefield.clearSpace(col, row);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                minefield.addFlag(col, row);
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
