package de.tilmanschweitzer.adventofcode.aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day04Test {

    @Test
    void isValidPassPhrase() {
        assertTrue(Day04.isValidPassPhrase(List.of("aa", "bb", "cc", "dd", "ee")));
        assertTrue(Day04.isValidPassPhrase(List.of("aa", "bb", "cc", "dd", "aaa")));

        assertFalse(Day04.isValidPassPhrase(List.of("aa", "bb", "cc", "dd", "aa")));
    }

    @Test
    void isValidPassPhraseV2() {
        assertTrue(Day04.isValidPassPhraseV2(List.of("abcde", "fghij")));
        assertTrue(Day04.isValidPassPhrase(List.of("a", "ab", "abc", "abd", "abf", "abj")));
        assertTrue(Day04.isValidPassPhrase(List.of("iiii", "oiii", "ooii", "oooi", "oooo")));

        assertFalse(Day04.isValidPassPhraseV2(List.of("abcde", "xyz", "ecdab")));
        assertFalse(Day04.isValidPassPhraseV2(List.of("oiii", "ioii", "iioi", "iiio")));
    }
}
