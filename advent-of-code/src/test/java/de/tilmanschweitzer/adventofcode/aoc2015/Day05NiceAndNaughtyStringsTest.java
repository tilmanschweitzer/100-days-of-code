package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day05NiceAndNaughtyStringsTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(236, new Day05NiceAndNaughtyStrings().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(51, new Day05NiceAndNaughtyStrings().getResultOfSecondPuzzle());
    }

    @Test
    public void detectNiceStrings() {
        assertTrue(Day05NiceAndNaughtyStrings.isNiceString("ugknbfddgicrmopn"));
        assertTrue(Day05NiceAndNaughtyStrings.isNiceString("aaa"));
    }

    @Test
    public void detectNaughtyStrings() {
        assertFalse(Day05NiceAndNaughtyStrings.isNiceString("jchzalrnumimnmhp"));
        assertFalse(Day05NiceAndNaughtyStrings.isNiceString("haegwjzuvuyypxyu"));
        assertFalse(Day05NiceAndNaughtyStrings.isNiceString("dvszwmarrgswjxmb"));
    }


    @Test
    public void detectNiceStringsVersion2() {
        assertTrue(Day05NiceAndNaughtyStrings.isNiceStringVersion2("qjhvhtzxzqqjkmpb"));
        assertTrue(Day05NiceAndNaughtyStrings.isNiceStringVersion2("xxyxx"));
    }

    @Test
    public void detectNaughtyStringsVersion2() {
        assertFalse(Day05NiceAndNaughtyStrings.isNiceStringVersion2("uurcxstgmygtbstg"));
        assertFalse(Day05NiceAndNaughtyStrings.isNiceStringVersion2("ieodomkazucvgmuy"));
    }
}
