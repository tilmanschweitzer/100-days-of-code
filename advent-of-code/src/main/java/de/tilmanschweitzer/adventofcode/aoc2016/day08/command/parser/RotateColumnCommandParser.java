package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.RotateColumnCommand;
import de.tilmanschweitzer.adventofcode.common.Pair;

import java.util.regex.Pattern;

public class RotateColumnCommandParser extends AbstractModifyScreenCommandParser<RotateColumnCommand> {

    private static Pattern pattern = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

    public RotateColumnCommandParser() {
        super(pattern);
    }

    @Override
    public RotateColumnCommand parse(String input) {
        final Pair<Integer> parsedInts = parseIntPair(input);
        return new RotateColumnCommand(parsedInts.getLeftValue(), parsedInts.getRightValue());
    }
}
