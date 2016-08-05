package org.androidforfun.snakoid.view;

import android.util.Log;

import org.androidforfun.snakoid.framework.Game;
import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Input.TouchEvent;
import org.androidforfun.snakoid.framework.Pixmap;
import org.androidforfun.snakoid.framework.Screen;
import org.androidforfun.snakoid.framework.TextStyle;
import org.androidforfun.snakoid.model.Settings;
import org.androidforfun.snakoid.model.Snake;
import org.androidforfun.snakoid.model.SnakeBody;
import org.androidforfun.snakoid.model.Fruit;
import org.androidforfun.snakoid.model.SnakeHead;
import org.androidforfun.snakoid.model.SnakeTail;
import org.androidforfun.snakoid.model.SnakoidWorld;

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
        Log.i(LOG_TAG, "draw -- begin");
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        drawSnakoid();
        states.get(SnakoidWorld.getInstance().getState()).draw();
        TextStyle style = new TextStyle();
        style.setColor(0xffffffff);
        style.setTextSize(24);
        style.setStyle(TextStyle.Style.BOLD);
        style.setAlign(TextStyle.Align.CENTER);
        g.drawText(score, g.getWidth()/2, g.getHeight()-16, style);
    }
    
    private void drawSnakoid() {
        Graphics g = game.getGraphics();
        Snake snake = SnakoidWorld.getInstance().getSnake();
        Fruit fruit = SnakoidWorld.getInstance().getFruit();

        Pixmap fruitPixmap = null;
        switch(fruit.getType()) {
            case Fruit.APPLE:
                fruitPixmap = Assets.apple;
                break;
            case Fruit.CHERRIES:
                fruitPixmap = Assets.cherries;
                break;
            case Fruit.ORANGE:
                fruitPixmap = Assets.orange;
                break;
        }
        int x = fruit.getX() * 32;
        int y = fruit.getY() * 32;
        g.drawPixmap(fruitPixmap, x, y);
        
        int len = snake.getLength();
        Pixmap snakeBodyPixmap = null;
        for(int i = 0; i < len; i++) {
            SnakeBody snakeBody = snake.getSnakeBody(i);
            if (snakeBody instanceof SnakeHead) {
                switch (snakeBody.getDirection()) {
                    case UP:
                        snakeBodyPixmap=Assets.headUp;
                        break;
                    case DOWN:
                        snakeBodyPixmap=Assets.headDown;
                        break;
                    case LEFT:
                        snakeBodyPixmap=Assets.headLeft;
                        break;
                    case RIGHT:
                        snakeBodyPixmap=Assets.headRight;
                        break;
                }
            } else if (snakeBody instanceof SnakeTail) {
                switch (snakeBody.getDirection()) {
                    case UP:
                        snakeBodyPixmap=Assets.tailUp;
                        break;
                    case DOWN:
                        snakeBodyPixmap=Assets.tailDown;
                        break;
                    case LEFT:
                        snakeBodyPixmap=Assets.tailLeft;
                        break;
                    case RIGHT:
                        snakeBodyPixmap=Assets.tailRight;
                        break;
                }
            } else {
                switch (snakeBody.getDirection()) {
                    case UP:
                    case DOWN:
                        snakeBodyPixmap=Assets.bodyVertical;
                        break;
                    case LEFT:
                    case RIGHT:
                        snakeBodyPixmap=Assets.bodyHorizontal;
                        break;
                    // The turn LEFT - DOWN anticlockwise is similar to UP - RIGHT:
                    //
                    //  ___
                    // |
                    // |
                    case UP_RIGHT:
                    case LEFT_DOWN:
                        snakeBodyPixmap=Assets.bodyCornerUpRight;
                        break;
                    // The turn UP - LEFT anticlockwise is similar to RIGHT - DOWN:
                    //
                    //  ___
                    //     |
                    //     |
                    case RIGHT_DOWN:
                    case UP_LEFT:
                        snakeBodyPixmap=Assets.bodyCornerRightDown;
                        break;
                    // The turn RIGHT - UP anticlockwise is similar to DOWN - LEFT:
                    //
                    //
                    //     |
                    //  ___|
                    case DOWN_LEFT:
                    case RIGHT_UP:
                        snakeBodyPixmap=Assets.bodyCornerDownLeft;
                        break;
                    // The turn DOWN - RIGHT anticlockwise is similar to LEFT - UP:
                    //
                    //
                    // |
                    // |___
                    case LEFT_UP:
                    case DOWN_RIGHT:
                        snakeBodyPixmap=Assets.bodyCornerLeftUp;
                        break;
                }
            }
            x = snakeBody.getX() * 32;
            y = snakeBody.getY() * 32;
            g.drawPixmap(snakeBodyPixmap, x, y);
        }
        
        g.drawPixmap(Assets.controlpanelshadow, 0, 388);
        g.drawPixmap(Assets.controlpanel, 0, 392);
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
                        SnakoidWorld.getInstance().getSnake().turnAntiClockwise();
                    }
                    if(event.x >= 250 && event.x < 300 && event.y >= 425 && event.y < 475) {
                        SnakoidWorld.getInstance().getSnake().turnClockwise();
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
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.buttons, 5, 20, 50, 100, 51, 51); // pause button
            g.drawPixmap(Assets.buttons, 20, 425, 50, 150, 51, 51);  // left button
            g.drawPixmap(Assets.buttons, 250, 425, 50, 200, 51, 51); // right button
        }
    }
    
    class GamePaused extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
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

            g.drawPixmap(Assets.pausemenu, 79, 100);
            g.drawPixmap(Assets.buttons, 20, 425, 50, 150, 51, 51);  // left button
            g.drawPixmap(Assets.buttons, 250, 425, 50, 200, 51, 51); // right button
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
            g.drawPixmap(Assets.buttons, 20, 425, 50, 150, 51, 51); // left button
            g.drawPixmap(Assets.buttons, 250, 425, 50, 200, 51, 51); // right button
        }
    }

    class GameOver extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
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
            Log.i(LOG_TAG, "draw -- begin");
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