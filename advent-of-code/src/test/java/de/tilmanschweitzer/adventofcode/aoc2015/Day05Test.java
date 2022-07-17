package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(236, new Day05().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(51, new Day05().getResultOfSecondPuzzle());
    }

    @Test
    public void detectNiceStrings() {
        assertTrue(Day05.isNiceString("ugknbfddgicrmopn"));
        assertTrue(Day05.isNiceString("aaa"));
    }

    @Test
    public void detectNaughtyStrings() {
        assertFalse(Day05.isNiceString("jchzalrnumimnmhp"));
        assertFalse(Day05.isNiceString("haegwjzuvuyypxyu"));
        assertFalse(Day05.isNiceString("dvszwmarrgswjxmb"));
    }


    @Test
    public void detectNiceStringsVersion2() {
        assertTrue(Day05.isNiceStringVersion2("qjhvhtzxzqqjkmpb"));
        assertTrue(Day05.isNiceStringVersion2("xxyxx"));
    }

    @Test
    public void detectNaughtyStringsVersion2() {
        assertFalse(Day05.isNiceStringVersion2("uurcxstgmygtbstg"));
        assertFalse(Day05.isNiceStringVersion2("ieodomkazucvgmuy"));
    }
}
