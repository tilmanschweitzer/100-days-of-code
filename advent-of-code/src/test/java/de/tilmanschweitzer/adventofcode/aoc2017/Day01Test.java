package de.tilmanschweitzer.adventofcode.aoc2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    @Test
    public void sumDuplicateNumbers() {
        assertEquals(3, Day01.sumDuplicateNumbers(Day01.parseNumbers("1122")));
        assertEquals(4, Day01.sumDuplicateNumbers(Day01.parseNumbers("1111")));
        assertEquals(0, Day01.sumDuplicateNumbers(Day01.parseNumbers("1234")));
        assertEquals(9, Day01.sumDuplicateNumbers(Day01.parseNumbers("91212129")));
    }

}
