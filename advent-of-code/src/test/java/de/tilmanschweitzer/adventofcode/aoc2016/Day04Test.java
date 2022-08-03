package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.aoc2016.Day04.Room;
import de.tilmanschweitzer.adventofcode.aoc2016.Day04.Room.CharCount;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.tilmanschweitzer.adventofcode.common.Converters.stringToCharList;
import static org.junit.jupiter.api.Assertions.*;

class Day04Test {


    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(185371, new Day04().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(0, new Day04().getResultOfSecondPuzzle());
    }

    @Test
    public void parse() {
        assertEquals(new Room("aaaaa-bbb-z-y-x", 123, "abxyz"), Room.parse("aaaaa-bbb-z-y-x-123[abxyz]"));
    }

    @Test
    public void getAllChars() {
        assertEquals(stringToCharList("aaaaabbbzyx"), Room.parse("aaaaa-bbb-z-y-x-123[abxyz]").getAllChars());
    }

    @Test
    public void getUniqueCharsInOrder() {
        assertEquals(stringToCharList("abzyx"), Room.parse("aaaaa-bbb-z-y-x-123[abxyz]").getUniqueCharsInOrder());
        assertEquals(stringToCharList("azdbui"), Room.parse("azdaabuiiaiai-123[azdbui]").getUniqueCharsInOrder());
    }

    @Test
    public void getCharsWithCount() {
        final List<CharCount> expectedCharCounts = List.of(CharCount.of('a', 5), CharCount.of('b', 3), CharCount.of('x'), CharCount.of('y'), CharCount.of('z'));
        assertEquals(expectedCharCounts, Room.parse("aaaaa-bbb-z-y-x-123[abxyz]").getCharsWithCount());

        final List<CharCount> expectedCharCounts2 = List.of(CharCount.of('e', 3), CharCount.of('f', 2), CharCount.of('h', 2), CharCount.of('d'), CharCount.of('g'), CharCount.of('i'));
        assertEquals(expectedCharCounts2, Room.parse("d-eee-ff-g-hh-i-123[efhdg]").getCharsWithCount());

        final List<CharCount> expectedCharCounts3 = List.of(CharCount.of('e', 3), CharCount.of('f', 2), CharCount.of('h', 2), CharCount.of('d'), CharCount.of('g'), CharCount.of('i'));
        assertEquals(expectedCharCounts2, Room.parse("d-eee-ff-g-hh-i-123[efhdg]").getCharsWithCount());
    }

    @Test
    public void getCalculatedCheckSum() {
        assertEquals("abxyz", Room.parse("aaaaa-bbb-z-y-x-123[abxyz]").getCalculatedCheckSum());
        assertEquals("abcde", Room.parse("a-b-c-d-e-f-g-h-987[abcde]").getCalculatedCheckSum());
    }

    @Test
    public void isValid() {
        assertTrue(Room.parse("aaaaa-bbb-z-y-x-123[abxyz]").isValid());
        assertTrue(Room.parse("a-b-c-d-e-f-g-h-987[abcde]").isValid());
        assertTrue(Room.parse("not-a-real-room-404[oarel]").isValid());
        assertFalse(Room.parse("totally-real-room-200[decoy]").isValid());
    }

}
