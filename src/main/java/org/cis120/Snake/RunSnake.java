package org.cis120.Snake;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    //filepath. The file consists of 4 lines keeps track of
    //top 3 high scores, and the last line represents the current score and time.
    //rankings of high score is evaluated by score * 0.6 + time * 0.4
    private static final String FILEPATH = "files/scoreHistory.txt";

    //clears scoreHistory file
    public static void clearScoreHistory() {
        try {
            FileWriter fw = new FileWriter(FILEPATH, false);
            fw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e1) {
            System.out.println("Error");
        }
    }

    public static void writeCurrentScore(int score, int time) {
        int[][] arr = readRecordAndCurrent();
        FileWriter fw;
        try {
            fw = new FileWriter(FILEPATH, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 3; i++) {
                bw.write(arr[i][0] + " " + arr[i][1] + "\n");
            }
            bw.write(score + " " + time);
            bw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e1) {
            System.out.println("Error");
        }


    }

    //Used when a game ends. Update the top 3 high scores with the current score and time, if
    //necessary. Change current score and time to 0
    public static void writeRecords() {
        int[][] arr = readRecordAndCurrent();
        int rank = 0;
        double well = arr[3][0] * 0.6 + arr[3][1] * 0.4;


        if (well >= arr[0][0] * 0.6 + arr[0][1] * 0.4) {
            rank = 1;
        } else if (well >= arr[1][0] * 0.6 + arr[1][1] * 0.4) {
            rank = 2;
        } else if (well >= arr[2][0] * 0.6 + arr[2][1] * 0.4) {
            rank = 3;
        }

        if (rank != 0) {
            FileWriter fw;
            try {
                fw = new FileWriter(FILEPATH, false);
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < rank - 1; i++) {
                    bw.write(arr[i][0] + " " + arr[i][1] + "\n");
                }
                bw.write(arr[3][0] + " " + arr[3][1] + "\n");
                for (int j = 0; j < 3 - rank; j++) {
                    bw.write(arr[rank - 1 + j][0] + " " + arr[rank - 1 + j][1] + "\n");
                }
                bw.write(0 + " " + 0);
                bw.close();

            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException();
            } catch (IOException e1) {
                System.out.println("Error in writing");
            }
        }

    }

    public static int[][] readRecordAndCurrent() {
        int[][] arr = new int[4][2];
        int count = 0;
        try {
            FileReader fr = new FileReader(FILEPATH);
            BufferedReader br = new BufferedReader(fr);
            try {
                String line = br.readLine();
                while (line != null && count < 4) {
                    String[] record = line.split(" ");
                    arr[count][0] = Integer.parseInt(record[0]);
                    arr[count][1] = Integer.parseInt(record[1]);
                    line = br.readLine();
                    count++;
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error in reading");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading");
        }
        return arr;
    }


    //Return a formatted string that states the top 3 records as well as current score and time.
    //To be displayed in game. used html because regular did not successfully skip line.
    public static String getScores() {
        int[][] arr = readRecordAndCurrent();
        String result = "<html>High Scores: <br/>----------------------------------------<br/>";
        for (int i = 1; i < 4; i++) {
            result += i + ". Score: " + arr[i - 1][0] + "\tTime: " + arr[i - 1][1] + "<br/>";
        }
        result += "<br/>Current Game:<br/>----------------------------------------<br/>"
                + "   Score: " + arr[3][0] + "\tTime: " + arr[3][1] + "<html>";
        return result;
    }


    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.


        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("THE NEW SNAKE GAME");
        frame.setLocation(300, 300);


        String description = ""
                + "Play this new version of Snake, a game where a snake navigates around a court" +
                "to eat apples and candy. \n You must beware of the poisonous potions! \n\n"
                + "Instructions: \n "
                + "Use the arrows on your keyboard to move the snake to eat apples and candy."
                + "Eating an apple increases the snake's length by 1 and gives you 5 points. \n"
                +"It also resets the color of the snake back to green.\n"
                + "Eating a candy increases the snake's length by 5 and gives you 10 points. \n"
                + "Candies will also randomly change the snake's color.\n"
                + "Drinking a poisonous potion is bad! It will randomly decrease the snake's length " +
                  "and will decrease your score by 5. \n"
                + "Poisonous potion also changes the snakes color to a dark grey. "
                + "\n\n"
                + "You will lose when your snake hits the wall, when your snake runs into itself, " +
                 "or when your score becomes negative. \n"
                + "Performance is evaluated by calculating a player's score * 0.6 + time * 0.4. The"
                + "\n"
                + "Time is measured in seconds.\n\n"
                + "Happy eating!";
        JOptionPane.showMessageDialog(frame, description);


        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Game in Progress");
        status_panel.add(status);

        //records panel is placed to the west of the gameboard
        final JPanel records_panel = new JPanel();
        frame.add(records_panel, BorderLayout.WEST);
        final String records_display = getScores();
        JLabel records = new JLabel(records_display);
        records_panel.add(records);

        // Main playing area
        final GameCourt court = new GameCourt(status, records);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();

    }
}
