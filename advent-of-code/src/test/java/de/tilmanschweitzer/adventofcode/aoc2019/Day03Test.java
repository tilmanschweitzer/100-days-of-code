package de.tilmanschweitzer.adventofcode.aoc2019;

import de.tilmanschweitzer.adventofcode.aoc2019.Day03.Point;
import de.tilmanschweitzer.adventofcode.aoc2019.Day03.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day03Test {

    @Test
    public void intersectionOfPaths_example1() {
        final List<Vector> firstPath = Vector.fromPathList("R8,U5,L5,D3");
        final List<Vector> secondPath = Vector.fromPathList("U7,R6,D4,L4");

        final Set<Point> result = Day03.findIntersectionOfPaths(firstPath, secondPath);
        final Set<Point> expectedPoints = Point.fromInputList("3,3;6,5").stream().collect(Collectors.toUnmodifiableSet());

        assertEquals(expectedPoints, result);
    }

    @Test
    public void intersectionOfPaths_example2() {
        final List<Vector> firstPath = Vector.fromPathList("R75,D30,R83,U83,L12,D49,R71,U7,L72");
        final List<Vector> secondPath = Vector.fromPathList("U62,R66,U55,R34,D71,R55,D58,R83");

        final Set<Point> result = Day03.findIntersectionOfPaths(firstPath, secondPath);

        final Point firstPoint = result.stream().sorted().findFirst().get();
        final int expectedDistance = 159;

        assertEquals(expectedDistance, firstPoint.getManhattanDistance());
    }

    @Test
    public void intersectionOfPaths_example3() {
        final List<Vector> firstPath = Vector.fromPathList("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51");
        final List<Vector> secondPath = Vector.fromPathList("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");

        final Set<Point> result = Day03.findIntersectionOfPaths(firstPath, secondPath);

        final Point firstPoint = result.stream().sorted().findFirst().get();
        final int expectedDistance = 135;
        assertEquals(expectedDistance, firstPoint.getManhattanDistance());
    }

    @Test
    public void pointFromPath_withOneVector() {
        final List<Vector> input = Vector.fromPathList("R8");

        final List<Point> result = Point.fromPath(input);
        final List<Point> expectedPoints = Point.fromInputList("1,0;2,0;3,0;4,0;5,0;6,0;7,0;8,0");

        assertEquals(expectedPoints, result);
    }

    @Test
    public void pointFromPath_withTwoVectors() {
        final List<Vector> input = Vector.fromPathList("R8,U3");

        final List<Point> result = Point.fromPath(input);
        final List<Point> expectedPoints = Point.fromInputList("1,0;2,0;3,0;4,0;5,0;6,0;7,0;8,0;8,1;8,2;8,3");

        assertEquals(expectedPoints, result);
    }

    @Test
    public void pointFromPath_withFourVectors() {
        final List<Vector> input = Vector.fromPathList("R8,U5,L5,D3");

        final List<Point> result = Point.fromPath(input);
        final List<Point> expectedPoints = Point.fromInputList("1,0;2,0;3,0;4,0;5,0;6,0;7,0;8,0;8,1;8,2;8,3;8,4;8,5;7,5;6,5;5,5;4,5;3,5;3,4;3,3;3,2");

        assertEquals(expectedPoints, result);
    }


    @Test
    public void pointFromInput() {
        assertEquals(new Point(8, 0), Point.fromInput("8,0"));
        assertEquals(new Point(11, -3), Point.fromInput("11,-3"));
        assertEquals(new Point(-1, 17), Point.fromInput("-1,17"));
    }

    @Test
    public void vectorFromPath() {
        assertEquals(new Vector(8, 0), Vector.fromPath("R8"));
        assertEquals(new Vector(0, 5), Vector.fromPath("U5"));
        assertEquals(new Vector(-5, 0), Vector.fromPath("L5"));
        assertEquals(new Vector(0, -3), Vector.fromPath("D3"));
    }

    @Test
    public void vectorFromPathList() {
        final String input = "R8,U5,L5,D3";

        final List<Vector> result = Vector.fromPathList(input);

        final List<Vector> expectedVectors = List.of(
                new Vector(8, 0),
                new Vector(0, 5),
                new Vector(-5, 0),
                new Vector(0, -3)
        );

        assertEquals(expectedVectors, result);
    }
}
