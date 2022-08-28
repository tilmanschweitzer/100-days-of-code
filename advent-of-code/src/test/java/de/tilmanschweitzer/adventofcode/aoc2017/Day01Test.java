package de.tilmanschweitzer.adventofcode.aoc2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(1158, new Day01().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(1132, new Day01().getResultOfSecondPuzzle());
    }

    @Test
    public void sumDuplicateNumbers() {
        assertEquals(3, Day01.sumDuplicateNumbers(Day01.parseNumbers("1122")));
        assertEquals(4, Day01.sumDuplicateNumbers(Day01.parseNumbers("1111")));
        assertEquals(0, Day01.sumDuplicateNumbers(Day01.parseNumbers("1234")));
        assertEquals(9, Day01.sumDuplicateNumbers(Day01.parseNumbers("91212129")));
    }


    @Test
    public void sumDuplicateNumbersV2() {
        assertEquals(6, Day01.sumDuplicateNumbersV2(Day01.parseNumbers("1212")));
        assertEquals(0, Day01.sumDuplicateNumbersV2(Day01.parseNumbers("1221")));
        assertEquals(4, Day01.sumDuplicateNumbersV2(Day01.parseNumbers("123425")));
        assertEquals(12, Day01.sumDuplicateNumbersV2(Day01.parseNumbers("123123")));
        assertEquals(4, Day01.sumDuplicateNumbersV2(Day01.parseNumbers("12131415")));

    }
}
