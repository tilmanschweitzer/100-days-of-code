package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(43074, new Day02().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(280, new Day02().getResultOfSecondPuzzle());
    }

    @Test
    public void parse() {
        assertEquals(List.of(5, 1, 9, 5), Day02.parseNumbers("5 1 9 5"));
        assertEquals(List.of(157, 564, 120, 495, 194), Day02.parseNumbers("157\t564\t120\t495\t194"));
    }

    @Test
    public void rowChecksum() {
        assertEquals(8, Day02.rowChecksum(Day02.parseNumbers("5 1 9 5")));
    }

    @Test
    public void findEvenlyDivisiblePair() {
        assertEquals(Pair.of(8, 2), Day02.findEvenlyDivisiblePair(List.of(5, 9, 2, 8)));
    }

    @Test
    public void isEvenlyDivisible() {
        assertTrue(Day02.isEvenlyDivisible(8, 2));
        assertTrue(Day02.isEvenlyDivisible(9, 3));

        assertFalse(Day02.isEvenlyDivisible(2, 8));
        assertFalse(Day02.isEvenlyDivisible(3, 9));
    }

    @Test
    public void spreadsheetChecksum() {
        final String s = "5 1 9 5\n" +
                "7 5 3\n" +
                "2 4 6 8";

        final List<List<Integer>> input = Arrays.stream(s.split("\n")).map(Day02::parseNumbers).collect(Collectors.toUnmodifiableList());

        assertEquals(18, Day02.spreadsheetChecksum(input));
    }

    @Test
    public void spreadsheetChecksumV2() {
        final String s = "5 9 2 8\n" +
                "9 4 7 3\n" +
                "3 8 6 5";

        final List<List<Integer>> input = Arrays.stream(s.split("\n")).map(Day02::parseNumbers).collect(Collectors.toUnmodifiableList());

        assertEquals(9, Day02.spreadsheetChecksumV2(input));
    }
}
