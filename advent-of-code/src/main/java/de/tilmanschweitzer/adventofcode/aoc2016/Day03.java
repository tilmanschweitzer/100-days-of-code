package de.tilmanschweitzer.adventofcode.aoc2016;

import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.common.CollectionFunctions;
import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.common.Triplet;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day03 extends MultiLineAdventOfCodeDay<Day03.Triangle, Long> {

    public Day03() {
        super(2016, 3);
    }

    public Long getResultOfFirstPuzzle(final List<Triangle> triangels) {
        return triangels.stream().filter(Triangle::isValid).count();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<Triangle> triangels) {
        final List<Triangle> verticalTriangles = IntStream.range(0, triangels.size() / 3).boxed().map(indexDividedByThree -> {
            final int index = indexDividedByThree * 3;
            final List<Triangle> trianglesByRow = triangels.subList(index, index + 3);

            final Triangle firstVertical = Triangle.of(trianglesByRow.stream().map(Triangle::getFirstValue));
            final Triangle secondVertical = Triangle.of(trianglesByRow.stream().map(Triangle::getSecondValue));
            final Triangle thirdVertical = Triangle.of(trianglesByRow.stream().map(Triangle::getThirdValue));

            return Stream.of(firstVertical, secondVertical, thirdVertical);
        }).flatMap(Streams::concat).collect(Collectors.toUnmodifiableList());
        return verticalTriangles.stream().filter(Triangle::isValid).count();
    }

    @Override
    public Triangle parseLine(String line) {
        return Triangle.of(Arrays.stream(line.split("\\s+")).filter(Predicate.not(String::isBlank)).map(Integer::parseInt));
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day03-input.txt");
    }

    public static class Triangle extends Triplet<Integer> {

        public Triangle(Integer firstValue, Integer secondValue, Integer thirdValue) {
            super(firstValue, secondValue, thirdValue);

        }

        public boolean isValid() {
            return CollectionFunctions.sum(getCatheti().values()) > getHypotenuse();
        }

        public Pair<Integer> getCatheti() {
            return Pair.of(values().sorted().limit(2));
        }

        public Integer getHypotenuse() {
            return values().max(Integer::compareTo).get();
        }

        public static Triangle of(Stream<Integer> valueStream) {
            final List<Integer> values = valueStream.collect(Collectors.toUnmodifiableList());
            if (values.size() != 3) {
                throw new IllegalArgumentException("Triangle must be created from 3 values but got " + values.size());
            }
            return new Triangle(values.get(0), values.get(1), values.get(2));
        }
    }
}
