package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MineFieldCanvas extends JPanel implements MouseListener{
    private int numColumns, numRows;
    private int cellSizeX, cellSizeY;
    MineFieldCanvas(){
        super(true);
        this.addMouseListener(this);
    }

    public void setParams(int numColumns, int numRows){
        this.numColumns = numColumns;
        this.numRows = numRows;
        cellSizeY = getHeight()/numRows;
        cellSizeX = getHeight()/numColumns;
    }


    public void paint(Graphics g){
        g.clearRect(0,0, getHeight(), getWidth());
        cellSizeY = getHeight()/numRows;
        cellSizeX = getHeight()/numColumns;
        drawGrid(g);
        for(int row = 0; row < numRows; row++){
            for(int col=0; col < numColumns; col++){
                drawCell(g, row, col);
            }
        }
    }

    private void drawGrid(Graphics g){
        g.setColor(Color.DARK_GRAY);

        //Draw Horizontal
        for(int y = 0; y<=getHeight(); y+=cellSizeY) {
            g.drawLine(0, y, getWidth(), y);
        }

        //Draw Vertical
        for(int x = 0; x<=getHeight(); x+=cellSizeX) {
            g.drawLine(x, 0, x, getHeight());
        }
    }

    private void drawCell(Graphics g,int row, int col){
        g.setFont(g.getFont().deriveFont(0.8f*cellSizeY));
        g.setColor(Color.LIGHT_GRAY);
        Rectangle rect = new Rectangle(col*cellSizeX + 1, row*cellSizeY + 1, cellSizeX - 1, cellSizeY - 1);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.RED);
        drawCenteredString(g, "8", rect, g.getFont());
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
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
        int row = e.getY()/cellSizeY;
        int col = e.getX()/cellSizeX;
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
