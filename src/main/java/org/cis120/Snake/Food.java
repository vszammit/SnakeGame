package org.cis120.Snake;

public abstract class Food extends GameObject {

    private boolean isPositive; //if increases score or decreases score
    private int score;
    private int inc;
    private Snake snake;


    public Food(int px, int py, int width, int height, int courtWidth, int courtHeight,
                boolean b, int inc, Snake snake) {
        super(px, py, width, height, courtWidth, courtHeight);
        this.inc = inc;
        isPositive = b;
        this.snake = snake;
        respawn();
    }

    //updates score from eating the food
    public void updateScore() {
        if (isPositive) {
            score += inc;
        } else {
            score -= inc;
        }
    }

    //checks if the inputted position is already contained by the snake.
    //want to make sure you aren't adding a food to the body's position
    public boolean checkUnavailable(int x, int y) {
        for (SnakePart part : snake.getBody()) {
            if (x == part.getPx() && y == part.getPy()) {
                return true;
            }
        }
        return false;
    }

    //respawn food back on the court after it's eaten
    public void respawn() {
        int x = ((int) (Math.random() * 40)) * 15;
        int y = ((int) (Math.random() * 40)) * 15;

        while (checkUnavailable(x, y)) {
            x = ((int) (Math.random() * 40)) * 15;
            y = ((int) (Math.random() * 40)) * 15;
        }
        setPx(x);
        setPy(y);
    }


    public int getScore() {
        return score;
    }

    //abstract methods that subclasses will override based on their individual characteristics
    //public abstract void respawn();

    public abstract boolean growSnake();

    public abstract void changeColor();


}