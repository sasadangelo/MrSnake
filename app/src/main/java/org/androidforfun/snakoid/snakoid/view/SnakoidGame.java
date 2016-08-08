package org.androidforfun.snakoid.snakoid.view;

import org.androidforfun.snakoid.framework.Screen;
import org.androidforfun.snakoid.framework.impl.AndroidGame;
import org.androidforfun.snakoid.view.LoadingScreen;

public class SnakoidGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}