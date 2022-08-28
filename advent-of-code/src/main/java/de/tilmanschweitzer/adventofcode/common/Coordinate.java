package de.tilmanschweitzer.adventofcode.common;

import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode
@ToString
public class Coordinate {
    protected final int x;
    protected final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getManhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }
}
