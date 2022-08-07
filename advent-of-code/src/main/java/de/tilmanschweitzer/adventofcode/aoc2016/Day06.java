package de.tilmanschweitzer.adventofcode.aoc2016;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day06 extends MultiLineAdventOfCodeDay<String, String> {

    public Day06() {
        super(2016, 6);
    }
    @Override
    public String getResultOfFirstPuzzle(final List<String> input) {
        return reconstructMessage(input);
    }

    @Override
    public String getResultOfSecondPuzzle(final List<String> input) {
        return "";
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day06-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    public static String reconstructMessage(List<String> input) {
        int firstLineLength = input.get(0).length();
        checkLineLengthUniformity(firstLineLength, input);

        final List<List<Character>> allCharsByPosition = IntStream.range(0, firstLineLength).boxed()
                .map(position -> input.stream().map(line -> line.charAt(position)).collect(Collectors.toUnmodifiableList()))
                .collect(Collectors.toUnmodifiableList());


        final List<Map<Character, Long>> charsByPositionAndOccurrence = allCharsByPosition.stream()
                .map(Day06::countChars)
                .collect(Collectors.toUnmodifiableList());

        return charsByPositionAndOccurrence.stream().map(charsByOccurrence -> {
            final Optional<Map.Entry<Character, Long>> max = charsByOccurrence.entrySet().stream().max(Map.Entry.comparingByValue());
            if (max.isEmpty()) {
                throw new RuntimeException("No entry found");
            }
            return Objects.toString(max.get().getKey());
        }).collect(Collectors.joining());
    }

    private static void checkLineLengthUniformity(int expectedLineLength, List<String> input) {
        boolean allLinesMatchLength = input.stream().noneMatch(line -> line.length() != expectedLineLength);
        Preconditions.checkArgument(allLinesMatchLength);
    }

    public static Map<Character, Long> countChars(List<Character> characters) {
        return characters.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


}
