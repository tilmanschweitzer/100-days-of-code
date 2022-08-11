package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.ModifyScreenCommand;

public interface ModifyScreenCommandParser<T extends ModifyScreenCommand> {

    boolean matches(String input);

    T parse(String input);
}
