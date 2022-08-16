package de.tilmanschweitzer.adventofcode.aoc2017;

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
        assertEquals(0, new Day02().getResultOfSecondPuzzle());
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
    public void spreadsheetChecksum() {
        final String s = "5 1 9 5\n" +
                "7 5 3\n" +
                "2 4 6 8";

        final List<List<Integer>> input = Arrays.stream(s.split("\n")).map(Day02::parseNumbers).collect(Collectors.toUnmodifiableList());

        assertEquals(18, Day02.spreadsheetChecksum(input));
    }
}
