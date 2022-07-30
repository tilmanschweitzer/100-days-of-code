package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Converters;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<String, String> {

    public Day02() {
        super(2016, 2);
    }

    public String getResultOfFirstPuzzle(final List<String> instructions) {
        return keyCodeForInstructions(instructions.stream());
    }

    @Override
    public String getResultOfSecondPuzzle(final List<String> instructions) {
        return "";
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    public static String keyCodeForInstructions(final String... instructions) {
        return keyCodeForInstructions(Arrays.stream(instructions));
    }

    public static String keyCodeForInstructions(final Stream<String> instructions) {
        return keyCodeForInstructions(instructions.map(Converters::stringToCharList).collect(Collectors.toUnmodifiableList()));
    }

    public static String keyCodeForInstructions(final List<List<Character>> instructions) {
        // "1 2 3 4 5 6 7 8 9"

        StringBuilder keyCodeBuilder = new StringBuilder();
        int currentNumber = 5;

        for (List<Character> instruction : instructions) {
            for (Character move : instruction) {
                if (move == 'U' && currentNumber - 3 >= 1) {
                    currentNumber -= 3;
                }
                if (move == 'D' && currentNumber + 3 <= 9) {
                    currentNumber += 3;
                }
                if (move == 'L' && ((currentNumber - 1) % 3) > 0) {
                    currentNumber -= 1;
                }
                if (move == 'R' && ((currentNumber - 1) % 3) < 2) {
                    currentNumber += 1;
                }
            }
            keyCodeBuilder.append(currentNumber);
        }

        return keyCodeBuilder.toString();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day02-input.txt");
    }

}
