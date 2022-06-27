package de.tilmanschweitzer.adventofcode.app;

import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class EmptyDay extends AdventOfCodeDay<Integer> {

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
        return getSystemResourceAsStream("year/filename.txt");
    }

}
