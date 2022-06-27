package de.tilmanschweitzer.adventofcode.aoc2020;

import de.tilmanschweitzer.adventofcode.app.AdventOfCodeDay;

import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day03 extends AdventOfCodeDay<String> {

    private static final char TREE = '#';

    public static long getEncounteredTreesForSlope(final List<String> lines, int rightStep, int downStep) {
        int encounteredTrees = 0;
        int right = rightStep;
        int down = downStep;

//        System.out.println(lines.stream().collect(Collectors.joining("\n")));

        while (down < lines.size()) {
            final char c = lines.get(down).charAt(right);

            if (c == TREE) {
                encounteredTrees++;
            }

            right = (right + rightStep) % lines.get(down).length();
            down += downStep;
        }

        return encounteredTrees;
    }

    @Override
    protected long getResultOfFirstPuzzle(List<String> lines) {
        return getEncounteredTreesForSlope(lines, 3, 1);
    }

    @Override
    protected long getResultOfSecondPuzzle(List<String> lines) {
        return getEncounteredTreesForSlope(lines, 1, 1) *
                getEncounteredTreesForSlope(lines, 3, 1) *
                getEncounteredTreesForSlope(lines, 5, 1) *
                getEncounteredTreesForSlope(lines, 7, 1) *
                getEncounteredTreesForSlope(lines, 1, 2);
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("day03-input.txt");
    }




}
