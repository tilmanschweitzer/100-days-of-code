package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.BasicCoordinate;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day03 extends SingleLineAdventOfCodeDay<Integer, Integer> {

    public Day03() {
        super(2017, 3);
    }


    public Integer getResultOfFirstPuzzle(final Integer number) {
        return getManhattanDistanceForAccessPort(number);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final Integer number) {
        return coordinatesForAddressSchemaV2().filter(valueCoordinate -> valueCoordinate.value > number).findFirst().map(ValueCoordinate::getValue).orElse(0);
    }

    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day03-input.txt");
    }


    /**
     * (-1,-1) (0,-1)  (1,-1)
     * (-1, 0) (0, 0)  (1, 0)
     * (-1, 1) (0, 1)  (1, 1)
     */
    public static BasicCoordinate nextCoordinate(BasicCoordinate coordinate) {
        final int x = coordinate.getX();
        final int y = coordinate.getY();
        final int level =  Math.max(Math.abs(x), Math.abs(y));

        if (level == y && x <= level) {
            // move to next level
            return coordinate.right();
        }
        if (level == x && y > -level) {
            return coordinate.up();
        }
        if (level == -y && x > -level) {
            return coordinate.left();
        }
        if (level == -x && y < level) {
            return coordinate.down();
        }

        return coordinate;
    }

    public static Stream<BasicCoordinate> coordinatesForAddressSchema() {
        final BasicCoordinate startPoint = BasicCoordinate.of(0, 0);
        return Stream.iterate(startPoint, Day03::nextCoordinate);
    }

    public static BasicCoordinate getCoordinateForAccessPort(int n) {
        if (n == 0) {
            throw new RuntimeException("Invalid adress : " + n);
        }
        return coordinatesForAddressSchema().skip(n - 1).findFirst().get();
    }

    public static Integer getValueForAccessPortV2(int n) {
        if (n == 0) {
            throw new RuntimeException("Invalid adress : " + n);
        }
        return coordinatesForAddressSchemaV2().skip(n - 1).findFirst().get().getValue();
    }

    public static int getManhattanDistanceForAccessPort(int n) {
        return getCoordinateForAccessPort(n).getManhattanDistance();
    }

    public static Stream<ValueCoordinate> coordinatesForAddressSchemaV2() {
        final ValueCoordinate startPoint = ValueCoordinate.of(0, 0, 1);
        final Map<BasicCoordinate, Integer> existingCoordinates = new HashMap<>();

        existingCoordinates.put(startPoint, startPoint.getValue());

        return Stream.iterate(startPoint, valueCoordinate -> {
            final BasicCoordinate nextCoordinate = nextCoordinate(valueCoordinate);
            final int value = nextCoordinate.getAllNeighborCoordinates().stream()
                    .map(existingCoordinates::get)
                    .filter(Objects::nonNull)
                    .reduce(Integer::sum)
                    .orElse(0);

            final ValueCoordinate nextValueCoordinate = ValueCoordinate.of(nextCoordinate, value);
            existingCoordinates.put(nextCoordinate, value);
            return nextValueCoordinate;
        });
    }

    public static class ValueCoordinate extends BasicCoordinate {
        final int value;

        public ValueCoordinate(int x, int y, int value) {
            super(x, y);
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ValueCoordinate of(int x, int y, int value) {
            return new ValueCoordinate(x, y, value);
        }

        public static ValueCoordinate of(BasicCoordinate coordinate, int value) {
            return new ValueCoordinate(coordinate.getX(), coordinate.getY(), value);
        }
    }
}
