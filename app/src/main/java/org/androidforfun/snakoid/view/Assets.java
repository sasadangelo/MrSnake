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
package org.androidforfun.snakoid.view;

import org.androidforfun.framework.Pixmap;
import org.androidforfun.framework.Sound;

/*
 This class contains the global references to all the assets used in MrSnakeGame game.
 *
 * @author Salvatore D'Angelo
 */
public class Assets {
    public static Pixmap background;
    public static Pixmap logo;

    // the screen used in Snakoid game
    public static Pixmap startscreen;
    public static Pixmap highscoresscreen;
    public static Pixmap gameoverscreen;

    public static Pixmap controlpanelshadow;
    public static Pixmap controlpanel;

    public static Pixmap mainmenu;
    public static Pixmap pausemenu;
    public static Pixmap readymenu;

    public static Pixmap buttons;
    public static Pixmap numbers;

    public static Pixmap headUp;
    public static Pixmap headLeft;
    public static Pixmap headDown;
    public static Pixmap headRight;

    public static Pixmap tailUp;
    public static Pixmap tailDown;
    public static Pixmap tailLeft;
    public static Pixmap tailRight;

    public static Pixmap bodyCornerUpRight;
    public static Pixmap bodyCornerRightDown;
    public static Pixmap bodyCornerDownLeft;
    public static Pixmap bodyCornerLeftUp;

    public static Pixmap bodyHorizontal;
    public static Pixmap bodyVertical;

    public static Pixmap apple;
    public static Pixmap cherries;
    public static Pixmap orange;

    public static Sound click;
    public static Sound eat;
    public static Sound bitten;
}
