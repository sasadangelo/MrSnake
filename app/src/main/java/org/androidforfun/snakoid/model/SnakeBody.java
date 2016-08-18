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
package org.androidforfun.snakoid.model;

import org.androidforfun.framework.Actor;

/*
 * This class represents a generic Mr Snake piece. It is an actor so it has a (x, y) position on
 * the grid and a size of 1 cell. It has a direction and can move Up, Down, Left and Right.
 *
 * @author Salvatore D'Angelo
 */
public class SnakeBody extends Actor {
    protected Direction direction;

    public SnakeBody() {
        super(0, 0, 1, 1);
    }

    public SnakeBody(int x, int y, Direction direction) {
        super(x, y, 1, 1);
        this.direction=direction;
    }

    public boolean isCorner()  {
        return !isStraight();
    }

    public boolean isStraight()  {
        return (this.direction==Direction.UP || this.direction==Direction.RIGHT ||
                this.direction==Direction.LEFT || this.direction==Direction.DOWN);
    }

    public void moveUp() {
        this.y-=1;
        if (this.y < 0)
            this.y=12;
    }

    public void moveDown() {
        this.y+=1;
        if (this.y > 12)
            this.y=0;
    }

    public void moveLeft() {
        this.x-=1;
        if (this.x < 0)
            this.x=9;
    }

    public void moveRight() {
        this.x+=1;
        if (this.x > 9)
            this.x=0;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction=direction;
    }
}
