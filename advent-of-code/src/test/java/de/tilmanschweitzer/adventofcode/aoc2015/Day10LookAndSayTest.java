package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10LookAndSayTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(252594, new Day10LookAndSay().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(3579328, new Day10LookAndSay().getResultOfSecondPuzzle());
    }

    @Test
    void lookAndSay() {
        assertEquals("11", Day10LookAndSay.lookAndSay("1"));
        assertEquals("21", Day10LookAndSay.lookAndSay("11"));
        assertEquals("1211", Day10LookAndSay.lookAndSay("21"));
        assertEquals("111221", Day10LookAndSay.lookAndSay("1211"));
    }

    @Test
    void lookAndSayWithRounds() {
        assertEquals("11", Day10LookAndSay.lookAndSay("1", 1));
        assertEquals("21", Day10LookAndSay.lookAndSay("1", 2));
        assertEquals("1211", Day10LookAndSay.lookAndSay("1", 3));
        assertEquals("111221", Day10LookAndSay.lookAndSay("1", 4));
    }
}
