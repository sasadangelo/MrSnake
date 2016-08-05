package org.androidforfun.snakoid.model;

public class SnakeBody {
    protected int x;
    protected int y;
    protected Direction direction;

    public SnakeBody() {
        this.x = 0;
        this.y = 0;
    }

    public SnakeBody(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction=direction;
    }

    public boolean isCorner()  {
        return !isStraight();
    }

    public boolean isStraight()  {
        return (this.direction==Direction.UP || this.direction==Direction.RIGHT ||
                this.direction==Direction.LEFT || this.direction==Direction.DOWN);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction=direction;
    }
}

