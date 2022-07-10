package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day08Test {

    @Test
    public void numberOfInputChars() {
        assertEquals(2, Day08.numberOfInputChars("\"\""));
        assertEquals(5, Day08.numberOfInputChars("\"abc\""));
        assertEquals(10, Day08.numberOfInputChars("\"aaa\\\"aaa\""));
        assertEquals(6, Day08.numberOfInputChars("\"\\x27\""));
    }

    @Test
    public void numberOfInterpretedChars() {
        assertEquals(0, Day08.numberOfParsedChars("\"\""));
        assertEquals(3, Day08.numberOfParsedChars("\"abc\""));
        assertEquals(7, Day08.numberOfParsedChars("\"aaa\\\"aaa\""));
        assertEquals(1, Day08.numberOfParsedChars("\"\\x27\""));
        assertEquals(1, Day08.numberOfParsedChars("\"\\\\\""));
    }

    @Test
    public void parseString() {
        assertEquals("", Day08.parseString("\"\""));
        assertEquals("\\", Day08.parseString("\"\\\\\""));
        assertEquals("abc", Day08.parseString("\"abc\""));
        assertEquals("aaa\"aaa", Day08.parseString("\"aaa\\\"aaa\""));
        assertEquals("'", Day08.parseString("\"\\x27\""));;
    }
}
