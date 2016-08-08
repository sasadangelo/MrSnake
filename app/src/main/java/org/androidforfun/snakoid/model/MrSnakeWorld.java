package org.androidforfun.snakoid.model;

import java.util.Random;

public class MrSnakeWorld {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;

    static final int SCORE_INCREMENT = 1;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    // the possible game status values
    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    private GameState state = GameState.Ready;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;
    // the user score
    private int score = 0;

    private Snake snake;
    private Fruit fruit;

    // the private static instance used to implement the Singleton pattern.
    private static MrSnakeWorld instance = null;
    
    private MrSnakeWorld() {
        snake = new Snake();
        placeFruit();
    }

    public static MrSnakeWorld getInstance() {
        if (instance == null) {
            instance = new MrSnakeWorld();
        }
        return instance;
    }
    
    private void placeFruit() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.getLength();
        for (int i = 0; i < len; i++) {
            SnakeBody part = snake.getSnakeBody(i);
            fields[part.getX()][part.getY()] = true;
        }

        int fruitX = random.nextInt(WORLD_WIDTH);
        int fruitY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (!fields[fruitX][fruitY])
                break;
            fruitX += 1;
            if (fruitX >= WORLD_WIDTH) {
                fruitX = 0;
                fruitY += 1;
                if (fruitY >= WORLD_HEIGHT) {
                    fruitY = 0;
                }
            }
        }
        fruit = new Fruit(fruitX, fruitY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (state == GameState.GameOver)
            return;

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            snake.advance();
            if (snake.checkBitten()) {
                state = GameState.GameOver;
                return;
            }

            if (snake.getSnakeHead().getX() == fruit.getX() && snake.getSnakeHead().getY() == fruit.getY()) {
                score += SCORE_INCREMENT;
                snake.eat();
                if (snake.getLength() == WORLD_WIDTH * WORLD_HEIGHT) {
                    state = GameState.GameOver;
                    return;
                } else {
                    placeFruit();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }

    public void clear() {
        score = 0;
        snake.reset();
        placeFruit();
        state = GameState.Ready;
    }
    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Snake getSnake() {
        return snake;
    }

    public Fruit getFruit() {
        return fruit;
    }
}
