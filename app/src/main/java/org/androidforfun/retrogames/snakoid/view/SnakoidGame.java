package org.androidforfun.retrogames.snakoid.view;

import org.androidforfun.retrogames.framework.Screen;
import org.androidforfun.retrogames.framework.impl.AndroidGame;
import org.androidforfun.retrogames.snakoid.view.LoadingScreen;

public class SnakoidGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}