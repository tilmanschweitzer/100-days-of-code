package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Pair;
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

    @Test
    public void findAbaCombination() {
        assertEquals(List.of(Pair.of('a', 'b')), Day07.findAbaCombination("aba"));
        assertEquals(List.of(Pair.of('b', 'a')), Day07.findAbaCombination("bab"));
        assertEquals(List.of(), Day07.findAbaCombination("aaa"));
    }

    @Test
    public void hasSupportForSSL() {
        assertTrue(Day07.hasSupportForSSL("aba[bab]xyz"));
        assertTrue(Day07.hasSupportForSSL("aaa[kek]eke"));
        assertTrue(Day07.hasSupportForSSL("zazbz[bzb]cdb"));

        assertFalse(Day07.hasSupportForSSL("xyx[xyx]xyx"));
    }
}
