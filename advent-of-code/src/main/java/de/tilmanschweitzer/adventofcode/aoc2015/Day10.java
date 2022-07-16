package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day10 extends SingleLineAdventOfCodeDay<String, Integer> {

    public static String lookAndSay(String input) {
        final StringBuilder output = new StringBuilder();
        final List<Character> characters = Arrays.stream(input.split("")).map(s -> s.charAt(0)).collect(Collectors.toUnmodifiableList());
        char lastChar = '\0';
        int charCount = 1;
        for (char c: characters) {
            if (lastChar == '\0') {
                lastChar = c;
            } else if (lastChar != c) {
                output.append(charCount).append(lastChar);
                lastChar = c;
                charCount = 1;
            } else {
                charCount++;
            }
        }
        output.append(charCount).append(lastChar);
        return output.toString();
    }

    public static String lookAndSay(String input, int rounds) {
        String output = input;
        while (rounds > 0) {
            output = lookAndSay(output);
            rounds--;
        }
        return output;
    }

    @Override
    public Integer getResultOfFirstPuzzle(final String input) {
        return lookAndSay(input, 40).length();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final String input) {
        return lookAndSay(input, 60).length();
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day10-input.txt");
    }

}
