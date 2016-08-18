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

import org.androidforfun.framework.Gdx;
import org.androidforfun.framework.Graphics;
import org.androidforfun.framework.Graphics.PixmapFormat;
import org.androidforfun.framework.Screen;
import org.androidforfun.mrsnake.model.Settings;

/*
 * This class represents the loading screen. It load in memory all the assets used by the game.
 * Usually games show a progress bar in this screen. To simplify the code and since the assets are
 * loaded very quickly I avoided this complication.
 *
 * @author Salvatore D'Angelo
 */
public class LoadingScreen implements Screen {
    private static final String LOG_TAG = "Snakoid.LoadingScreen";

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        Graphics g = Gdx.graphics;

        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);

        // Screens
        Assets.startscreen = g.newPixmap("startscreen.png", PixmapFormat.RGB565);
        Assets.highscoresscreen = Assets.startscreen;
        Assets.gameoverscreen = g.newPixmap("gameover.png", PixmapFormat.RGB565);

        Assets.controlpanel = g.newPixmap("controlpanel.png", PixmapFormat.ARGB4444);
        Assets.controlpanelshadow = g.newPixmap("controlpanelshadow.png", PixmapFormat.ARGB4444);

        // Menus
        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.pausemenu = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.readymenu = g.newPixmap("ready.png", PixmapFormat.ARGB4444);

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

        // Audio effects
        Assets.click = Gdx.audio.newSound("click.ogg");
        Assets.eat = Gdx.audio.newSound("eat.ogg");
        Assets.bitten = Gdx.audio.newSound("bitten.ogg");
        Settings.load(Gdx.fileIO);
        Gdx.game.setScreen(new StartScreen());
    }

    /*
     * Draw nothing.
     */
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
    }

    /*
     * The screen is paused.
     */
    public void pause() {
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