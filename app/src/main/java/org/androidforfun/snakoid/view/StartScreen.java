package org.androidforfun.snakoid.view;

import org.androidforfun.snakoid.model.Settings;

import org.androidforfun.snakoid.framework.Game;
import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Input;
import org.androidforfun.snakoid.framework.Screen;

import java.util.List;

public class StartScreen extends Screen {
    public StartScreen(Game game) {
        super(game);               
    }   

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(inBounds(event, 32, 374, 51, 51)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                if(inBounds(event, 64, 220, 192, 42) ) {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }
    
    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    @Override
    public void draw(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.startscreen, 0, 0);
        g.drawPixmap(Assets.logo, 54, 20);
        g.drawPixmap(Assets.mainmenu, 64, 220);
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 32, 370, 0, 0, 51, 51);
        else
            g.drawPixmap(Assets.buttons, 32, 370, 50, 0, 51, 51);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
