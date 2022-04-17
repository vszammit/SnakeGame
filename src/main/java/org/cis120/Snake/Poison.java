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

public class Poison extends Food {
    public static final String IMG_FILE = "files/snakePoison.png";

    public static final boolean GOOD = false;
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int MAXW = 600;
    private static final int MAXH = 600;

    private static final int INC = 5;
    private Snake snake;

    private static BufferedImage image;

    public Poison(Snake snake) {
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

    //randomly shrinks the snake's length by 1-5
    @Override
    public boolean growSnake() {
        return snake.shrink(((int) (Math.random() * 5) + 1));
    }

    @Override
    public void changeColor() {
        snake.setColor(Color.GRAY);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}