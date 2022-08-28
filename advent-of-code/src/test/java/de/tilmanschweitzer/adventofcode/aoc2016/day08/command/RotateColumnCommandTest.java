package de.tilmanschweitzer.adventofcode.aoc2016.day08.command;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.RotateColumnCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotateColumnCommandTest {

    @Test
    void parse() {
        assertEquals(new RotateColumnCommand(13, 7), new RotateColumnCommandParser().parse("rotate column x=13 by 7"));
        assertEquals(new RotateColumnCommand(3, 17), new RotateColumnCommandParser().parse("rotate column x=3 by 17"));
    }

    @Test
    void apply() {
        final Screen screen = Screen.of(7, 3);
        screen.turnOn(6, 1);

        final String expectedString = ".......\n" +
                ".......\n" +
                "......#";

        new RotateColumnCommand(6, 4).apply(screen);

        assertEquals(expectedString, screen.toString());
    }

}
