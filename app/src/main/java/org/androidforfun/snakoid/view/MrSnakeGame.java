package org.androidforfun.snakoid.view;

import org.androidforfun.framework.Screen;
import org.androidforfun.framework.impl.AndroidGame;

public class MrSnakeGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}