package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.CreateRectCommand;
import de.tilmanschweitzer.adventofcode.common.Pair;

import java.util.regex.Pattern;

public class CreateRectCommandParser extends AbstractModifyScreenCommandParser<CreateRectCommand> {

    private static Pattern pattern = Pattern.compile("rect (\\d+)x(\\d+)");

    public CreateRectCommandParser() {
        super(pattern);
    }

    @Override
    public CreateRectCommand parse(String input) {
        final Pair<Integer> parsedInts = parseIntPair(input);
        return new CreateRectCommand(parsedInts.getLeftValue(), parsedInts.getRightValue());
    }
}
