package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.CreateRectCommand;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.RotateColumnCommand;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.RotateRowCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainedModifyScreenCommandParserTest {

    @Test
    public void parse() {
        final ChainedModifyScreenCommandParser universalParser = ChainedModifyScreenCommandParser.of(new CreateRectCommandParser(), new RotateRowCommandParser(), new RotateColumnCommandParser());

        assertEquals(new RotateColumnCommand(13, 7), universalParser.parse("rotate column x=13 by 7"));
        assertEquals(new RotateColumnCommand(3, 17), universalParser.parse("rotate column x=3 by 17"));
        assertEquals(new CreateRectCommand(7, 13), universalParser.parse("rect 7x13"));
        assertEquals(new CreateRectCommand(17, 5), universalParser.parse("rect 17x5"));
        assertEquals(new RotateRowCommand(13, 7), universalParser.parse("rotate row y=13 by 7"));
        assertEquals(new RotateRowCommand(3, 17), universalParser.parse("rotate row y=3 by 17"));
    }

}
