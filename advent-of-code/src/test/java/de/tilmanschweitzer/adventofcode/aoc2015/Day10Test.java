package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    public void lookAndSay() {
        assertEquals("11", Day10.lookAndSay("1"));
        assertEquals("21", Day10.lookAndSay("11"));
        assertEquals("1211", Day10.lookAndSay("21"));
        assertEquals("111221", Day10.lookAndSay("1211"));
    }

    @Test
    public void lookAndSayWithRounds() {
        assertEquals("11", Day10.lookAndSay("1", 1));
        assertEquals("21", Day10.lookAndSay("1",2));
        assertEquals("1211", Day10.lookAndSay("1", 3));
        assertEquals("111221", Day10.lookAndSay("1", 4));
    }
}
