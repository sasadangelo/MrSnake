/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Mr Snake project.
 *  This file derive from the Mr Nom project developed by Mario Zechner for the Beginning Android
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
package org.androidforfun.snakoid.model;

import org.androidforfun.framework.Actor;

/*
 * This class represents the fruit eaten by the snake. Possible fruit are: apple. orange and cherries.
 * A fruit has a (x, y) position on the game grid.
 *
 * @author Salvatore D'Angelo
 */
public class Fruit extends Actor {
    public static final int APPLE = 0;
    public static final int CHERRIES = 1;
    public static final int ORANGE = 2;

    private int type;

    public Fruit(int x, int y, int type) {
        super(x, y, 1, 1);
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
