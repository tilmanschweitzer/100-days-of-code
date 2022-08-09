package de.tilmanschweitzer.adventofcode.aoc2016;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(118L, new Day07().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(0L, new Day07().getResultOfSecondPuzzle());
    }

    @Test
    public void containsAbbaCombination() {
        assertTrue(Day07.containsAbbaCombination("abba"));
        assertTrue(Day07.containsAbbaCombination("oxyyx"));

        assertFalse(Day07.containsAbbaCombination("aaaa"));
        assertFalse(Day07.containsAbbaCombination("abac"));
        assertFalse(Day07.containsAbbaCombination("abccab"));
    }

    @Test
    public void getStringsInsideBrackets() {
        assertEquals(List.of("cd", "ij", "mn"), Day07.getStringsInsideBrackets("ab[cd]ef[ij]kl[mn]op"));
    }

    @Test
    public void getStringsOutsideBrackets() {
        assertEquals(List.of("ab", "ef", "kl", "op"), Day07.getStringsOutsideBrackets("ab[cd]ef[ij]kl[mn]op"));
    }

    @Test
    public void hasSupportForTLS() {
        assertTrue(Day07.hasSupportForTLS("abba[mnop]qrst"));
        assertTrue(Day07.hasSupportForTLS("ioxxoj[asdfgh]zxcvbn"));

        assertFalse(Day07.hasSupportForTLS("aaaa[qwer]tyui"));
        assertFalse(Day07.hasSupportForTLS("abcd[bddb]xyyx"));
    }

}
