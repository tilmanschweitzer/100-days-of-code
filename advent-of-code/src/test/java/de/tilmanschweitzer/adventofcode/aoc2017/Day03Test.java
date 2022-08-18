package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.BasicCoordinate;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.BasicCoordinate.of;
import static org.junit.jupiter.api.Assertions.*;

class Day03Test {


    @Test
    public void nextCoordinate() {
        assertEquals(of(1, 0), Day03.nextCoordinate(of(0, 0)));
        assertEquals(of(1, -1), Day03.nextCoordinate(of(1, 0)));
        assertEquals(of(0, -1), Day03.nextCoordinate(of(1, -1)));
        assertEquals(of(-1, -1), Day03.nextCoordinate(of(0, -1)));
        assertEquals(of(-1, 0), Day03.nextCoordinate(of(-1, -1)));
        assertEquals(of(-1, 1), Day03.nextCoordinate(of(-1, 0)));
        assertEquals(of(0, 1), Day03.nextCoordinate(of(-1, 1)));
        assertEquals(of(1, 1), Day03.nextCoordinate(of(0, 1)));

        // move to next level
        assertEquals(of(2, 1), Day03.nextCoordinate(of(1, 1)));
    }

    @Test
    public void coordinatesForAddressSchema() {
        final List<BasicCoordinate> expectedResult = List.of(of(0, 0), of(1, 0), of(1, -1), of(0, -1), of(-1, -1));
        assertEquals(expectedResult, Day03.coordinatesForAddressSchema().limit(5).collect(Collectors.toList()));
    }


    @Test
    public void getCoordinateForAccessPort() {
        assertEquals(of(1, 1), Day03.getCoordinateForAccessPort(9));
        assertEquals(of(2, 2), Day03.getCoordinateForAccessPort(25));
        assertEquals(of(3, 3), Day03.getCoordinateForAccessPort(49));
        assertEquals(of(4, 4), Day03.getCoordinateForAccessPort(81));
    }

    @Test
    public void getManhattanDistanceForAccessPorts() {
        assertEquals(0, Day03.getManhattanDistanceForAccessPort(1));
        assertEquals(2, Day03.getManhattanDistanceForAccessPort(9));
        assertEquals(3, Day03.getManhattanDistanceForAccessPort(12));
        assertEquals(2, Day03.getManhattanDistanceForAccessPort(23));
        assertEquals(31, Day03.getManhattanDistanceForAccessPort(1024));
    }
}
