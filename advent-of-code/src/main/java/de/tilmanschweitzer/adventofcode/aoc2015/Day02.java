package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day02.*;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<Dimensions, Integer> {

    public Day02() {
        super(2015, 2);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Dimensions> inputDimensions) {
        return inputDimensions.stream().map(Dimensions::getSurfaceWithSlack).reduce(Integer::sum).orElse(0);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Dimensions> inputDimensions) {
        return inputDimensions.stream().map(Dimensions::getTotalRibbon).reduce(Integer::sum).orElse(0);
    }

    @Override
    public Dimensions parseLine(String line) {
        return Dimensions.fromInput(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day02-input.txt");
    }

    @EqualsAndHashCode
    public static class Dimensions {
        private final int length;
        private final int width;
        private final int height;

        public Dimensions(int length, int width, int height) {
            this.length = length;
            this.width = width;
            this.height = height;
        }

        public static Dimensions fromInput(String input) {
            final String[] splittedInput = input.split("x");
            final int length = Integer.parseInt(splittedInput[0]);
            final int width = Integer.parseInt(splittedInput[1]);
            final int height = Integer.parseInt(splittedInput[2]);
            return new Dimensions(length, width, height);
        }

        public int getTop() {
            return length * width;
        }

        public int getFront() {
            return width * height;
        }

        public int getSide() {
            return height * length;
        }

        public int getSurface() {
            return 2 * getTop() + 2 * getFront() + 2 * getSide();
        }

        public int getSurfaceWithSlack() {
            final Integer slack = Stream.of(getFront(), getTop(), getSide()).min(Integer::compareTo).orElse(0);
            return getSurface() + slack;
        }

        public int getWrapRibbon() {
            final List<Integer> smallestSides = Stream.of(length, width, height).sorted().limit(2).collect(Collectors.toUnmodifiableList());
            return smallestSides.get(0) * 2 + smallestSides.get(1) * 2;
        }

        public int getBowRibbon() {
            return length * width * height;
        }

        public int getTotalRibbon() {
            return getWrapRibbon() + getBowRibbon();
        }
    }
}
