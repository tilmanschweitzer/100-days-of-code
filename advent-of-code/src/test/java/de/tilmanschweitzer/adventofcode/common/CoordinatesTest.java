package de.tilmanschweitzer.adventofcode.common;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinatesTest {

    @Test
    public void forEachCoordinateInGrid() {
        final Set<Coordinate> result2x3Grid = Coordinates.coordinateStreamWithinGrid(2, 3).collect(Collectors.toUnmodifiableSet());
        final Set<BasicCoordinate> expectedResult2x3Grid = BasicCoordinate.of(0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2);

        assertEquals(expectedResult2x3Grid, result2x3Grid);

        final Set<Coordinate> result4x1Grid = Coordinates.coordinateStreamWithinGrid(4, 1).collect(Collectors.toUnmodifiableSet());
        final Set<BasicCoordinate> expectedResult4x1Grid = BasicCoordinate.of(0, 0, 1, 0, 2, 0, 3, 0);

        assertEquals(expectedResult4x1Grid, result4x1Grid);
    }
}
