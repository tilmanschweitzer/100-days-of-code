package de.tilmanschweitzer.adventofcode.aoc2016.day09;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day09 extends SingleLineAdventOfCodeDay<String, Integer> {

    public Day09() {
        super(2016, 9);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final String input) {
        return decompressLength(input);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final String input) {
        return 0;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day09-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    public static String decompress(String input) {
        if (input.indexOf('(') < 0) {
            return input;
        }

        final int next = input.indexOf('(');

        final String markerString = extractNextMarker(input);
        final Pair<Integer> marker = parseMarker(markerString);
        final int repeatRange = marker.getLeftValue();
        final int repeatTimes = marker.getRightValue();

        final String uncompressed = input.substring(0, next);


        final int repeatedStringStartIndex = next + markerString.length();
        final String stringToBeRepeated = input.substring(repeatedStringStartIndex, repeatedStringStartIndex + repeatRange);
        final String repeatedString = repeat(repeatTimes, stringToBeRepeated);

        final String remainingInput = input.substring(repeatedStringStartIndex + repeatRange);

        return uncompressed + repeatedString + decompress(remainingInput);
    }

    public static int decompressLength(String input) {
        if (input.indexOf('(') < 0) {
            return input.length();
        }

        final int next = input.indexOf('(');

        final String markerString = extractNextMarker(input);
        final Pair<Integer> marker = parseMarker(markerString);
        final int repeatRange = marker.getLeftValue();
        final int repeatTimes = marker.getRightValue();

        final int repeatedStringStartIndex = next + markerString.length();

        final String remainingInput = input.substring(repeatedStringStartIndex + repeatRange);

        final int repeatedLength = repeatRange * repeatTimes;

        return next + repeatedLength + decompressLength(remainingInput);
    }

    public static String repeat(int repeat, String s) {
        return IntStream.range(0, repeat).boxed().map(i -> s).collect(Collectors.joining());
    }

    public static String extractNextMarker(String s) {
        final int startIndex = s.indexOf('(');
        final int endIndex = 1 + s.indexOf(')');

        if (startIndex < 0 || endIndex <= 0 || endIndex < startIndex) {
            throw new RuntimeException("Marker does not match format");
        }
        return s.substring(startIndex, endIndex);
    }

    public static Pair<Integer> parseMarker(String s) {
        final String[] values = s.substring(1, s.length() - 1).split("x");
        return Pair.of(Arrays.stream(values).map(Integer::parseInt));
    }

}
