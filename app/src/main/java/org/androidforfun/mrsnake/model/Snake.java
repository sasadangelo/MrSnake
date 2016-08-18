/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Mr Snake project.
 *  This file derive from the Mr Nom project developed by Mario Zechner for the Beginning Android
 *  Games book (chapter 6).
 *
 *  Mr Snake is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mr Snake is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License.
 */
package org.androidforfun.mrsnake.model;

import java.util.ArrayList;
import java.util.List;

/*
 * This class represents the snake. A snake is composed by several pieces described by the class
 * SnakeBody. In particular the first piece is the head represented by SnakeHead class and the last
 * piece is the tail represented by SnakeTail class.
 *
 * @author Salvatore D'Angelo
 */
public class Snake {
    // the list of pieces of the snake
    private List<SnakeBody> parts = new ArrayList<>();
    // the direction of the snake. Possible values are: UP, DOWN, LEFT and RIGHT.
    private Direction direction;
    // when the head of the snake turn this variable contains the direction of the neck. Its value
    // depends on if the snake turned clockwise or anticlockwise. In the former case the possible
    // values are: UP_RIGHT, RIGHT_DOWN, DOWN_LEFT, LEFT_UP. In the latter case the possible values
    // are: UP_LEFT, LEFT_DOWN, DOWN_RIGHT, RIGHT_UP.
    private Direction neckDirection;

    public Snake() {
        reset();
    }

    /*
     * The snake is turned clockwise. The new direction and neck direction depends on the old
     * direction.
     *     UP -> LEFT - UP_LEFT
     *     LEFT -> DOWN - LEFT_DOWN
     *     DOWN -> RIGHT - DOWN_RIGHT
     *     RIGHT -> UP - RIGHT_UP
     */
    public void turnAntiClockwise() {
        switch (direction) {
            case UP:
                neckDirection=Direction.UP_LEFT;
                direction=Direction.LEFT;
                break;
            case LEFT:
                neckDirection=Direction.LEFT_DOWN;
                direction=Direction.DOWN;
                break;
            case DOWN:
                neckDirection=Direction.DOWN_RIGHT;
                direction=Direction.RIGHT;
                break;
            case RIGHT:
                neckDirection=Direction.RIGHT_UP;
                direction=Direction.UP;
                break;
        }
    }

    /*
     * The snake is turned anticlockwise. The new direction and neck direction depends on the old
     * direction.
     *     UP -> RIGHT - UP_RIGHT
     *     RIGHT -> DOWN - RIGHT_DOWN
     *     DOWN -> LEFT - DOWN_LEFT
     *     LEFT -> UP - LEFT_UP
     */
    public void turnClockwise() {
        switch (direction) {
            case UP:
                neckDirection=Direction.UP_RIGHT;
                direction=Direction.RIGHT;
                break;
            case RIGHT:
                neckDirection=Direction.RIGHT_DOWN;
                direction=Direction.DOWN;
                break;
            case DOWN:
                neckDirection=Direction.DOWN_LEFT;
                direction=Direction.LEFT;
                break;
            case LEFT:
                neckDirection=Direction.LEFT_UP;
                direction=Direction.UP;
                break;
        }
    }

    /*
     * When the snake eat a fruti its size must be increased by 1 piece. This piece is added in the
     * middle so the tail should shift by a position. The direction of this shift depends on the
     * current direction of the tail
     */
    public void eat() {
        // add a new piece to the snake
        SnakeTail tail = (SnakeTail) parts.get(parts.size()-1);
        parts.add(parts.size()-1, new SnakeBody(tail.getX(), tail.getY(), tail.direction));
        // shift the tail by 1 position with a direction that depends on its current position.
        switch (tail.direction) {
            case UP:
                tail.moveDown();
                break;
            case DOWN:
                tail.moveUp();
                break;
            case LEFT:
                tail.moveRight();
                break;
            case RIGHT:
                tail.moveLeft();
                break;
        }
    }

    /*
     * Advance the snake on the grid by a position that depends on the current snake direction.
     */
    public void advance() {
        int last = parts.size() - 1;
        SnakeHead head = (SnakeHead) parts.get(0);
        SnakeBody neck = parts.get(1);
        SnakeTail tail = (SnakeTail) parts.get(last);

        // move all the snake pieces but head, one step forward
        for(int i = last; i > 0; i--) {
            SnakeBody before = parts.get(i-1);
            SnakeBody part = parts.get(i);
            part.setX(before.getX());
            part.setY(before.getY());
            part.direction=before.direction;
        }

        // move the head towards the snake direction.
        head.direction=direction;
        switch (direction) {
            case UP:
                head.moveUp();
                break;
            case LEFT:
                head.moveLeft();
                break;
            case DOWN:
                head.moveDown();
                break;
            case RIGHT:
                head.moveRight();
                break;
        }

        // determine the neck direction
        switch (neckDirection) {
            case UP_RIGHT:
            case LEFT_UP:
            case DOWN_LEFT:
            case RIGHT_DOWN:
            case LEFT_DOWN:
            case UP_LEFT:
            case DOWN_RIGHT:
            case RIGHT_UP:
                neck.direction=neckDirection;
                neckDirection=Direction.UP;
                break;
        }

        // determine the tail direction
        switch (tail.direction) {
            case DOWN_LEFT:
            case UP_LEFT:
                tail.direction=Direction.LEFT;
                break;
            case LEFT_UP:
            case RIGHT_UP:
                tail.direction=Direction.UP;
                break;
            case RIGHT_DOWN:
            case LEFT_DOWN:
                tail.direction=Direction.DOWN;
                break;
            case UP_RIGHT:
            case DOWN_RIGHT:
                tail.direction=Direction.RIGHT;
                break;
        }
    }

    /*
     * Initializes the snake with a head, a tail and 2 pieces in the middle.
     * Direction is UP.
     */
    public void reset() {
        direction = Direction.UP;
        neckDirection = Direction.UP;
        parts.clear();
        parts.add(new SnakeHead(5, 6, Direction.UP));
        parts.add(new SnakeBody(5, 7, Direction.UP));
        parts.add(new SnakeBody(5, 8, Direction.UP));
        parts.add(new SnakeTail(5, 9, Direction.UP));
    }

    /*
     * Check if snake eat itself.
     */
    public boolean checkBitten() {
        int len = parts.size();
        SnakeBody head = parts.get(0);
        for(int i = 1; i < len; i++) {
            SnakeBody part = parts.get(i);
            if (head.collide(part))
                return true;
        }
        return false;
    }

    public SnakeHead getSnakeHead() {
        return (SnakeHead) parts.get(0);
    }
    public SnakeBody getSnakeBody(int i) {
        return parts.get(i);
    }
    public int getLength() {
        return parts.size();
    }
}
