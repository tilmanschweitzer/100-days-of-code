package de.tilmanschweitzer.adventofcode.aoc2019;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.tilmanschweitzer.adventofcode.aoc2019.Day03.Point.CENTRAL_PORT;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.*;

public class Day03 extends MultiLineAdventOfCodeDay<List<Day03.Vector>> {

    public static Set<Point> findIntersectionOfPaths(List<Vector> firstPath, List<Vector> secondPath) {
        final List<Point> firstPoints = Point.fromPath(firstPath);
        final List<Point> secondPoints = Point.fromPath(secondPath);
        return findIntersectionOfPoints(firstPoints, secondPoints);
    }

    public static Set<Point> findIntersectionOfPoints(List<Point> firstPoints, List<Point> secondPoints) {
        final Set<Point> firstPointsSet = firstPoints.stream().collect(toUnmodifiableSet());
        final Set<Point> secondPointsSet = secondPoints.stream().collect(toUnmodifiableSet());
        return Sets.intersection(firstPointsSet, secondPointsSet).stream()
                .map(Point::intersectionFrom)
                .collect(toUnmodifiableSet());
    }

    public static Point findClosestIntersectionToCentralPort(List<Vector> firstPath, List<Vector> secondPath) {
        final Set<Point> result = findIntersectionOfPaths(firstPath, secondPath);
        final Optional<Point> firstPointOptional = result.stream().sorted().findFirst();
        if (firstPointOptional.isEmpty()) {
            throw new RuntimeException("Found no intersection");
        }
        return firstPointOptional.get();
    }

    private static final int GRID_OFFSET = 1;

    public static String getPrintableVersion(List<Vector> path) {
        final List<Point> points = Point.fromPath(path);
        return Grid.from(points).getPrintableVersion();

    }
    public static String getPrintableVersion(List<Vector> firstPath, List<Vector> secondPath) {
        final List<Point> firstPoints = Point.fromPath(firstPath);
        final List<Point> secondPoints = Point.fromPath(secondPath);
        final Set<Point> intersectionOfPoints = findIntersectionOfPoints(firstPoints, secondPoints);

        final List<Point> allPoints = new ArrayList<>();
        allPoints.addAll(firstPoints);
        allPoints.addAll(secondPoints);
        allPoints.addAll(intersectionOfPoints);

        return Grid.from(allPoints).getPrintableVersion();
    }

    @Override
    public long getResultOfFirstPuzzle(final List<List<Vector>> paths) {
        if (paths.size() != 2) {
            throw new RuntimeException("Unexpected number of paths");
        }
        final List<Vector> firstPath = paths.get(0);
        final List<Vector> secondPath = paths.get(1);

        return findClosestIntersectionToCentralPort(firstPath, secondPath).getManhattanDistance();
    }

    @Override
    public long getResultOfSecondPuzzle(final List<List<Vector>> inputNumbers) {
        return 0;
    }


    @Override
    public List<Vector> parseLine(String line) {
        return Vector.fromPathList(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2019/day03-input.txt");
    }

    public static class Point extends Coordinate implements Comparable<Point> {
        public static Point CENTRAL_PORT = new Point(0, 0, Type.CENTRAL_PORT);

        final Type type;

        public enum Type {
            CENTRAL_PORT,
            VERTICAL,
            HORIZONTAL,
            CORNER,
            INTERSECTION,
            UNKNOWN
        }

        public Point(int x, int y) {
            this(x, y, Type.UNKNOWN);
        }

        public Point(int x, int y, Type type) {
            super(x, y);
            this.type = type;
        }

        public static List<Point> fromInputList(String inputList) {
            return Arrays.stream(inputList.split(";")).map(Point::fromInput).collect(toUnmodifiableList());
        }

        public static Point fromInput(String input) {
            final String[] split = input.split(",");
            final int x = Integer.parseInt(split[0]);
            final int y = Integer.parseInt(split[1]);
            return new Point(x, y, Type.UNKNOWN);
        }

        public static Point intersectionFrom(Point point) {
            return new Point(point.x, point.y, Type.INTERSECTION);
        }

        public static List<Point> fromPath(final List<Vector> path) {
            int currentX = 0;
            int currentY = 0;
            final List<Point> points = new ArrayList<>();
            for (int vectorIndex = 0; vectorIndex < path.size(); vectorIndex++) {
                final Vector vector = path.get(vectorIndex);
                int numberOfSteps = Math.abs(vector.getManhattanDistance());
                for (int step = 1; step <= numberOfSteps; step++) {
                    currentX += vector.getX() / numberOfSteps;
                    currentY += vector.getY() / numberOfSteps;


                    final Type type;
                    if (step == numberOfSteps && vectorIndex < path.size() - 1) {
                        type = Type.CORNER;
                    } else if (vector.getX() == 0) {
                        type = Type.VERTICAL;
                    } else if (vector.getY() == 0) {
                        type = Type.HORIZONTAL;
                    } else {
                        type = Type.UNKNOWN;
                    }

                    points.add(new Point(currentX, currentY, type));
                }
            }

            return points;
        }


        public Type getType() {
            return type;
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
            return Arrays.stream(pathList.split(",")).map(Vector::fromPath).collect(toUnmodifiableList());
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

    public static class Grid {

        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;

        private final List<List<Character>> grid;
        private final List<Point> points;

        private Grid(Point rightTop, Point leftBottom, List<Point> points) {
            this.maxX = Math.max(rightTop.getX(), 0);
            this.maxY = Math.max(rightTop.getY(), 0);
            this.minX = Math.min(leftBottom.getX(), 0);
            this.minY = Math.min(leftBottom.getY(), 0);
            this.points = points;
            this.grid = initEmptyGrid(this);
        }

        public static Grid from(List<Point> points) {
            final Point rightTop = getRightTop(points);
            final Point leftBottom = getLeftBottom(points);

            final Grid grid = new Grid(rightTop, leftBottom, points);
            grid.plotPointsToGrid();
            return grid;
        }

        private static Point getRightTop(final List<Point> allPoints) {
            final int maxX = allPoints.stream().map(Coordinate::getX).max(Integer::compareTo).orElse(0);
            final int maxY = allPoints.stream().map(Coordinate::getY).max(Integer::compareTo).orElse(0);
            return new Point(maxX, maxY);
        }

        private static Point getLeftBottom(final List<Point> allPoints) {
            final int minX = allPoints.stream().map(Coordinate::getX).min(Integer::compareTo).orElse(0);
            final int minY = allPoints.stream().map(Coordinate::getY).min(Integer::compareTo).orElse(0);
            return new Point(minX, minY);
        }

        private static List<List<Character>> initEmptyGrid(Grid grid) {
            final int xSize = grid.maxX - grid.minX + GRID_OFFSET * 3;
            final int ySize = grid.maxY - grid.minY + GRID_OFFSET * 3;

            return IntStream.range(0, ySize).boxed()
                    .map(y -> IntStream.range(0, xSize).boxed()
                            .map(x -> '.')
                            .collect(toList())
                    ).collect(toList());
        }

        private void plotPointsToGrid() {
            setPointOnGrid(CENTRAL_PORT);
            points.forEach(this::setPointOnGrid);
        }

        private void setPointOnGrid(Point p) {
            final int x = p.getX() + GRID_OFFSET - minX;
            final int y = grid.size() - GRID_OFFSET * 2 - p.getY() + minY;
            final char c = getCharForType(p.getType());
            grid.get(y).set(x, c);
        }

        private static char getCharForType(Point.Type type) {
            if (type == Point.Type.UNKNOWN) {
                return '?';
            }
            if (type == Point.Type.CENTRAL_PORT) {
                return 'o';
            }
            if (type == Point.Type.HORIZONTAL) {
                return '-';
            }
            if (type == Point.Type.VERTICAL) {
                return '|';
            }
            if (type == Point.Type.CORNER) {
                return '+';
            }
            if (type == Point.Type.INTERSECTION) {
                return 'X';
            }
            return '*';
        }


        public String getPrintableVersion() {
            return grid.stream().map(characters -> Joiner.on("").join(characters)).collect(Collectors.joining("\n"));

        }
    }
}
