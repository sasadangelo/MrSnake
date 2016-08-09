package org.androidforfun.snakoid.model;

import org.androidforfun.framework.Actor;

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
