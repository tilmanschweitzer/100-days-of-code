package de.tilmanschweitzer.adventofcode.aoc2019;

import com.google.common.collect.Sets;
import de.tilmanschweitzer.adventofcode.app.AdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day03 extends AdventOfCodeDay<Integer> {

    public static Set<Point> findIntersectionOfPaths(List<Vector> firstPath, List<Vector> secondPath) {
        final Set<Point> firstPoints = Point.fromPath(firstPath).stream().collect(Collectors.toUnmodifiableSet());
        final Set<Point> secondPoints = Point.fromPath(secondPath).stream().collect(Collectors.toUnmodifiableSet());

        return Sets.intersection(firstPoints, secondPoints);
    }

    @Override
    public long getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return 0;
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("year/filename.txt");
    }

    public static class Point extends Coordinate implements Comparable<Point> {

        public Point(int x, int y) {
            super(x, y);
        }

        public static List<Point> fromInputList(String inputList) {
            return Arrays.stream(inputList.split(";")).map(Point::fromInput).collect(Collectors.toUnmodifiableList());
        }

        public static Point fromInput(String input) {
            final String[] split = input.split(",");
            final int x = Integer.parseInt(split[0]);
            final int y = Integer.parseInt(split[1]);
            return new Point(x, y);
        }

        public static List<Point> fromPath(final List<Vector> path) {
            int currentX = 0;
            int currentY = 0;
            final List<Point> points = new ArrayList<>();
            for (final Vector vector : path) {
                int steps = Math.abs(vector.getManhattanDistance());
                for (int i = 1; i <= steps; i++) {
                    currentX += vector.getX() / steps;
                    currentY += vector.getY() / steps;
                    points.add(new Point(currentX, currentY));
                }
            }

            return points;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y +")";
        }

        @Override
        public int compareTo(Point o) {
            return getManhattanDistance() - o.getManhattanDistance();
        }
    }
    public static class Vector extends Coordinate {

        public Vector(int x, int y) {
            super(x, y);
        }

        public static List<Vector> fromPathList(String pathList) {
            return Arrays.stream(pathList.split(",")).map(Vector::fromPath).collect(Collectors.toUnmodifiableList());
        }
        public static Vector fromPath(String path) {
            final char direction = path.charAt(0);
            final int number = Integer.parseInt(path.substring(1));
            if (direction == 'R') {
                return new Vector(number, 0);
            }
            if (direction == 'L') {
                return new Vector(-number, 0);
            }
            if (direction == 'U') {
                return new Vector(0, number);
            }
            if (direction == 'D') {
                return new Vector(0, -number);
            }
            throw new UnknownVectorDirection(direction);
        }

        private static class UnknownVectorDirection extends RuntimeException {
            public UnknownVectorDirection(char direction) {
                super("Unknown vector direction: " + direction);
            }
        }
    }
    public static abstract class Coordinate {
        final int x;
        final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getManhattanDistance() {
            return Math.abs(x) + Math.abs(y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate coordinate = (Coordinate) o;
            return x == coordinate.x && y == coordinate.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

    }
}
