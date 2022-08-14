package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends SingleLineAdventOfCodeDay<List<Integer>, Integer> {

    public Day01() {
        super(2017, 1);
    }

    public Integer getResultOfFirstPuzzle(final List<Integer> numbers) {
        return sumDuplicateNumbers(numbers);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> numbers) {
        return 0;
    }

    @Override
    public List<Integer> parseLine(String line) {
        return parseNumbers(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day01-input.txt");
    }


    public static List<Integer> parseNumbers(String s) {
        return Arrays.stream(s.split("")).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
    }

    public static int sumDuplicateNumbers(List<Integer> numbers) {
        return IntStream.range(0, numbers.size()).boxed().map(index -> {
            int nextIndex = (index + 1) % numbers.size();
            if (numbers.get(index) != numbers.get(nextIndex)) {
                return 0;
            }
            return numbers.get(index);
        }).reduce(Integer::sum).orElse(0);
    }
}
