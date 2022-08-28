package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(118L, new Day07().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(260L, new Day07().getResultOfSecondPuzzle());
    }

    @Test
    void containsAbbaCombination() {
        assertTrue(Day07.containsAbbaCombination("abba"));
        assertTrue(Day07.containsAbbaCombination("oxyyx"));

        assertFalse(Day07.containsAbbaCombination("aaaa"));
        assertFalse(Day07.containsAbbaCombination("abac"));
        assertFalse(Day07.containsAbbaCombination("abccab"));
    }

    @Test
    void getStringsInsideBrackets() {
        assertEquals(List.of("cd", "ij", "mn"), Day07.getStringsInsideBrackets("ab[cd]ef[ij]kl[mn]op"));
    }

    @Test
    void getStringsOutsideBrackets() {
        assertEquals(List.of("ab", "ef", "kl", "op"), Day07.getStringsOutsideBrackets("ab[cd]ef[ij]kl[mn]op"));
    }

    @Test
    void hasSupportForTLS() {
        assertTrue(Day07.hasSupportForTLS("abba[mnop]qrst"));
        assertTrue(Day07.hasSupportForTLS("ioxxoj[asdfgh]zxcvbn"));

        assertFalse(Day07.hasSupportForTLS("aaaa[qwer]tyui"));
        assertFalse(Day07.hasSupportForTLS("abcd[bddb]xyyx"));
    }

    @Test
    void findAbaCombination() {
        assertEquals(Set.of(Pair.of('a', 'b')), Day07.findAbaCombination("aba").collect(Collectors.toSet()));
        assertEquals(Set.of(Pair.of('b', 'a')), Day07.findAbaCombination("bab").collect(Collectors.toSet()));
        assertEquals(Set.of(), Day07.findAbaCombination("aaa").collect(Collectors.toSet()));
    }

    @Test
    void hasSupportForSSL() {
        assertTrue(Day07.hasSupportForSSL("aba[bab]xyz"));
        assertTrue(Day07.hasSupportForSSL("aaa[kek]eke"));
        assertTrue(Day07.hasSupportForSSL("zazbz[bzb]cdb"));

        assertFalse(Day07.hasSupportForSSL("xyx[xyx]xyx"));
    }
}
