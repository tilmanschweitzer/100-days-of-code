package de.tilmanschweitzer.adventofcode.aoc2016.day08;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScreenTest {

    @Test
    public void testToString() {
        final Screen screen = Screen.of(5, 4);
        final String expectedString = ".....\n" +
                ".....\n" +
                ".....\n" +
                ".....";

        assertEquals(expectedString, screen.toString());
    }

    @Test
    public void turnOn() {
        final Screen screen = Screen.of(7, 3);
        screen.turnOn(5, 1);

        final String expectedString = ".......\n" +
                ".....#.\n" +
                ".......";

        assertEquals(expectedString, screen.toString());
    }
}
