package de.tilmanschweitzer.adventofcode.aoc2020.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toUnmodifiableList;

public abstract class AdventOfCodeDay<T> {

    private List<T> parsedInput;

    public void runFirstPuzzle() {
        runFirstPuzzle(getParsedInput());
    }

    public void runSecondPuzzle() {
        runSecondPuzzle(getParsedInput());
    }

    protected abstract void runFirstPuzzle(List<T> input);

    protected abstract void runSecondPuzzle(List<T> input);

    protected abstract T parseLine(String line);

    protected abstract InputStream getInputAsStream();

    private List<T> getParsedInput() {
        if (parsedInput == null) {
            parsedInput = parseInputFromStream(getInputAsStream());
        }
        return parsedInput;
    }

    private List<T> parseInputFromStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(this::parseLine).collect(toUnmodifiableList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
