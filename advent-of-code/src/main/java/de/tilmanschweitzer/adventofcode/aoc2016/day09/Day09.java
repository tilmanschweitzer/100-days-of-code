package de.tilmanschweitzer.adventofcode.aoc2016.day09;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day09 extends SingleLineAdventOfCodeDay<String, Long> {

    public Day09() {
        super(2016, 9);
    }

    @Override
    public Long getResultOfFirstPuzzle(final String input) {
        return decompressLength(input);
    }

    @Override
    public Long getResultOfSecondPuzzle(final String input) {
        return 0L;
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
        final StringBuilder sb = new StringBuilder();

        int lastCut = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                sb.append(input, lastCut, i);

                final String unparsedInput = input.substring(i);
                final Optional<String> markerOptional = extractNextMarker(unparsedInput);

                if (markerOptional.isPresent()) {
                    final String marker = markerOptional.get();
                    final Pair<Integer> markerValues = parseMarker(marker);
                    i += marker.length();

                    final Integer width = markerValues.getLeftValue();
                    final Integer times = markerValues.getRightValue();

                    final String repeatedString = unparsedInput.substring(marker.length(), marker.length() + width);


                    sb.append(repeat(times, repeatedString));

                    i += width ;
                }

                lastCut = i;
                i--;
            }
        }

        sb.append(input.substring(lastCut));


        return sb.toString();
    }

    public static long decompressLength(String input) {
        final StringBuilder sb = new StringBuilder();

        long length = 0;

        int lastCut = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                length += i - lastCut;


                final String unparsedInput = input.substring(i);
                final Optional<String> markerOptional = extractNextMarker(unparsedInput);

                if (markerOptional.isPresent()) {
                    final String marker = markerOptional.get();
                    final Pair<Integer> markerValues = parseMarker(marker);
                    i += marker.length();

                    final Integer width = markerValues.getLeftValue();
                    final Integer times = markerValues.getRightValue();

                    length += width * times;

                    i += width;
                }

                lastCut = i;

            }
        }

        sb.append(input.substring(lastCut));

        length += input.length() - lastCut;

        return length;
    }

    public static String repeat(int repeat, String s) {
        return IntStream.range(0, repeat).boxed().map(i -> s).collect(Collectors.joining());
    }

    public static Optional<String> extractNextMarker(String s) {
        final int startIndex = s.indexOf('(');
        final int endIndex = 1 + s.indexOf(')');

        if (startIndex < 0 || endIndex <= 0 || endIndex < startIndex) {
            return Optional.empty();
        }
        return Optional.of(s.substring(startIndex, endIndex));
    }

    public static Pair<Integer> parseMarker(String s) {
        final String[] values = s.substring(1, s.length() - 1).split("x");
        return Pair.of(Arrays.stream(values).map(Integer::parseInt));
    }

}
