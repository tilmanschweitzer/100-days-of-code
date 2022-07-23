package de.tilmanschweitzer.adventofcode.aoc2018;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends MultiLineAdventOfCodeDay<Integer, Integer> {

    public Day01() {
        super(2018, 1);
    }

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
    public Integer getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return sum(inputNumbers);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
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
