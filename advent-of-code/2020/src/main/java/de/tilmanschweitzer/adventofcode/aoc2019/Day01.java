package de.tilmanschweitzer.adventofcode.aoc2019;

import de.tilmanschweitzer.adventofcode.app.AdventOfCodeDay;

import java.io.InputStream;
import java.util.List;


import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends AdventOfCodeDay<Integer> {

    @Override
    public long getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2019/day01-input.txt");
    }

}
