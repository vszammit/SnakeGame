package org.cis120.Snake;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A game object displayed using an image.
 *
 * Note that the image is read from the file when the object is constructed, and that all objects
 * created by this constructor share the same image data (i.e. img is static). This is important for
 * efficiency: your program will go very slowly if you try to create a new BufferedImage every time
 * the draw method is invoked.
 */

public class Candy extends Food {
    public static final String IMG_FILE = "files/candy.png";

    public static final boolean GOOD = true;
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int MAXW = 600;
    private static final int MAXH = 600;
    private static final int INC = 10;

    private Snake snake;

    private static BufferedImage image;

    public Candy(Snake snake) {
        super(0, 0, WIDTH, HEIGHT, MAXW, MAXH, GOOD, INC, snake);

        try {
            if (image == null) {
                image = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        this.snake = snake;
        this.respawn();
    }

    //increases snake's length by 5
    @Override
    public boolean growSnake() {
        return snake.grow(5);
    }

    //randomly changes snake's color to anything
    @Override
    public void changeColor() {
        snake.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
                (int) (Math.random() * 256)));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
