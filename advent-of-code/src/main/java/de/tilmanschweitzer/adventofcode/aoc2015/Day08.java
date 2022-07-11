package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day08 extends MultiLineAdventOfCodeDay<String> {

    @Override
    public long getResultOfFirstPuzzle(final List<String> input) {
        final Integer sumOfInputChars = input.stream().map(Day08::countInputChars).reduce(Integer::sum).orElse(0);
        final Integer sumOfParsedChars = input.stream().map(Day08::countParsedChars).reduce(Integer::sum).orElse(0);
        return sumOfInputChars - sumOfParsedChars;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<String> inputCircuitSteps) {
        return 0;
    }

    public static int countInputChars(String s) {
        return s.length();
    }

    public static int countParsedChars(final String input) {
        return parseString(input).length();
    }

    public static String parseString(String input) {
        String remainingInput = input;

        int remainingCharIndex = 0;
        int charIndex = 0;

        while (charIndex < input.length()) {
            if (charIndex == 0) {
                final char firstChar = remainingInput.charAt(remainingCharIndex);
                if (firstChar == '\"') {
                    remainingInput = remainingInput.substring( 1);
                } else {
                    throw new RuntimeException("Unexpected first char: " + firstChar);
                }
                charIndex++;
                continue;
            }
            if (remainingCharIndex == remainingInput.length() - 1) {
                final char lastChar = remainingInput.charAt(remainingCharIndex);
                if (lastChar == '\"') {
                    remainingInput = remainingInput.substring(0,  remainingInput.length() - 1);
                } else {
                    throw new RuntimeException("Unexpected last char: " + lastChar);
                }
                charIndex++;
                continue;
            }
            if (remainingCharIndex < remainingInput.length() - 2 && remainingInput.charAt(remainingCharIndex) == '\\') {
                final char nextChar = remainingInput.charAt(remainingCharIndex + 1);
                final String previousChars = remainingInput.substring(0, remainingCharIndex);
                if (nextChar == '\\') {
                    remainingInput = previousChars + remainingInput.substring(remainingCharIndex + 1);
                    charIndex++;
                } else if (nextChar == '\"') {
                    remainingInput = previousChars + remainingInput.substring(remainingCharIndex + 1);
                    charIndex++;
                } else if (nextChar == 'x') {
                    final String unicodeSequence = remainingInput.substring(remainingCharIndex, remainingCharIndex + 4);
                    System.out.println(unicodeSequence);
                    if (unicodeSequence.matches("\\\\x([\\da-fA-F]{2})")) {
                        remainingInput = previousChars + unicodeChar(unicodeSequence) + remainingInput.substring(remainingCharIndex + 4);
                    }
                    charIndex += 2;
                }
            }
            remainingCharIndex++;
            charIndex++;
        }


        return remainingInput;
    }

    private static String unicodeChar(String unicodeHex) {
        if (!unicodeHex.matches("\\\\x([\\da-fA-F]{2})")) {
            throw new RuntimeException("Unexpected unicode hex sequence: " + unicodeHex);
        }
        return new String(new byte[]{(byte) Integer.parseInt(unicodeHex.substring(2), 16)}, StandardCharsets.UTF_8);
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
