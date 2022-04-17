package org.cis120.Snake;

import java.awt.Graphics;

/**
 * A GameObject in the game.
 *
 * These objects exist in the game court and have positions.
 */
public abstract class GameObject {

    private int px;
    private int py;

    /* Size of object, in pixels. */
    private int width;
    private int height;

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
     * positions for the upper-left hand corner of the object.
     */
    private int maxX;
    private int maxY;

    /**
     * Constructor
     */
    public GameObject(int px, int py, int width, int height, int courtWidth,
                      int courtHeight) {
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;

        // take the width and height into account when setting the bounds for the upper left corner
        // of the object.
        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
    }

    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    /*** SETTERS **********************************************************************************/
    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }


    /**
     * Default draw method that provides how the object should be drawn in the GUI. This method does
     * not draw anything. Subclass should override this method based on how their object should
     * appear.
     *
     * @param g The <code>Graphics</code> context used for drawing the object. Remember graphics
     * contexts that we used in OCaml, it gives the context in which the object should be drawn (a
     * canvas, a frame, etc.)
     */
    public abstract void draw(Graphics g);
}