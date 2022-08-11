package de.tilmanschweitzer.adventofcode.aoc2016.day08.screen;

import de.tilmanschweitzer.adventofcode.common.Coordinate;

public class ScreenPixel extends Coordinate {

    private boolean on = false;

    private ScreenPixel(int x, int y) {
        super(x, y);
    }

    public static ScreenPixel of(Coordinate coordinate) {
        return new ScreenPixel(coordinate.getX(), coordinate.getY());
    }

    public boolean isOn() {
        return on;
    }

    public void turnOn() {
        on = true;
    }

    public void turnOff() {
        on = false;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
