package org.androidforfun.snakoid.view;

import org.androidforfun.snakoid.framework.Rectangle;
import org.androidforfun.snakoid.model.Settings;
import org.androidforfun.snakoid.framework.Game;
import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Input;
import org.androidforfun.snakoid.framework.Screen;

import java.util.List;

public class StartScreen extends Screen {
    private Rectangle backgroundBounds;
    private Rectangle logoBounds;
    private Rectangle soundButtonBounds;
    private Rectangle mainMenuBounds;
    private Rectangle playMenuBounds;
    private Rectangle highscoresMenuBounds;
    private Rectangle quitMenuBounds;

    public StartScreen(Game game) {
        super(game);               

        backgroundBounds=new Rectangle(0, 0, 320, 480);
        logoBounds=new Rectangle(54, 20, 213, 57);
        soundButtonBounds=new Rectangle(32, 370, 50, 50);
        mainMenuBounds=new Rectangle(64, 220, 192, 128);
        playMenuBounds=new Rectangle(64, 220, 192, 42);
        highscoresMenuBounds=new Rectangle(64, 220 + 42, 192, 42);
        quitMenuBounds=new Rectangle(64, 220 + 84, 192, 42);
    }   

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(soundButtonBounds.contains(event.x, event.y)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                if(playMenuBounds.contains(event.x, event.y)) {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(highscoresMenuBounds.contains(event.x, event.y)) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(quitMenuBounds.contains(event.x, event.y)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    System.exit(1);
                    return;
                }
            }
        }
    }

    @Override
    public void draw(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.startscreen, backgroundBounds.getX(), backgroundBounds.getY());
        g.drawPixmap(Assets.logo, logoBounds.getX(), logoBounds.getY());
        g.drawPixmap(Assets.mainmenu, mainMenuBounds.getX(), mainMenuBounds.getY());
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 0, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
        else
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 50, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
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
