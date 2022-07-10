package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day08 extends MultiLineAdventOfCodeDay<String> {

    @Override
    public long getResultOfFirstPuzzle(final List<String> input) {
        final Integer sumOfInputChars = input.stream().map(Day08::numberOfInputChars).reduce(Integer::sum).orElse(0);
        final Integer sumOfParsedChars = input.stream().map(Day08::numberOfParsedChars).reduce(Integer::sum).orElse(0);
        return sumOfInputChars - sumOfParsedChars;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<String> inputCircuitSteps) {
        return 0;
    }

    public static int numberOfInputChars(String s) {
        return s.length();
    }

    public static int numberOfParsedChars(final String s) {
        return parseString(s).length();
    }

    public static String parseString(String s) {
        final Pattern unicodeHexChar = Pattern.compile("\\\\x([\\da-fA-F]{2})");
        final Matcher unicodeHexCharMatcher = unicodeHexChar.matcher(s);
        while (unicodeHexCharMatcher.find()) {
            final String match = unicodeHexCharMatcher.group(0);
            final String hexValue = unicodeHexCharMatcher.group(1);
            final String replacement = new String(new byte[]{(byte) Integer.parseInt(hexValue, 16)}, StandardCharsets.UTF_8);
            s = s.replace(match, replacement);
        }
        final String unescapedString = s.replaceAll("\\\\\"", "\"");
        return unescapedString.substring(1, unescapedString.length() - 1);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day08-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }
}
