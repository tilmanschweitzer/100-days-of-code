package de.tilmanschweitzer.adventofcode.day;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toUnmodifiableList;

public abstract class MultiLineAdventOfCodeDay<T> extends AdventOfCodeDay<List<T>> {

    protected abstract T parseLine(String line);

    protected List<T> getParsedInput() {
        return parseInputFromStream(getInputAsStream());
    }

    private List<T> parseInputFromStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(this::parseLine).collect(toUnmodifiableList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
