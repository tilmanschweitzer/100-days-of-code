package de.tilmanschweitzer.adventofcode.aoc2016;

import org.junit.jupiter.api.Test;

import static de.tilmanschweitzer.adventofcode.aoc2016.Day02.BATHROOM_KEYPAD;
import static de.tilmanschweitzer.adventofcode.aoc2016.Day02.SIMPLE_KEYPAD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals("52981", new Day02().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals("74CD2", new Day02().getResultOfSecondPuzzle());
    }

    @Test
    public void keyCodeForInstructions() {
        assertEquals("1", Day02.keyCodeForInstructions("1", '1', "U"));
        assertEquals("1", Day02.keyCodeForInstructions("123", '2', "L"));
        assertEquals("3", Day02.keyCodeForInstructions("123", '2', "R"));

        assertEquals("1", Day02.keyCodeForInstructions("1\n2\n3", '2', "U"));
        assertEquals("3", Day02.keyCodeForInstructions("1\n2\n3", '2', "D"));


        assertEquals("2", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "U"));
        assertEquals("2", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "UU"));
        assertEquals("8", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "D"));
        assertEquals("8", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "DD"));
        assertEquals("4", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "L"));
        assertEquals("4", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "LL"));
        assertEquals("6", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "R"));
        assertEquals("6", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "RR"));

        assertEquals("1985", Day02.keyCodeForInstructions(SIMPLE_KEYPAD, '5', "ULL", "RRDDD", "LURDL", "UUUUD"));

        assertEquals("3", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "U"));
        assertEquals("1", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "UU"));
        assertEquals("1", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "UUU"));

        assertEquals("B", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "D"));
        assertEquals("D", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "DD"));
        assertEquals("D", Day02.keyCodeForInstructions(BATHROOM_KEYPAD, '7', "DDD"));
    }
}
