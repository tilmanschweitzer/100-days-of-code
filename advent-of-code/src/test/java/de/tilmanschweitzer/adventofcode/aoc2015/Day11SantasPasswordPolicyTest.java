package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11SantasPasswordPolicyTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals("hxbxxyzz", new Day11SantasPasswordPolicy().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals("hxcaabcc", new Day11SantasPasswordPolicy().getResultOfSecondPuzzle());
    }

    @Test
    void findNextPassword() {
        assertEquals("abcdffaa", Day11SantasPasswordPolicy.findNextPassword("abcdefgh"));
        assertEquals("ghjaabcc", Day11SantasPasswordPolicy.findNextPassword("ghijklmn"));
    }

    @Test
    void incrementPassword() {
        assertEquals("xy", Day11SantasPasswordPolicy.incrementPassword("xx"));
        assertEquals("xz", Day11SantasPasswordPolicy.incrementPassword("xy"));
        assertEquals("ya", Day11SantasPasswordPolicy.incrementPassword("xz"));
        assertEquals("yb", Day11SantasPasswordPolicy.incrementPassword("ya"));
        assertEquals("aa", Day11SantasPasswordPolicy.incrementPassword("z"));
    }

    @Test
    void isValidPassword() {
        assertTrue(Day11SantasPasswordPolicy.isValidPassword("abcdffaa"));
        assertTrue(Day11SantasPasswordPolicy.isValidPassword("ghjaabcc"));
        assertFalse(Day11SantasPasswordPolicy.isValidPassword("hijklmmn"));
        assertFalse(Day11SantasPasswordPolicy.isValidPassword("abbceffg"));
        assertFalse(Day11SantasPasswordPolicy.isValidPassword("abbcegjk"));
    }

    @Test
    void includesOneStraightOfThreeIncreasingLetters() {
        assertTrue(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("hijklmmn"));
        assertTrue(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("abc"));
        assertTrue(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("xyz"));
        assertTrue(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("aaamnozzz"));
        assertFalse(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("abd"));
        assertFalse(Day11SantasPasswordPolicy.includesOneStraightOfThreeIncreasingLetters("abdeghjkmlpq"));
    }

    @Test
    void notContainConfusingLetters() {
        assertTrue(Day11SantasPasswordPolicy.notContainConfusingLetters("abc"));
        assertTrue(Day11SantasPasswordPolicy.notContainConfusingLetters("xyz"));
        assertFalse(Day11SantasPasswordPolicy.notContainConfusingLetters("hijklmmn"));
        assertFalse(Day11SantasPasswordPolicy.notContainConfusingLetters("abi"));
        assertFalse(Day11SantasPasswordPolicy.notContainConfusingLetters("mno"));
        assertFalse(Day11SantasPasswordPolicy.notContainConfusingLetters("kl"));
    }

    @Test
    void containTwoLetterPairs() {
        assertTrue(Day11SantasPasswordPolicy.containTwoLetterPairs("aabb"));
        assertTrue(Day11SantasPasswordPolicy.containTwoLetterPairs("abcddeff"));
        assertFalse(Day11SantasPasswordPolicy.containTwoLetterPairs("aazxy"));
        assertFalse(Day11SantasPasswordPolicy.containTwoLetterPairs("try"));
        assertFalse(Day11SantasPasswordPolicy.containTwoLetterPairs("blog"));
    }

}
