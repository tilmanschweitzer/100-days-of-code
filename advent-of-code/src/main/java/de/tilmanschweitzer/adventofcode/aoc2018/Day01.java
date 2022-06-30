package de.tilmanschweitzer.adventofcode.aoc2018;

import de.tilmanschweitzer.adventofcode.app.AdventOfCodeDay;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends AdventOfCodeDay<Integer> {

    public static int firstFirstDuplicateFrequency(List<Integer> inputNumbers) {
        int currentFrequency = 0;
        final Set<Integer> previousFrequency = new HashSet<>();
        int inputIndex = 0;

        while (!previousFrequency.contains(currentFrequency)) {
            previousFrequency.add(currentFrequency);
            final Integer inputNumber = inputNumbers.get(inputIndex % inputNumbers.size());
            inputIndex++;
            currentFrequency += inputNumber;
        }
        return currentFrequency;
    }

    @Override
    public long getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return inputNumbers.stream().reduce(Integer::sum).orElse(0);
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return firstFirstDuplicateFrequency(inputNumbers);
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2018/day01-input.txt");
    }

}
