package de.tilmanschweitzer.adventofcode.day;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public abstract class SingleLineAdventOfCodeDay<I, A> extends AdventOfCodeDay<I, A> {

    protected abstract I parseLine(String line);

    protected I getParsedInput() {
        return parseInputFromStream(getInputAsStream());
    }

    private I parseInputFromStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().findFirst().map(this::parseLine).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
