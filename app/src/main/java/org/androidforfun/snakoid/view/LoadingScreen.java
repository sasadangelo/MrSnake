package org.androidforfun.snakoid.view;

import android.util.Log;
import org.androidforfun.snakoid.framework.Game;
import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Graphics.PixmapFormat;
import org.androidforfun.snakoid.framework.Pixmap;
import org.androidforfun.snakoid.framework.Screen;
import org.androidforfun.snakoid.model.Settings;

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
        Assets.highscoresscreen = Assets.startscreen;
        Assets.gameoverscreen = g.newPixmap("gameover.png", PixmapFormat.RGB565);

        Assets.controlpanel = g.newPixmap("controlpanel.png", PixmapFormat.ARGB4444);
        Assets.controlpanelshadow = g.newPixmap("controlpanelshadow.png", PixmapFormat.ARGB4444);

        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.readymenu = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pausemenu = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);

        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);

        Assets.headUp = g.newPixmap("headup.png", PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("headright.png", PixmapFormat.ARGB4444);

        Assets.tailUp = g.newPixmap("tailup.png", PixmapFormat.ARGB4444);
        Assets.tailDown = g.newPixmap("taildown.png", PixmapFormat.ARGB4444);
        Assets.tailLeft = g.newPixmap("tailleft.png", PixmapFormat.ARGB4444);
        Assets.tailRight = g.newPixmap("tailright.png", PixmapFormat.ARGB4444);

        Assets.bodyHorizontal = g.newPixmap("body-horizontal.png", PixmapFormat.ARGB4444);
        Assets.bodyVertical = g.newPixmap("body-vertical.png", PixmapFormat.ARGB4444);

        Assets.bodyCornerUpRight=g.newPixmap("body-corner-up-right.png", PixmapFormat.ARGB4444);
        Assets.bodyCornerRightDown=g.newPixmap("body-corner-right-down.png", PixmapFormat.ARGB4444);
        Assets.bodyCornerDownLeft=g.newPixmap("body-corner-down-left.png", PixmapFormat.ARGB4444);
        Assets.bodyCornerLeftUp=g.newPixmap("body-corner-left-up.png", PixmapFormat.ARGB4444);

        Assets.apple = g.newPixmap("apple.png", PixmapFormat.ARGB4444);
        Assets.cherries = g.newPixmap("cherries.png", PixmapFormat.ARGB4444);
        Assets.orange = g.newPixmap("orange.png", PixmapFormat.ARGB4444);

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