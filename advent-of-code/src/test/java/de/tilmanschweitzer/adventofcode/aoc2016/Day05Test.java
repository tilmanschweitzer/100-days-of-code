package de.tilmanschweitzer.adventofcode.aoc2016;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals("c6697b55", new Day05().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals("8c35d1ab", new Day05().getResultOfSecondPuzzle());
    }

    @Test
    void findPasswordForDoorId() {
        assertEquals("18f47a30", Day05.findPasswordForDoorId("abc", 5, 8));
    }

    @Test
    void findMoreComplexPasswordForDoorId() {
        assertEquals("05ace8e3", Day05.findMoreComplexPasswordForDoorId("abc", 5, 8));
    }
}
