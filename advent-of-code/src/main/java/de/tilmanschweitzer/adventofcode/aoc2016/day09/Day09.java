package de.tilmanschweitzer.adventofcode.aoc2016.day09;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        return decompressV2Length(input);
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
        return genericDecompressX(input, Function.identity(), (a, b) -> a + b, Function.identity(), Day09::decompress);
    }

    public static String decompressV2(String input) {
        return genericDecompressX(input, Function.identity(), (a, b) -> a + b, Day09::decompressV2, Day09::decompressV2);
    }

    private static <T> T genericDecompressX(String input, Function<String, T> map, BiFunction<T, T, T> reduce, Function<String, T> decompressRepeatedString, Function<String, T> decompressRemainingInput) {
        if (input.indexOf('(') < 0) {
            return map.apply(input);
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

        final T firstReduction = reduce.apply(map.apply(uncompressed), decompressRepeatedString.apply(repeatedString));
        final T secondReduction = reduce.apply(firstReduction, decompressRemainingInput.apply(remainingInput));

        return secondReduction;
    }


    private static String genericDecompress(String input, Function<String, String> decompressRepeatedString, Function<String, String> decompressRemainingInput) {
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

        return uncompressed + decompressRepeatedString.apply(repeatedString) + decompressRemainingInput.apply(remainingInput);
    }

    public static long decompressLength(String input) {
        return genericDecompressX(input, s -> (long) s.length(), Long::sum, s -> (long) s.length(), Day09::decompressLength);
    }

    public static long decompressV2Length(String input) {
        return genericDecompressX(input, s -> (long) s.length(), Long::sum, Day09::decompressV2Length, Day09::decompressV2Length);
    }


    private static long genericDecompressLength(String input, Function<String, Long> decompressRepeatedString, Function<String, Long> decompressRemainingInput) {
        if (input.indexOf('(') < 0) {
            return input.length();
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

        return uncompressed.length() + decompressRepeatedString.apply(repeatedString) + decompressRemainingInput.apply(remainingInput);
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
