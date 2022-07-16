package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class EmptyDay extends MultiLineAdventOfCodeDay<Integer, Integer> {

    @Override
    public Integer getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("year/filename.txt");
    }

}
