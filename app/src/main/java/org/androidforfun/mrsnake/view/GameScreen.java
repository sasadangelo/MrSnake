/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Mr Snake project.
 *  This file derives from the Mr Nom project developed by Mario Zechner for the Beginning Android
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
package org.androidforfun.mrsnake.view;

import android.util.Log;

import org.androidforfun.mrsnake.model.MrSnakeWorld;
import org.androidforfun.mrsnake.model.Settings;
import org.androidforfun.framework.Gdx;
import org.androidforfun.framework.Graphics;
import org.androidforfun.framework.Input.TouchEvent;
import org.androidforfun.framework.Rectangle;
import org.androidforfun.framework.Screen;
import org.androidforfun.framework.TextStyle;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/*
 * This class represents the game screen. The processing and rendering depends on the game state managed
 * by State pattern. The update and draw method are delegated to:
 *    GamePause.update, GamePause.draw
 *    GameReady.update, GameReady.draw
 *    GameRunning.update, GameRunning.draw
 *    GameOver.update, GameOver.draw
 *
 * depending on the status of the game.
 *
 * @author Salvatore D'Angelo
 */
public class GameScreen implements Screen {
    int oldScore = 0;
    private static final String LOG_TAG = "Snakoid.GameScreen";
    String score = "0";
    Map<MrSnakeWorld.GameState, GameState> states = new EnumMap<>(MrSnakeWorld.GameState.class);

    private Rectangle gameoverScreenBounds;
    private Rectangle gameScreenBounds;
    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle pauseMenuBounds;
    private Rectangle readyMenuBounds;
    private Rectangle homeMenuBounds;
    private Rectangle controlPanelBounds;
    private Rectangle controlPanelShadowBounds;

    private MrSnakeWorldRenderer renderer;

    public GameScreen() {
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(MrSnakeWorld.GameState.Paused, new GamePaused());
        states.put(MrSnakeWorld.GameState.Ready, new GameReady());
        states.put(MrSnakeWorld.GameState.Running, new GameRunning());
        states.put(MrSnakeWorld.GameState.GameOver, new GameOver());

        gameoverScreenBounds=new Rectangle(0, 0, 320, 480);
        gameScreenBounds=new Rectangle(0, 0, 320, 480);
        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(20, 425, 50, 50);
        rightButtonBounds=new Rectangle(250, 425, 50, 50);
        pauseMenuBounds=new Rectangle(79, 100, 160, 48);
        readyMenuBounds=new Rectangle(58, 100, 225, 96);
        homeMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);
        controlPanelBounds=new Rectangle(0, 388, 320, 88);
        controlPanelShadowBounds=new Rectangle(0, 392, 320, 88);

        renderer = new MrSnakeWorldRenderer();
    }

    /*
     * The update method is delegated to:
     *    GamePause.update
     *    GameReady.update
     *    GameRunning.update
     *    GameOver.update
     *
     * depending on the status of the game.
     */
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();
        states.get(MrSnakeWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }

    /*
     * The draw method is delegated to:
     *    GamePause.draw
     *    GameReady.draw
     *    GameRunning.draw
     *    GameOver.draw
     *
     * depending on the status of the game.
     */
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");

        Gdx.graphics.drawPixmap(Assets.background, gameoverScreenBounds.getX(), gameoverScreenBounds.getY());
        // render the game world.
        renderer.draw();
        // draw the control panel
        Gdx.graphics.drawPixmap(Assets.controlpanelshadow, controlPanelBounds.getX(), controlPanelBounds.getY());
        Gdx.graphics.drawPixmap(Assets.controlpanel, controlPanelShadowBounds.getX(), controlPanelShadowBounds.getY());
        // draw the buttons.
        Gdx.graphics.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 150,
                leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1);  // left button
        Gdx.graphics.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 50, 200,
                rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button

        // draw score on control panel
        TextStyle style = new TextStyle();
        style.setColor(0xffffffff);
        style.setTextSize(24);
        style.setStyle(TextStyle.Style.BOLD);
        style.setAlign(TextStyle.Align.CENTER);
        Gdx.graphics.drawText(score, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-16, style);

        states.get(MrSnakeWorld.getInstance().getState()).draw();
    }

    /*
     * Draw text on the screen in the (x, y) position.
     */
    public void drawText(String text, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char character = text.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            Gdx.graphics.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    /*
     * The screen is paused.
     */
    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");

        if(MrSnakeWorld.getInstance().getState() == MrSnakeWorld.GameState.Running)
            MrSnakeWorld.getInstance().setState(MrSnakeWorld.GameState.Paused);

        if(MrSnakeWorld.getInstance().getState() == MrSnakeWorld.GameState.GameOver) {
            Settings.addScore(MrSnakeWorld.getInstance().getScore());
            Settings.save(Gdx.fileIO);
        }
    }

    /*
     * The abstract class representing a generic State. Used to implement the State pattern.
     */
    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }

    /*
     * This class represents the game screen in running state. It will be responsible to update and
     * draw when the game is running.
     *
     * @author Salvatore D'Angelo
     */
    class GameRunning extends GameState {
        /*
         * Update the game when it is in running state. The method catch the user input and
         * depending on it will move, rotate or accelerate the falling shape. It can also pause the
         * game and check for game over.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameRunning.update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            MrSnakeWorld.getInstance().setState(MrSnakeWorld.GameState.Paused);
                            return;
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            MrSnakeWorld.getInstance().getSnake().turnAntiClockwise();
                        }
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            MrSnakeWorld.getInstance().getSnake().turnClockwise();
                        }
                        break;
                }
            }

            MrSnakeWorld.getInstance().update(deltaTime);
            if (MrSnakeWorld.getInstance().getState() == MrSnakeWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }
            if(oldScore != MrSnakeWorld.getInstance().getScore()) {
                oldScore = MrSnakeWorld.getInstance().getScore();
                score = "" + oldScore;
                if(Settings.soundEnabled)
                    Assets.eat.play(1);
            }
        }

        /*
         * Draw the game in running state.
         */
        void draw() {
            Log.i(LOG_TAG, "GameRunning.draw -- begin");

            // draw the pause button
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
        }
    }

    /*
     * This class represents the game screen in pause state. It will be responsible to update and
     * draw when the game is paused.
     *
     * @author Salvatore D'Angelo
     */
    class GamePaused extends GameState {
        /*
         * Update the game when it is in paused state. The method catch the user input and
         * depending on it will resume the game or return to the start screen.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GamePaused.update -- begin");

            // Check if user asked to resume the game or come back to the start screen.
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(pauseMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        MrSnakeWorld.getInstance().setState(MrSnakeWorld.GameState.Running);
                        return;
                    }
                    if(homeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        return;
                    }
                }
            }
        }

        /*
         * Draw the game in paused state.
         */
        void draw() {
            Log.i(LOG_TAG, "GamePaused.draw -- begin");

            // draw the pause menu
            Gdx.graphics.drawPixmap(Assets.pausemenu, pauseMenuBounds.getX(), pauseMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen in ready state. It will be responsible to update and
     * draw when the game is ready.
     *
     * @author Salvatore D'Angelo
     */
    class GameReady extends GameState {
        /*
         * Update the game when it is in ready state. The method catch the user input and
         * resume the game.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameReady.update -- begin");
            if(touchEvents.size() > 0)
                MrSnakeWorld.getInstance().setState(MrSnakeWorld.GameState.Running);
        }

        /*
         * Draw the game in ready state.
         */
        void draw() {
            Log.i(LOG_TAG, "GameReady.draw -- begin");
            Graphics g = Gdx.graphics;

            g.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            // draw the ready menu
            g.drawPixmap(Assets.readymenu, readyMenuBounds.getX(), readyMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen when it is over. It will be responsible to update and
     * draw when the game is over.
     *
     * @author Salvatore D'Angelo
     */
    class GameOver extends GameState {
        /*
         * Update the game when it is over. The method catch the user input and return to the
         * start screen.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameOver.update -- begin");
            // check if the x button is pressed.
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (xButtonBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        MrSnakeWorld.getInstance().clear();
                        return;
                    }
                }
            }
        }

        /*
         * Draw the game when it si over.
         */
        void draw() {
            Log.i(LOG_TAG, "GameOver.draw -- begin");
            Graphics g = Gdx.graphics;

            // draw pause button
            g.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            // draw black transparent background
            g.drawPixmap(Assets.gameoverscreen, gameoverScreenBounds.getX(), gameoverScreenBounds.getY());
            // draw X button
            g.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1);
            drawText("" + MrSnakeWorld.getInstance().getScore(), 180, 280);
        }
    }

    /*
     * The screen is resumed.
     */
    public void resume() {
    }

    /*
     * The screen is disposed.
     */
    public void dispose() {
    }
}