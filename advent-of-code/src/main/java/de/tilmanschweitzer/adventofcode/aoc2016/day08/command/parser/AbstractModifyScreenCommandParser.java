package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.ModifyScreenCommand;
import de.tilmanschweitzer.adventofcode.common.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractModifyScreenCommandParser<T extends ModifyScreenCommand> implements ModifyScreenCommandParser<T> {

    private final Pattern pattern;

    protected AbstractModifyScreenCommandParser(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(String input) {
        return pattern.matcher(input).matches();
    }


    protected Pair<Integer> parseIntPair(String input) {
        final Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new CouldNotParseInputException(input);
        }
        int a = Integer.parseInt(matcher.group(1));
        int b = Integer.parseInt(matcher.group(2));
        return Pair.of(a, b);
    }
}
