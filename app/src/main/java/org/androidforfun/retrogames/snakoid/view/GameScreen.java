package org.androidforfun.retrogames.snakoid.view;

import android.util.Log;

import org.androidforfun.retrogames.framework.Game;
import org.androidforfun.retrogames.framework.Graphics;
import org.androidforfun.retrogames.framework.Input.TouchEvent;
import org.androidforfun.retrogames.framework.Pixmap;
import org.androidforfun.retrogames.framework.Screen;
import org.androidforfun.retrogames.snakoid.model.Settings;
import org.androidforfun.retrogames.snakoid.model.Snake;
import org.androidforfun.retrogames.snakoid.model.SnakePart;
import org.androidforfun.retrogames.snakoid.model.Stain;
import org.androidforfun.retrogames.snakoid.model.SnakoidWorld;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends Screen {
    int oldScore = 0;
    private static final String LOG_TAG = "Snakoid.GameScreen";
    String score = "0";
    Map<SnakoidWorld.GameState, GameState> states = new EnumMap<SnakoidWorld.GameState, GameState>(SnakoidWorld.GameState.class);
    
    public GameScreen(Game game) {
        super(game);
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(SnakoidWorld.GameState.Paused, new GamePaused());
        states.put(SnakoidWorld.GameState.Ready, new GameReady());
        states.put(SnakoidWorld.GameState.Running, new GameRunning());
        states.put(SnakoidWorld.GameState.GameOver, new GameOver());
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
        Log.i(LOG_TAG, "present -- begin");
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.gamescreen, 0, 0);
        drawSnakoid();
        states.get(SnakoidWorld.getInstance().getState()).draw();
        drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);                
    }
    
    private void drawSnakoid() {
        Graphics g = game.getGraphics();
        Snake snake = SnakoidWorld.getInstance().getSnake();
        SnakePart head = snake.parts.get(0);
        Stain stain = SnakoidWorld.getInstance().getStain();
        
        
        Pixmap stainPixmap = null;
        if(stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;
        if(stain.type == Stain.TYPE_2)
            stainPixmap = Assets.stain2;
        if(stain.type == Stain.TYPE_3)
            stainPixmap = Assets.stain3;
        int x = stain.x * 32;
        int y = stain.y * 32;      
        g.drawPixmap(stainPixmap, x, y);             
        
        int len = snake.parts.size();
        for(int i = 1; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.tail, x, y);
        }
        
        Pixmap headPixmap = null;
        if(snake.direction == Snake.UP) 
            headPixmap = Assets.headUp;
        if(snake.direction == Snake.LEFT) 
            headPixmap = Assets.headLeft;
        if(snake.direction == Snake.DOWN) 
            headPixmap = Assets.headDown;
        if(snake.direction == Snake.RIGHT) 
            headPixmap = Assets.headRight;        
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
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

            int srcX = 0;
            int srcWidth = 0;
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
            Log.i(LOG_TAG, "updateRunning -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x >= 5 && event.x < 55 && event.y >= 20 && event.y < 70) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Paused);
                        return;
                    }
                }
                if(event.type == TouchEvent.TOUCH_DOWN) {
                    if(event.x >= 20 && event.x < 70 && event.y >= 425 && event.y < 475) {
                        SnakoidWorld.getInstance().getSnake().turnLeft();
                    }
                    if(event.x >= 250 && event.x < 300 && event.y >= 425 && event.y < 475) {
                        SnakoidWorld.getInstance().getSnake().turnRight();
                    }
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
            Log.i(LOG_TAG, "drawRunningUI -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.buttons, 5, 20, 50, 100, 51, 51); // pause button
            g.drawPixmap(Assets.buttons, 20, 425, 50, 50, 51, 51);  // left button
            g.drawPixmap(Assets.buttons, 250, 425, 0, 50, 51, 51); // right button
        }
    }
    
    class GamePaused extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "updatePaused -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x > 80 && event.x <= 240) {
                        if(event.y > 100 && event.y <= 148) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Running);
                            return;
                        }                    
                        if(event.y > 148 && event.y < 196) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            game.setScreen(new StartScreen(game));
                            return;
                        }
                    }
                }
            }
        }
        
        void draw() {
            Log.i(LOG_TAG, "drawPausedUI -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.pausemenu, 100, 100);
            g.drawPixmap(Assets.buttons, 20, 425, 50, 50, 51, 51);  // left button
            g.drawPixmap(Assets.buttons, 250, 425, 0, 50, 51, 51); // right button
        }
    }
    
    class GameReady extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "updateReady -- begin");
            if(touchEvents.size() > 0)
                SnakoidWorld.getInstance().setState(SnakoidWorld.GameState.Running);
        }

        void draw() {
            Log.i(LOG_TAG, "drawReadyUI -- begin");
            Graphics g = game.getGraphics();
            
            g.drawPixmap(Assets.readymenu, 65, 100);
            g.drawPixmap(Assets.buttons, 20, 425, 50, 50, 51, 51); // left button
            g.drawPixmap(Assets.buttons, 250, 425, 0, 50, 51, 51); // right button
        }
    }

    class GameOver extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "updateGameOver -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
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
            Log.i(LOG_TAG, "drawGameOverUI -- begin");
            Graphics g = game.getGraphics();
            
            g.drawPixmap(Assets.gameoverscreen, 0, 0);
            g.drawPixmap(Assets.buttons, 128, 200, 0, 100, 51, 51);
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