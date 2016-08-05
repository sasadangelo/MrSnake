package org.androidforfun.snakoid.model;

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
