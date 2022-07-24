package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day08CountEncodedAndDecodedStringsTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(1342, new Day08CountEncodedAndDecodedStrings().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(2074, new Day08CountEncodedAndDecodedStrings().getResultOfSecondPuzzle());
    }

    @Test
    public void countInputChars() {
        assertEquals(2, Day08CountEncodedAndDecodedStrings.countInputChars("\"\""));
        assertEquals(5, Day08CountEncodedAndDecodedStrings.countInputChars("\"abc\""));
        assertEquals(10, Day08CountEncodedAndDecodedStrings.countInputChars("\"aaa\\\"aaa\""));
        assertEquals(6, Day08CountEncodedAndDecodedStrings.countInputChars("\"\\x27\""));
        assertEquals(6, Day08CountEncodedAndDecodedStrings.countInputChars("\"x\\\\x\""));
        assertEquals(4, Day08CountEncodedAndDecodedStrings.countInputChars("\"\\\\\""));
    }

    @Test
    public void countParsedChars() {
        assertEquals(0, Day08CountEncodedAndDecodedStrings.countParsedChars("\"\""));
        assertEquals(3, Day08CountEncodedAndDecodedStrings.countParsedChars("\"abc\""));
        assertEquals(7, Day08CountEncodedAndDecodedStrings.countParsedChars("\"aaa\\\"aaa\""));
        assertEquals(1, Day08CountEncodedAndDecodedStrings.countParsedChars("\"\\x27\""));
        assertEquals(3, Day08CountEncodedAndDecodedStrings.countParsedChars("\"x\\\\x\""));
        assertEquals(1, Day08CountEncodedAndDecodedStrings.countParsedChars("\"\\\\\""));
        assertEquals("d\\gkbqo\\fwukyxab\"u".length(), Day08CountEncodedAndDecodedStrings.countParsedChars("\"d\\\\gkbqo\\\\fwukyxab\\\"u\""));
        assertEquals("kbngyfvvsdismznhar\\p\"\"gpryt\"jaeh".length(), Day08CountEncodedAndDecodedStrings.countParsedChars("\"kbngyfvvsdismznhar\\\\p\\\"\\\"gpryt\\\"jaeh\""));
        assertEquals("jcrkptrsasjp\\\"cwigzynjgspxxv\\vyb".length(), Day08CountEncodedAndDecodedStrings.countParsedChars("\"jcrkptrsasjp\\\\\\\"cwigzynjgspxxv\\\\vyb\""));
    }

    @Test
    public void parseString() {
        assertEquals("", Day08CountEncodedAndDecodedStrings.parseString("\"\""));
        assertEquals("\\", Day08CountEncodedAndDecodedStrings.parseString("\"\\\\\""));
        assertEquals("abc", Day08CountEncodedAndDecodedStrings.parseString("\"abc\""));
        assertEquals("aaa\"aaa", Day08CountEncodedAndDecodedStrings.parseString("\"aaa\\\"aaa\""));
        assertEquals("'", Day08CountEncodedAndDecodedStrings.parseString("\"\\x27\""));;
        assertEquals("d\\gkbqo\\fwukyxab\"u", Day08CountEncodedAndDecodedStrings.parseString("\"d\\\\gkbqo\\\\fwukyxab\\\"u\""));
    }

    @Test
    public void escapeString() {
        assertEquals("\"\\\"\\\"\"", Day08CountEncodedAndDecodedStrings.escapeString("\"\""));
        assertEquals("\"\\\"abc\\\"\"", Day08CountEncodedAndDecodedStrings.escapeString("\"abc\""));
    }

    @Test
    public void countEscapedChars() {
        assertEquals(6, Day08CountEncodedAndDecodedStrings.countEscapedChars("\"\""));
        assertEquals(9, Day08CountEncodedAndDecodedStrings.countEscapedChars("\"abc\""));
        assertEquals(16, Day08CountEncodedAndDecodedStrings.countEscapedChars("\"aaa\\\"aaa\""));
        assertEquals(11, Day08CountEncodedAndDecodedStrings.countEscapedChars("\"\\x27\""));
    }
}
