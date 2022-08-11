package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.ModifyScreenCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChainedModifyScreenCommandParser implements ModifyScreenCommandParser<ModifyScreenCommand> {

    private final List<ModifyScreenCommandParser<?>> parsers;

    private ChainedModifyScreenCommandParser(List<ModifyScreenCommandParser<?>> parsers) {
        this.parsers = parsers;
    }

    public static ChainedModifyScreenCommandParser of(ModifyScreenCommandParser<?>... parsers) {
        return new ChainedModifyScreenCommandParser(Arrays.stream(parsers).collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public boolean matches(String input) {
        return parsers.stream().anyMatch(parser -> parser.matches(input));
    }

    @Override
    public ModifyScreenCommand parse(String input) {
        final Optional<ModifyScreenCommandParser<?>> matchingParser = parsers.stream().filter(parser -> parser.matches(input)).findFirst();
        if (matchingParser.isEmpty()) {
            throw new RuntimeException("Found no parser for input: '" + input + "'");
        }
        return matchingParser.get().parse(input);
    }
}
