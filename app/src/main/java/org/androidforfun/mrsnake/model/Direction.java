/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Mr Snake project.
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
package org.androidforfun.mrsnake.model;

/*
 * This enum represents the possible direction the snake piece can have. Head and Body can go:
 * UP, DOWN, RIGHT and LEFT. Corner Body pieces can go, when it turns clockwise: UP_RIGHT,
 * RIGHT_DOWN, DOWN_LEFT, LEFT_UP. Corner Body pieces can go, when it turns anticlockwise: LEFT_DOWN,
 * DOWN_RIGHT, RIGHT_UP, UP_LEFT.
 * From a graphical point of view the following directions are exactly the same:
 *     UP_RIGHT=LEFT_DOWN
 *     RIGHT_DOWN=UP_LEFT
 *     DOWN_LEFT=RIGHT_UP
 *     LEFT_UP=DOWN_RIGHT
 *
 * @author Salvatore D'Angelo
 */
public enum Direction {
    UP, // Direction towards UP of a straight snake piece
    LEFT, // Direction towards LEFT of a straight snake piece
    DOWN, // Direction towards DOWN of a straight snake piece
    RIGHT, // Direction towards RIGHT of a straight snake piece
    UP_RIGHT, // Corner direction towards UP and then RIGHT (clockwise)
    RIGHT_DOWN, // Direction towards RIGHT and then DOWN of a corner snake piece (clockwise)
    DOWN_LEFT, // Direction towards DOWN and then LEFT of a corner snake piece (clockwise)
    LEFT_UP, // Direction towards LEFT and then UP of a corner snake piece (clockwise)
    LEFT_DOWN, // Direction towards LEFT and then DOWN of a corner snake piece (anticlockwise)
    DOWN_RIGHT, // Direction towards DOWN and then RIGHT of a corner snake piece (anticlockwise)
    RIGHT_UP, // Direction towards RIGHT and then UP of a corner snake piece (anticlockwise)
    UP_LEFT // Direction towards UP and then LEFT of a corner snake piece (anticlockwise)
}
