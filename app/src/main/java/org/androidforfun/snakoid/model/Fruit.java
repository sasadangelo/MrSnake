package org.androidforfun.snakoid.model;

public class Fruit {
    public static final int APPLE = 0;
    public static final int CHERRIES = 1;
    public static final int ORANGE = 2;

    private int x, y;
    private int type;

    public Fruit(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
