package org.androidforfun.retrogames.snakoid;

import org.androidforfun.retrogames.framework.Screen;
import org.androidforfun.retrogames.framework.impl.AndroidGame;

public class SnakoidGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
}