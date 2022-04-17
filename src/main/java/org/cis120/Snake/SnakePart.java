package org.cis120.Snake;

import java.awt.Color;
import java.awt.Graphics;

public class SnakePart extends GameObject {

    //A block of square that represents a part of the snake's body. Extends the Coordinate
    //class
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int MAX_WIDTH = 600;
    private static final int MAX_HEIGHT = 600;

    private Color color;

    public SnakePart(int x, int y, Color c) {
        super(x, y, WIDTH, HEIGHT, MAX_WIDTH, MAX_HEIGHT);
        color = c;

    }

    public void setColor(Color c) {
        color = c;
    }

    //returns true if the body is out of bounds
    public boolean hitWall() {
        if (this.getPx() < 0 || this.getPx() > getMaxX()
                || this.getPy() < 0 || this.getPy() > getMaxY()) {
            return true;
        } else {
            return false;
        }
    }

    //returns true if the current body intersects another coordinate object
    public boolean intersects(GameObject obj) {
        if (this.getPx() + this.getWidth() > obj.getPx()
                && this.getPy() + this.getHeight() > obj.getPy()
                && obj.getPx() + obj.getWidth() > this.getPx()
                && obj.getPy() + obj.getHeight() > this.getPy()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());

    }

}