package org.androidforfun.retrogames.snakoid.view;

import android.util.Log;
import org.androidforfun.retrogames.framework.Game;
import org.androidforfun.retrogames.framework.Graphics;
import org.androidforfun.retrogames.framework.Graphics.PixmapFormat;
import org.androidforfun.retrogames.framework.Screen;
import org.androidforfun.retrogames.snakoid.model.Settings;

public class LoadingScreen extends Screen {
    private static final String LOG_TAG = "Snakoid.LoadingScreen";
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);

        Assets.startscreen = g.newPixmap("startscreen.png", PixmapFormat.RGB565);
        Assets.gamescreen = g.newPixmap("gamescreen.png", PixmapFormat.RGB565);
        Assets.highscoresscreen = Assets.startscreen;
        Assets.gameoverscreen = g.newPixmap("gameover.png", PixmapFormat.RGB565);

        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.readymenu = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pausemenu = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);

        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);

        Assets.headUp = g.newPixmap("headup.png", PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("headright.png", PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("tail.png", PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("stain3.png", PixmapFormat.ARGB4444);
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new StartScreen(game));
    }

    @Override
    public void draw(float deltaTime) {

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