package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.BasicCoordinate;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
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
        return 0;
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

    public static int getManhattanDistanceForAccessPort(int n) {
        return getCoordinateForAccessPort(n).getManhattanDistance();
    }

}
