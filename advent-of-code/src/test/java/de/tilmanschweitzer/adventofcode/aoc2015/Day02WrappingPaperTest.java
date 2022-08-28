package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day02WrappingPaper.Dimensions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02WrappingPaperTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(1606483, new Day02WrappingPaper().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(3842356, new Day02WrappingPaper().getResultOfSecondPuzzle());
    }

    @Test
    void testDimensionsFromInput() {
        assertEquals(new Dimensions(2, 3, 4), Dimensions.fromInput("2x3x4"));
        assertEquals(new Dimensions(1, 1, 10), Dimensions.fromInput("1x1x10"));
    }

    @Test
    void testDimensionsGetSurface() {
        assertEquals(52, new Dimensions(2, 3, 4).getSurface());
        assertEquals(42, new Dimensions(1, 1, 10).getSurface());
    }

    @Test
    void testDimensionsGetSurfaceWithSlack() {
        assertEquals(58, new Dimensions(2, 3, 4).getSurfaceWithSlack());
        assertEquals(43, new Dimensions(1, 1, 10).getSurfaceWithSlack());
    }

    @Test
    void testDimensionsGetWrapRibbon() {
        assertEquals(10, new Dimensions(2, 3, 4).getWrapRibbon());
        assertEquals(4, new Dimensions(1, 1, 10).getWrapRibbon());
    }

    @Test
    void testDimensionsGetBowRibbon() {
        assertEquals(24, new Dimensions(2, 3, 4).getBowRibbon());
        assertEquals(10, new Dimensions(1, 1, 10).getBowRibbon());
    }

    @Test
    void testDimensionsGetTotalRibbon() {
        assertEquals(34, new Dimensions(2, 3, 4).getTotalRibbon());
        assertEquals(14, new Dimensions(1, 1, 10).getTotalRibbon());
    }
}
