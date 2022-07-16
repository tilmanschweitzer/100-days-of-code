package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    public void findNextPassword() {
        assertEquals("abcdffaa", Day11.findNextPassword("abcdefgh"));
        assertEquals("ghjaabcc", Day11.findNextPassword("ghijklmn"));
    }

    @Test
    public void incrementPassword() {
        assertEquals("xy", Day11.incrementPassword("xx"));
        assertEquals("xz", Day11.incrementPassword("xy"));
        assertEquals("ya", Day11.incrementPassword("xz"));
        assertEquals("yb", Day11.incrementPassword("ya"));
        assertEquals("aa", Day11.incrementPassword("z"));
    }

    @Test
    public void isValidPassword() {
        assertTrue(Day11.isValidPassword("abcdffaa"));
        assertTrue(Day11.isValidPassword("abcdefhh"));
        assertFalse(Day11.isValidPassword("hijklmmn"));
        assertFalse(Day11.isValidPassword("abbceffg"));
        assertFalse(Day11.isValidPassword("abbcegjk"));
    }

    @Test
    public void includesOneStraightOfThreeIncreasingLetters() {
        assertTrue(Day11.includesOneStraightOfThreeIncreasingLetters("hijklmmn"));
        assertTrue(Day11.includesOneStraightOfThreeIncreasingLetters("abc"));
        assertTrue(Day11.includesOneStraightOfThreeIncreasingLetters("xyz"));
        assertTrue(Day11.includesOneStraightOfThreeIncreasingLetters("aaamnozzz"));
        assertFalse(Day11.includesOneStraightOfThreeIncreasingLetters("abd"));
        assertFalse(Day11.includesOneStraightOfThreeIncreasingLetters("abdeghjkmlpq"));
    }

    @Test
    public void notContainConfusingLetters() {
        assertTrue(Day11.notContainConfusingLetters("abc"));
        assertTrue(Day11.notContainConfusingLetters("xyz"));
        assertFalse(Day11.notContainConfusingLetters("hijklmmn"));
        assertFalse(Day11.notContainConfusingLetters("abi"));
        assertFalse(Day11.notContainConfusingLetters("mno"));
        assertFalse(Day11.notContainConfusingLetters("kl"));
    }

    @Test
    public void containTwoLetterPairs() {
        assertTrue(Day11.containTwoLetterPairs("aabb"));
        assertTrue(Day11.containTwoLetterPairs("abcddeff"));
        assertFalse(Day11.containTwoLetterPairs("aazxy"));
        assertFalse(Day11.containTwoLetterPairs("try"));
        assertFalse(Day11.containTwoLetterPairs("blog"));
    }

}
