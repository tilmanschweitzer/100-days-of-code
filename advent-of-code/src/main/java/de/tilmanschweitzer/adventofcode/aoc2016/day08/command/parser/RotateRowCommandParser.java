package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.RotateRowCommand;
import de.tilmanschweitzer.adventofcode.common.Pair;

import java.util.regex.Pattern;

public class RotateRowCommandParser extends AbstractModifyScreenCommandParser<RotateRowCommand> {

    private static Pattern pattern = Pattern.compile("rotate row y=(\\d+) by (\\d+)");

    public RotateRowCommandParser() {
        super(pattern);
    }

    @Override
    public RotateRowCommand parse(String input) {
        final Pair<Integer> parsedInts = parseIntPair(input);
        return new RotateRowCommand(parsedInts.getLeftValue(), parsedInts.getRightValue());
    }
}
