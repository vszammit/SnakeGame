package org.cis120.Snake;
import org.cis120.Game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Snake snake;
    private Apple apple;
    private Poison poison;
    private Candy candy;
    int tick;//used to track number of ticks to calculate time
    int currentScore;

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel scores;

    // Game constants
    public static final int COURT_WIDTH = 600;
    public static final int COURT_HEIGHT = 600;
    //public static final int SQUARE_VELOCITY = 4;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 60;

    public GameCourt(JLabel status, JLabel records) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the snake to change directions when an arrowkey
        //is pressed. (The tick method below actually moves the snake.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    snake.changeDirection(Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snake.changeDirection(Direction.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    snake.changeDirection(Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    snake.changeDirection(Direction.UP);
                }
            }
        });

        this.status = status;
        this.scores = records;

        RunSnake.clearScoreHistory();
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snake = new Snake(0, 0, Color.GREEN);
        candy = new Candy(snake);
        apple = new Apple(snake);
        poison = new Poison(snake);

        tick = 0;
        playing = true;
        status.setText("Game in Progress");

        //update records
        RunSnake.writeRecords();
        scores.setText(RunSnake.getScores());

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * Finds out whether or not the user (or another user) wants to play
     *
     * @return 0 if the user selects no and 1 if the user selects yes
     */
    private int checkPlayAgain() {
        int check;
        check = JOptionPane.showConfirmDialog(null,
                "Sorry, You Lose! \n"
                        +"Your Score: " + currentScore +"\n"
                + "Would you like to play again? ",
                "Play Again?", JOptionPane.YES_NO_OPTION);

        return check;
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            tick++;
            snake.move();

            //check for intersection with foods
            if (snake.intersect(apple)) {
                apple.growSnake();
                apple.respawn();
                apple.updateScore();
                apple.changeColor();
                status.setText("Yummy!");
            } else if (snake.intersect(candy)) {
                candy.growSnake();
                candy.respawn();
                candy.updateScore();
                candy.changeColor();
                status.setText("Yummy!");
            } else if (snake.intersect(poison)) {
                poison.updateScore();
                poison.respawn();
                if (poison.growSnake()) {
                    poison.changeColor();
                    status.setText("Yuck!");
                } else {
                    playing = false;
                    snake.setColor(Color.BLACK);
                    status.setText("You lose!");
                }
            }

            //updates current score
            currentScore = apple.getScore() + candy.getScore() + poison.getScore();
            RunSnake.writeCurrentScore(currentScore, ((int)(tick * 60 / 1000.0)));
            scores.setText(RunSnake.getScores());

            // check for the game end conditions
            if (snake.hasDirection()) {
                if (snake.outOfBounds()) {
                    playing = false;
                    snake.setColor(Color.BLACK);
                    status.setText("You lose!");
                    checkPlayAgain();
                } else if (snake.bodyCollision()) {
                    playing = false;
                    snake.setColor(Color.BLACK);
                    status.setText("You lose!");
                    checkPlayAgain();
                } else if (currentScore < 0) {
                    playing = false;
                    snake.setColor(Color.BLACK);
                    status.setText("You lose!");
                    checkPlayAgain();
                }
            }


            // update the display
            repaint();
        }
    }


    //draw background grid
    public void drawGrid(Graphics g) {
        Color gridColor = new Color (24, 140, 190);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        g.setColor(gridColor);
        for (int col = 15; col < (COURT_WIDTH * 15); col+=15) {
            g.drawRect(col, 0, col,COURT_WIDTH * 15);
        }
        for (int row = 15; row < (COURT_HEIGHT * 15); row+=15) {
            g.drawRect(0, row, COURT_HEIGHT * 15, row);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        snake.draw(g);
        apple.draw(g);
        candy.draw(g);
        poison.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}