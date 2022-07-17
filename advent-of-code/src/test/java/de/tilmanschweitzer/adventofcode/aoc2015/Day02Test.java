package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day02.Dimensions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(1606483, new Day02().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(3842356, new Day02().getResultOfSecondPuzzle());
    }

    @Test
    public void testDimensionsFromInput() {
        assertEquals(new Dimensions(2, 3, 4), Dimensions.fromInput("2x3x4"));
        assertEquals(new Dimensions(1, 1, 10), Dimensions.fromInput("1x1x10"));
    }

    @Test
    public void testDimensionsGetSurface() {
        assertEquals(52, new Dimensions(2, 3, 4).getSurface());
        assertEquals(42, new Dimensions(1, 1, 10).getSurface());
    }

    @Test
    public void testDimensionsGetSurfaceWithSlack() {
        assertEquals(58, new Dimensions(2, 3, 4).getSurfaceWithSlack());
        assertEquals(43, new Dimensions(1, 1, 10).getSurfaceWithSlack());
    }

    @Test
    public void testDimensionsGetWrapRibbon() {
        assertEquals(10, new Dimensions(2, 3, 4).getWrapRibbon());
        assertEquals(4, new Dimensions(1, 1, 10).getWrapRibbon());
    }

    @Test
    public void testDimensionsGetBowRibbon() {
        assertEquals(24, new Dimensions(2, 3, 4).getBowRibbon());
        assertEquals(10, new Dimensions(1, 1, 10).getBowRibbon());
    }

    @Test
    public void testDimensionsGetTotalRibbon() {
        assertEquals(34, new Dimensions(2, 3, 4).getTotalRibbon());
        assertEquals(14, new Dimensions(1, 1, 10).getTotalRibbon());
    }
}
