package de.tilmanschweitzer.adventofcode.aoc2018;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01NotQuiteLispTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(513, new Day01().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(287, new Day01().getResultOfSecondPuzzle());
    }

    @Test
    void firstFirstDuplicateFrequency() {
        assertEquals(2, Day01.firstFirstDuplicateFrequency(List.of(1, -2, +3, 1)));
        assertEquals(0, Day01.firstFirstDuplicateFrequency(List.of(1, -1)));
        assertEquals(10, Day01.firstFirstDuplicateFrequency(List.of(3, 3, 4, -2, -4)));
        assertEquals(5, Day01.firstFirstDuplicateFrequency(List.of(-6, +3, +8, +5, -6)));
        assertEquals(14, Day01.firstFirstDuplicateFrequency(List.of(+7, +7, -2, -7, -4)));
    }

}
