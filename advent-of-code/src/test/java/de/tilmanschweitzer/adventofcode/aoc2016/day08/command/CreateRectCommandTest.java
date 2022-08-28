package de.tilmanschweitzer.adventofcode.aoc2016.day08.command;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.CreateRectCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateRectCommandTest {

    @Test
    public void parse() {
        assertEquals(new CreateRectCommand(7, 13), new CreateRectCommandParser().parse("rect 7x13"));
        assertEquals(new CreateRectCommand(17, 5), new CreateRectCommandParser().parse("rect 17x5"));
    }

    @Test
    public void apply() {
        final Screen screen = Screen.of(7, 3);
        final String expectedString = "###....\n" +
                "###....\n" +
                ".......";

        new CreateRectCommand(3, 2).apply(screen);

        assertEquals(expectedString, screen.toString());
    }

}
