/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Mr Snake project.
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

import java.util.Random;

/*
 * This class represents the Mr Snake world. It is the entry point of the Model.
 * The Mr Snake world is basically a 10x13 grid where the snake can move. Each element of the grid
 * is called cell. Each cell can contain: nothing, snake piece, fruit.
 * The game can be in different state: paused, running, game over and ready.
 *
 * @author Salvatore D'Angelo
 */
public class MrSnakeWorld {
    // Mr Snake world is a 10x13 grid
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

    // the game grid
    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;
    // the user score
    private int score = 0;
    // the snake
    private Snake snake;
    // the current fruit
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
    
    /*
     * This method place a fruit on the grid in a random way. At each instant of time only one fruit
     * is present on the grid. When the snake eat a fruit a new one is generated.
     */
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

    /*
     * This method update the Mr Snake world. It is called once per frame in the Game Loop.
     */
    public void update(float deltaTime) {
        // if the game is over stop immediately
        if (state == GameState.GameOver)
            return;

        tickTime += deltaTime;

        // iterate until the deltaTime time has been spent.
        while (tickTime > tick) {
            tickTime -= tick;
            // at each update the snake advance
            snake.advance();
            // check if the snake ate itself. In this case the game is over.
            if (snake.checkBitten()) {
                state = GameState.GameOver;
                return;
            }

            //if (snake.getSnakeHead().getX() == fruit.getX() && snake.getSnakeHead().getY() == fruit.getY()) {
            // if the snake head collide with a fruit then the snake eat it and the score is
            // increased
            if (snake.getSnakeHead().collide(fruit)) {
                // increase the score
                score += SCORE_INCREMENT;
                // eat the fruit
                snake.eat();
                // if the snake length is equal to the grid cells the game is over. The reason is
                // that it is impossible for the snake to move.
                if (snake.getLength() == WORLD_WIDTH * WORLD_HEIGHT) {
                    state = GameState.GameOver;
                    return;
                } else {
                    // generate a new fruit and place in on the grid randomly
                    placeFruit();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }

    /*
     * Reset the game world.
     */
    public void clear() {
        score = 0;
        snake.reset();
        placeFruit();
        state = GameState.Ready;
    }

    /*
     * Gets the game state.
     */
    public GameState getState() {
        return state;
    }

    /*
     * Sets the game state.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /*
     * Gets the game score.
     */
    public int getScore() {
        return score;
    }

    /*
     * Gets the snake.
     */
    public Snake getSnake() {
        return snake;
    }

    /*
     * Gets the current fruit.
     */
    public Fruit getFruit() {
        return fruit;
    }
}
