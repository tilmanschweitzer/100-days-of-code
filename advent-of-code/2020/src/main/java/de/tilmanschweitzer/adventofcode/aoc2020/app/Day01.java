package de.tilmanschweitzer.adventofcode.aoc2020.app;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toList;

public class Day01 extends AdventOfCodeDay<Integer> {

    @Override
    public long getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        final Optional<Pair<Integer>> matchingNumberPair = findMatchingNumberPair(inputNumbers, 2020);

        if (matchingNumberPair.isEmpty()) {
            throw new RuntimeException("No matching pair found");
        }
        final Pair<Integer> pair = matchingNumberPair.get();
        return pair.leftValue * pair.rightValue;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        final Optional<Triplet<Integer>> matchingNumberTriplet = findMatchingNumberTriplet(inputNumbers, 2020);

        if (matchingNumberTriplet.isEmpty()) {
            throw new RuntimeException("No matching triplet found");
        }
        final Triplet<Integer> triplet = matchingNumberTriplet.get();
        return triplet.firstValue * triplet.secondValue * triplet.thirdValue;
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("day01-input.txt");
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

    public static class Pair<T> {
        final T leftValue;
        final T rightValue;

        public Pair(T leftValue, T rightValue) {
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }


        @Override
        public String toString() {
            return "Pair(" + leftValue + ", " + rightValue + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?> pair = (Pair<?>) o;
            return Objects.equals(leftValue, pair.leftValue) && Objects.equals(rightValue, pair.rightValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(leftValue, rightValue);
        }
    }

    public static class Triplet<T> {
        final T firstValue;
        final T secondValue;

        final T thirdValue;

        public Triplet(T firstValue, T secondValue, T thirdValue) {
            this.firstValue = firstValue;
            this.secondValue = secondValue;
            this.thirdValue = thirdValue;
        }


        @Override
        public String toString() {
            return "Triplet(" + firstValue + ", " + secondValue + ", " + thirdValue + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triplet<?> pair = (Triplet<?>) o;
            return Objects.equals(firstValue, pair.firstValue) && Objects.equals(secondValue, pair.secondValue) && Objects.equals(thirdValue, pair.thirdValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstValue, secondValue, thirdValue);
        }
    }
}
