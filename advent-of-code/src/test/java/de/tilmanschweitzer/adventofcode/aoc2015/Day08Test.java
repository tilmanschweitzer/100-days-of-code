package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day08Test {

    @Test
    public void countInputChars() {
        assertEquals(2, Day08.countInputChars("\"\""));
        assertEquals(5, Day08.countInputChars("\"abc\""));
        assertEquals(10, Day08.countInputChars("\"aaa\\\"aaa\""));
        assertEquals(6, Day08.countInputChars("\"\\x27\""));
        assertEquals(6, Day08.countInputChars("\"x\\\\x\""));
        assertEquals(4, Day08.countInputChars("\"\\\\\""));

    }

    @Test
    public void countParsedChars() {
        assertEquals(0, Day08.countParsedChars("\"\""));
        assertEquals(3, Day08.countParsedChars("\"abc\""));
        assertEquals(7, Day08.countParsedChars("\"aaa\\\"aaa\""));
        assertEquals(1, Day08.countParsedChars("\"\\x27\""));
        assertEquals(3, Day08.countParsedChars("\"x\\\\x\""));
        assertEquals(1, Day08.countParsedChars("\"\\\\\""));
        assertEquals("d\\gkbqo\\fwukyxab\"u".length(), Day08.countParsedChars("\"d\\\\gkbqo\\\\fwukyxab\\\"u\""));
        assertEquals("kbngyfvvsdismznhar\\p\"\"gpryt\"jaeh".length(), Day08.countParsedChars("\"kbngyfvvsdismznhar\\\\p\\\"\\\"gpryt\\\"jaeh\""));
        assertEquals("jcrkptrsasjp\\\"cwigzynjgspxxv\\vyb".length(), Day08.countParsedChars("\"jcrkptrsasjp\\\\\\\"cwigzynjgspxxv\\\\vyb\""));
    }

    @Test
    public void parseString() {
        assertEquals("", Day08.parseString("\"\""));
        assertEquals("\\", Day08.parseString("\"\\\\\""));
        assertEquals("abc", Day08.parseString("\"abc\""));
        assertEquals("aaa\"aaa", Day08.parseString("\"aaa\\\"aaa\""));
        assertEquals("'", Day08.parseString("\"\\x27\""));;
        assertEquals("d\\gkbqo\\fwukyxab\"u", Day08.parseString("\"d\\\\gkbqo\\\\fwukyxab\\\"u\""));
    }
}
