package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        return 0;
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



}
