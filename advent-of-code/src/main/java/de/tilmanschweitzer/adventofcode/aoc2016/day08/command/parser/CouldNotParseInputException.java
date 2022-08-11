package de.tilmanschweitzer.adventofcode.aoc2016.day08.command.parser;

public class CouldNotParseInputException extends RuntimeException {

    public CouldNotParseInputException(String message) {
        super("Could not parse input: '" + message + "'");
    }
}
