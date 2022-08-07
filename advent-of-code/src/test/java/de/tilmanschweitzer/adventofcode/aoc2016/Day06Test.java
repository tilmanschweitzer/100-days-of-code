package de.tilmanschweitzer.adventofcode.aoc2016;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static de.tilmanschweitzer.adventofcode.common.Converters.stringToCharList;
import static org.junit.jupiter.api.Assertions.*;

class Day06Test {


    @Test
    public void reconstructMessage() {
        final List<String> testInput = List.of("eedadn",
                "drvtee",
                "eandsr",
                "raavrd",
                "atevrs",
                "tsrnev",
                "sdttsa",
                "rasrtv",
                "nssdts",
                "ntnada",
                "svetve",
                "tesnvt",
                "vntsnd",
                "vrdear",
                "dvrsen",
                "enarar");


        assertEquals("easter", Day06.reconstructMessage(testInput));
    }


    @Test
    public void countChars() {
        final Map<Character, Long> expectedResult = Map.of('a', 3L, 'b', 2L, 'c', 1L, 'z', 2L, 'x', 1L);
        assertEquals(expectedResult, Day06.countChars(stringToCharList("aaabbczxz")));
    }

}
