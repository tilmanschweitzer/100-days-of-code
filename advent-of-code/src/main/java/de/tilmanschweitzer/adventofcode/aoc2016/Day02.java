package de.tilmanschweitzer.adventofcode.aoc2016;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.common.BasicCoordinate;
import de.tilmanschweitzer.adventofcode.common.Converters;
import de.tilmanschweitzer.adventofcode.common.Coordinates;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<String, String> {

    public static final String SIMPLE_KEYPAD =  "123\n" +
                                                "456\n" +
                                                "789";

    public static final String BATHROOM_KEYPAD =    "  1  \n" +
                                                    " 234 \n" +
                                                    "56789\n" +
                                                    " ABC \n" +
                                                    "  D  ";
    public Day02() {
        super(2016, 2);
    }

    public String getResultOfFirstPuzzle(final List<String> instructions) {
        return keyCodeForInstructions(SIMPLE_KEYPAD, '5', instructions.stream());
    }

    @Override
    public String getResultOfSecondPuzzle(final List<String> instructions) {
        return keyCodeForInstructions(BATHROOM_KEYPAD, '5', instructions.stream());
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    public static String keyCodeForInstructions(final String keyPad, final char initialKey, final String... instructions) {
        return keyCodeForInstructions(keyPad, initialKey, Arrays.stream(instructions));
    }

    public static String keyCodeForInstructions(final String keyPad, final char initialKey, final Stream<String> instructions) {
        return keyCodeForInstructions(keyPad, initialKey, instructions.map(Converters::stringToCharList).collect(Collectors.toUnmodifiableList()));
    }

    public static String keyCodeForInstructions(final String keyPadInput, final char initialKey, final List<List<Character>> instructions) {
        final KeyPad keyPad = KeyPad.of(keyPadInput, initialKey);

        StringBuilder keyCodeBuilder = new StringBuilder();

        for (List<Character> instruction : instructions) {
            for (Character move : instruction) {

                if (move == 'U') {
                    keyPad.moveUp();
                } else if (move == 'D') {
                    keyPad.moveDown();
                } else if (move == 'L') {
                    keyPad.moveLeft();
                } else if (move == 'R') {
                    keyPad.moveRight();
                } else {
                    throw new RuntimeException("Unknown move: " + move);
                }
            }
            keyCodeBuilder.append(keyPad.getCurrentKey());
        }

        return keyCodeBuilder.toString();
    }

    private static boolean allLinesHaveSameLength(List<String> keyPadLines) {
        return keyPadLines.stream()
                .map(String::length)
                .filter(length -> length != keyPadLines.get(0).length())
                .findAny().isEmpty();
    }


    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day02-input.txt");
    }

    public static class KeyPad {

        private final Map<BasicCoordinate, Character> keyPadCoordinates;
        private BasicCoordinate currentKey;

        private KeyPad(Map<BasicCoordinate, Character> keyPadCoordinates, BasicCoordinate currentKey) {
            this.keyPadCoordinates = keyPadCoordinates;
            this.currentKey = currentKey;
        }

        public static KeyPad of(String keyPadInput, char initialValue) {
            final List<String> keyPadLines = Arrays.stream(keyPadInput.split("\n")).collect(Collectors.toUnmodifiableList());

            Preconditions.checkArgument(allLinesHaveSameLength(keyPadLines));
            final int width = keyPadLines.get(0).length();
            final int height = keyPadLines.size();

            final Map<BasicCoordinate, Character> keyPadCoordinates = Coordinates.coordinateStreamWithinGrid(width, height)
                    .collect(Collectors.toMap(BasicCoordinate::of, coordinate ->  keyPadLines.get(coordinate.getY()).charAt(coordinate.getX())));

            final Optional<BasicCoordinate> initialKeyOptional = keyPadCoordinates.entrySet().stream().filter(entry -> entry.getValue() == initialValue).map(Map.Entry::getKey).findFirst();

            if (initialKeyOptional.isEmpty()) {
                throw new RuntimeException("Initial value must be part of the given keyPadInput");
            }

            return new KeyPad(keyPadCoordinates, initialKeyOptional.get());
        }

        public char getCurrentKey() {
            return keyPadCoordinates.get(currentKey);
        }

        public void moveUp() {
            moveTo(currentKey.up());
        }

        public void moveDown() {
            moveTo(currentKey.down());
        }

        public void moveLeft() {
            moveTo(currentKey.left());
        }

        public void moveRight() {
            moveTo(currentKey.right());
        }

        private void moveTo(BasicCoordinate coordinate) {
            if (keyPadCoordinates.containsKey(coordinate) && keyPadCoordinates.get(coordinate) != ' ') {
                currentKey = coordinate;
            }
        }
    }

}
