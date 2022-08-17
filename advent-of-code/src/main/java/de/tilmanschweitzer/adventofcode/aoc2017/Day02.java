package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.common.combination.OrderedCombinations;
import de.tilmanschweitzer.adventofcode.common.combination.UnorderedCombinations;
import de.tilmanschweitzer.adventofcode.common.combination.validator.CollectionSizeValidator;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<List<Integer>, Integer> {

    public Day02() {
        super(2017, 2);
    }

    public Integer getResultOfFirstPuzzle(final List<List<Integer>> numbers) {
        return spreadsheetChecksum(numbers);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<List<Integer>> numbers) {
        return spreadsheetChecksumV2(numbers);
    }

    @Override
    public List<Integer> parseLine(String line) {
        return parseNumbers(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day02-input.txt");
    }


    public static List<Integer> parseNumbers(String s) {
        return Arrays.stream(s.trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
    }

    public static int rowChecksum(List<Integer> numbers) {
        final int min = numbers.stream().min(Integer::compareTo).orElse(0);
        final int max = numbers.stream().max(Integer::compareTo).orElse(0);
        return max - min;
    }

    public static int spreadsheetChecksum(List<List<Integer>> input) {
        return input.stream().map(Day02::rowChecksum).reduce(Integer::sum).orElse(0);
    }

    public static int spreadsheetChecksumV2(List<List<Integer>> input) {
        return input.stream().map(Day02::findEvenlyDivisiblePair).map(Day02::divide).reduce(Integer::sum).orElse(0);
    }

    public static int divide(Pair<Integer> pair) {
        return pair.getLeftValue() / pair.getRightValue();
    }

    public static boolean isEvenlyDivisible(Pair<Integer> pair) {
        return isEvenlyDivisible(pair.getLeftValue(), pair.getRightValue());
    }

    public static boolean isEvenlyDivisible(int n, int d) {
        return n % d == 0;
    }

    public static Pair<Integer> findEvenlyDivisiblePair(List<Integer> numbers) {
        final Set<Pair<Integer>> allCombinations = OrderedCombinations.allCombinations(numbers, new CollectionSizeValidator<>(2))
                .map(pair -> Pair.of(pair.stream()))
                .collect(Collectors.toSet());

        final List<Pair<Integer>> result = allCombinations.stream().filter(Day02::isEvenlyDivisible).collect(Collectors.toUnmodifiableList());

        if (result.isEmpty()) {
            throw new RuntimeException("No result for : " + numbers);
        }
        if (result.size() != 1) {
            throw new RuntimeException("Unexpected size " + result.size() + " for : " + numbers);
        }
        return result.get(0);
    }


}
