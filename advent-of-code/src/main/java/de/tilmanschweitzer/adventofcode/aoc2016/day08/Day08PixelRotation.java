package de.tilmanschweitzer.adventofcode.aoc2016.day08;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.ModifyScreenCommand;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.ChainedModifyScreenCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.CreateRectCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.RotateColumnCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser.RotateRowCommandParser;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day08PixelRotation extends MultiLineAdventOfCodeDay<ModifyScreenCommand, Long> {

    final ChainedModifyScreenCommandParser universalParser = ChainedModifyScreenCommandParser.of(
            new CreateRectCommandParser(),
            new RotateRowCommandParser(),
            new RotateColumnCommandParser()
    );

    public Day08PixelRotation() {
        super(2016, 8);
    }

    @Override
    public Long getResultOfFirstPuzzle(final List<ModifyScreenCommand> commands) {
        final Screen screen = Screen.of(50, 6);
        commands.forEach(screen::applyCommand);
        return screen.countLights();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<ModifyScreenCommand> input) {
        return 0L;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day08-input.txt");
    }

    @Override
    protected ModifyScreenCommand parseLine(String line) {
        return universalParser.parse(line);
    }

}
