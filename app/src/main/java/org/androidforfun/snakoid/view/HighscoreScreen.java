package org.androidforfun.snakoid.view;

import org.androidforfun.framework.Rectangle;
import org.androidforfun.snakoid.model.Settings;
import org.androidforfun.framework.Game;
import org.androidforfun.framework.Graphics;
import org.androidforfun.framework.Input.TouchEvent;
import org.androidforfun.framework.Screen;

import java.util.List;

public class HighscoreScreen extends Screen {
    private Rectangle backgroundBounds;
    private Rectangle backButtonBounds;

    String lines[] = new String[5];

    public HighscoreScreen(Game game) {
        super(game);

        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }

        backgroundBounds=new Rectangle(0, 0, 320, 480);
        backButtonBounds=new Rectangle(32, 370, 50, 50);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (backButtonBounds.contains(event.x, event.y)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new StartScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void draw(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.highscoresscreen, backgroundBounds.getX(), backgroundBounds.getY());

        int y = 100;
        for (int i = 0; i < 5; i++) {
            drawText(g, lines[i], 20, y);
            y += 50;
        }
        g.drawPixmap(Assets.buttons, backButtonBounds.getX(), backButtonBounds.getY(), 50, 50,
                backButtonBounds.getWidth()+1, backButtonBounds.getHeight()+1);
    }

    public void drawText(Graphics g, String line, int x, int y) {
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
