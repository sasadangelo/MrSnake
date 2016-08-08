package org.androidforfun.snakoid.view;

import android.util.Log;

import org.androidforfun.snakoid.framework.Game;
import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Input.TouchEvent;
import org.androidforfun.snakoid.framework.Pixmap;
import org.androidforfun.snakoid.framework.Rectangle;
import org.androidforfun.snakoid.framework.Screen;
import org.androidforfun.snakoid.framework.TextStyle;
import org.androidforfun.snakoid.model.Settings;
import org.androidforfun.snakoid.model.SnakoidWorld;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends Screen {
    int oldScore = 0;
    private static final String LOG_TAG = "Snakoid.GameScreen";
    String score = "0";
    Map<SnakoidWorld.GameState, GameState> states = new EnumMap<>(SnakoidWorld.GameState.class);

    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle resumeMenuBounds;
    private Rectangle homeMenuBounds;

    private SnakoidWorldRenderer renderer;

    public GameScreen(Game game) {
        super(game);
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(SnakoidWorld.GameState.Paused, new GamePaused());
        states.put(SnakoidWorld.GameState.Ready, new GameReady());
        states.put(SnakoidWorld.GameState.Running, new GameRunning());
        states.put(SnakoidWorld.GameState.GameOver, new GameOver());

        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(20, 425, 50, 50);
        rightButtonBounds=new Rectangle(250, 425, 50, 50);
        resumeMenuBounds=new Rectangle(80, 100, 160, 48);
        homeMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);

        renderer = new SnakoidWorldRenderer(game.getGraphics());
    }

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        states.get(SnakoidWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }

    @Override
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        renderer.draw();
        states.get(SnakoidWorld.getInstance().getState()).draw();
        TextStyle style = new TextStyle();
        style.setColor(0xffffffff);
        style.setTextSize(24);
        style.setStyle(TextStyle.Style.BOLD);
        style.setAlign(TextStyle.Align.CENTER);
        g.drawText(score, g.getWidth()/2, g.getHeight()-16, style);
    }
    
    public void drawText(Graphics g, String line, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

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

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
    
    @Override
    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");
        if(SnakoidWorld.getInstance().getState() == SnakoidWorld.GameState.Running)
            SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Paused);

        if(SnakoidWorld.getInstance().getState() == SnakoidWorld.GameState.GameOver) {
            Settings.addScore(SnakoidWorld.getInstance().getScore());
            Settings.save(game.getFileIO());
        }
    }

    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }
    
    class GameRunning extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Paused);
                            return;
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            SnakoidWorld.getInstance().getSnake().turnAntiClockwise();
                        }
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            SnakoidWorld.getInstance().getSnake().turnClockwise();
                        }
                        break;
                }
            }

            SnakoidWorld.getInstance().update(deltaTime);
            if (SnakoidWorld.getInstance().getState() == SnakoidWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }
            if(oldScore != SnakoidWorld.getInstance().getScore()) {
                oldScore = SnakoidWorld.getInstance().getScore();
                score = "" + oldScore;
                if(Settings.soundEnabled)
                    Assets.eat.play(1);
            }
        }
        
        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            g.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 150,
                    leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1);  // left button
            g.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 50, 200,
                    rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        }
    }
    
    class GamePaused extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(resumeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Running);
                        return;
                    }
                    if(homeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new StartScreen(game));
                        return;
                    }
                }
            }
        }
        
        void draw() {
            Log.i(LOG_TAG, "drawPausedUI -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.pausemenu, 79, 100);
            g.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 150,
                    leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1);  // left button
            g.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 50, 200,
                    rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        }
    }
    
    class GameReady extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            if(touchEvents.size() > 0)
                SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Running);
        }

        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();
            
            g.drawPixmap(Assets.readymenu, 58, 100);
            g.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 150,
                    leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1);  // left button
            g.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 50, 200,
                    rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        }
    }

    class GameOver extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (xButtonBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new StartScreen(game));
                        SnakoidWorld.getInstance().clear();
                        return;
                    }
                }
            }
        }
        
        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();
            
            g.drawPixmap(Assets.gameoverscreen, 0, 0);
            g.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1);
            drawText(g, "" + SnakoidWorld.getInstance().getScore(), 180, 280);
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}