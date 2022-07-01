package de.tilmanschweitzer.adventofcode.day;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toUnmodifiableList;

public abstract class SingleLineAdventOfCodeDay<T> extends AdventOfCodeDay<T> {

    protected abstract T parseLine(String line);

    protected T getParsedInput() {
        return parseInputFromStream(getInputAsStream());
    }

    private T parseInputFromStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().findFirst().map(this::parseLine).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
