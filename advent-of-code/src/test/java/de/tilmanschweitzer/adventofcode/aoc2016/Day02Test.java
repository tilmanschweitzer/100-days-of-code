package de.tilmanschweitzer.adventofcode.aoc2016;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {

    @Test
    public void keyCodeForInstructions() {
        assertEquals("2", Day02.keyCodeForInstructions("U"));
        assertEquals("2", Day02.keyCodeForInstructions("UU"));
        assertEquals("8", Day02.keyCodeForInstructions("D"));
        assertEquals("8", Day02.keyCodeForInstructions("DD"));
        assertEquals("4", Day02.keyCodeForInstructions("L"));
        assertEquals("4", Day02.keyCodeForInstructions("LL"));
        assertEquals("6", Day02.keyCodeForInstructions("R"));
        assertEquals("6", Day02.keyCodeForInstructions("RR"));

        assertEquals("1985", Day02.keyCodeForInstructions("ULL", "RRDDD", "LURDL", "UUUUD"));
    }
}
