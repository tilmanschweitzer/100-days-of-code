package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.aoc2016.Day03.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day03Test {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(983L, new Day03().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(1836L, new Day03().getResultOfSecondPuzzle());
    }

    @Test
    void parse() {
        final Day03 day03 = new Day03();
        assertEquals(new Triangle(1, 2, 3), day03.parseLine("    1    2\t3    "));
        assertEquals(new Triangle(315, 200, 999), day03.parseLine("  \t  315    200\t    999 "));
    }


    @Test
    void isValidTriangle() {
        assertTrue(new Triangle(2, 3, 4).isValid());
        assertTrue(new Triangle(10, 6, 5).isValid());
        assertFalse(new Triangle(1, 1, 2).isValid());
    }
}
