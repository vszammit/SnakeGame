package org.cis120.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakePart> body;
    private SnakePart head;
    private Direction direction;
    private Color color;
    private boolean hitWall = false;
    private boolean hitBody = false;
    private boolean grown = false;
    private boolean moved = false;

    public Snake(int x, int y, Color c) {
        body = new LinkedList<>();
        head = new SnakePart(x, y, c);
        body.add(head);
        color = c;
    }

    //detects intersection of head and food
    public boolean intersect(GameObject obj) {
        return head.intersects(obj);
    }

    //grows snake by particular length
    public boolean grow(int length) {
        SnakePart tail = body.peekLast();
        for (int i = 0; i < length; i++) {
            body.addLast(tail);
        }
        grown = true;
        return true;
    }

    //shrinks snake by length
    public boolean shrink (int length) {
        if (length >= body.size()) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                body.removeLast();
            }
            return true;
        }
    }

    //returns body while making sure encapsulation is achieved
    public LinkedList<SnakePart> getBody() {
        LinkedList<SnakePart> temp = new LinkedList<>();
        temp.addAll(body);
        return temp;
    }

    //returns color while making sure encapsulated is achieved
    public Color getColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void setColor(Color c) {
        color = c;
        for (SnakePart part : body) {
            part.setColor(c);
        }
    }

    //Updates the direction of the snake
    public void changeDirection(Direction d) {
        if (d == Direction.RIGHT && direction != Direction.LEFT) {
            direction = d;
        } else if (d == Direction.LEFT && direction != Direction.RIGHT) {
            direction = d;
        } else if (d == Direction.UP && direction != Direction.DOWN) {
            direction = d;
        } else if (d == Direction.DOWN && direction != Direction.UP) {
            direction = d;
        }
    }

    //checks if snake has a direction
    public boolean hasDirection() {
        if (direction == Direction.DOWN) {
            return true;
        } else if (direction == Direction.UP) {
            return true;
        } else if (direction == Direction.RIGHT) {
            return true;
        } else if (direction == Direction.LEFT) {
            return true;
        } else {
            return false;
        }
    }

    //moves snake by adding a heads and removing the last element
    public void move() {
        if (direction == null) {
            return;
        } else if (direction == Direction.RIGHT) {
            head = new SnakePart(head.getPx() + 15, head.getPy(), color);
        } else if (direction == Direction.LEFT) {
            head = new SnakePart(head.getPx() - 15, head.getPy(), color);
        } else if (direction == Direction.UP) {
            head = new SnakePart(head.getPx(), head.getPy() - 15, color);
        } else {
            head = new SnakePart(head.getPx(), head.getPy() + 15, color);
        }
        if (head.hitWall()) {
            hitWall = true;
        } else {
            body.addFirst(head);
            body.removeLast();
        }
        if (grown) {
            moved = true;
        }
    }

    //checks if snake hits wall
    public boolean outOfBounds() {
        return hitWall;
    }

    //checks if snake's head hits its body at any body part
    public boolean bodyCollision() {
        if (moved) {
            Iterator <SnakePart> iter = body.iterator();
            iter.next();
            while (iter.hasNext()) {
                SnakePart c = iter.next();
                if (c.intersects(head)) {
                    hitBody = true;
                }
            }
        }
        return hitBody;
    }

    public void draw(Graphics g) {
        for (SnakePart part : body) {
            part.draw(g);
        }
    }
}