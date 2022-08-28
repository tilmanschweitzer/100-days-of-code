package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.Hashes.md5;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day05 extends SingleLineAdventOfCodeDay<String, String> {

    public Day05() {
        super(2016, 5);
    }

    @Override
    public String getResultOfFirstPuzzle(final String input) {
        return findPasswordForDoorId(input, 5, 8);
    }

    @Override
    public String getResultOfSecondPuzzle(final String input) {
        return findMoreComplexPasswordForDoorId(input, 5, 8);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day05-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    public static String findPasswordForDoorId(String doorId, int numberOfZeros, int passwordLength) {
        return hashesWithLeadingZero(doorId, numberOfZeros)
                .limit(passwordLength)
                .map(s -> Objects.toString(s.charAt(numberOfZeros)))
                .collect(Collectors.joining());
    }

    public static String findMoreComplexPasswordForDoorId(String doorId, int numberOfZeros, int passwordLength) {
        final Set<Integer> alreadySetPositions = new HashSet<>();

        return hashesWithLeadingZero(doorId, numberOfZeros)
                .parallel()
                .map(hash -> Pair.of(hash.charAt(numberOfZeros), hash.charAt(numberOfZeros + 1)))
                .filter(pair -> {
                    final String positionAsString = Objects.toString(pair.getLeftValue());
                    if (!positionAsString.matches("\\d")) {
                        return false;
                    }
                    int position = Integer.parseInt(positionAsString);

                    return position < passwordLength && alreadySetPositions.add(position);
                }).limit(passwordLength)
                .sorted(Comparator.comparingInt(pair -> Integer.parseInt(Objects.toString(pair.getLeftValue()))))
                .map(Pair::getRightValue)
                .map(Objects::toString)
                .collect(Collectors.joining());
    }

    private static Stream<String> hashesWithLeadingZero(String doorId, int numberOfZeros) {
        final String leadingZeros = IntStream.range(0, numberOfZeros).boxed().map(i -> "0").collect(Collectors.joining());
        return Stream.iterate(0L, i -> i + 1L)
                .map(i -> md5(doorId + i))
                .filter(hash -> hash.startsWith(leadingZeros));
    }
}
