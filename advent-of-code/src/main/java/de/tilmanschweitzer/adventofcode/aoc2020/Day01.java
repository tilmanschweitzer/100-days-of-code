package de.tilmanschweitzer.adventofcode.aoc2020;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.common.Triplet;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toList;

public class Day01 extends MultiLineAdventOfCodeDay<Integer, Integer> {

    public Day01() {
        super(2020, 1);
    }
    @Override
    public Integer getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        final Optional<Pair<Integer>> matchingNumberPair = findMatchingNumberPair(inputNumbers, 2020);

        if (matchingNumberPair.isEmpty()) {
            throw new RuntimeException("No matching pair found");
        }
        final Pair<Integer> pair = matchingNumberPair.get();
        return pair.getLeftValue() * pair.getRightValue();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        final Optional<Triplet<Integer>> matchingNumberTriplet = findMatchingNumberTriplet(inputNumbers, 2020);

        if (matchingNumberTriplet.isEmpty()) {
            throw new RuntimeException("No matching triplet found");
        }
        final Triplet<Integer> triplet = matchingNumberTriplet.get();
        return triplet.getFirstValue() * triplet.getSecondValue() * triplet.getThirdValue();
    }

    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2020/day01-input.txt");
    }

    private static final String LINE_SEPARATOR = "\n";

    public static Optional<Pair<Integer>> findMatchingNumberPair(final List<Integer> testInput, int sumOfPair) {
        if (testInput.size() < 2) {
            return Optional.empty();
        }

        int rightValueIndex = 1;

        for (final Integer leftValue : testInput.stream().limit(testInput.size() - 1).collect(toList())) {
            for (final Integer rightValue : testInput.stream().skip(rightValueIndex++).collect(toList())) {
                if (leftValue + rightValue == sumOfPair) {
                    return Optional.of(new Pair<>(leftValue, rightValue));
                }
            }
        }

        return Optional.empty();
    }

    public static Optional<Triplet<Integer>> findMatchingNumberTriplet(final List<Integer> testInput, int sumOfTriplet) {
        if (testInput.size() < 3) {
            return Optional.empty();
        }

        int firstValueIndex = 1;

        for (final Integer firstValue : testInput.stream().limit(testInput.size() - 2).collect(toList())) {
            int secondValueIndex = firstValueIndex + 1;
            for (final Integer secondValue : testInput.stream().skip(firstValueIndex++).limit(testInput.size() - 1).collect(toList())) {
                for (final Integer thirdValue : testInput.stream().skip(secondValueIndex++).collect(toList())) {
                    if (firstValue + secondValue + thirdValue == sumOfTriplet) {
                        return Optional.of(new Triplet<>(firstValue, secondValue, thirdValue));
                    }
                }
            }
        }

        return Optional.empty();
    }

}
