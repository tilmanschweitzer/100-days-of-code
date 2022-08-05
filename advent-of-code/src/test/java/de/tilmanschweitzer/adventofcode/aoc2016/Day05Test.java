package de.tilmanschweitzer.adventofcode.aoc2016;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals("c6697b55", new Day05().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals("", new Day05().getResultOfSecondPuzzle());
    }

    @Test
    public void findHashStaringWith() {
        assertEquals( "18f47a30", Day05.findPasswordForDoorId("abc", 5));
    }
}
