package de.tilmanschweitzer.adventofcode.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day02WrappingPaperTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(607, new Day02().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(321, new Day02().getResultOfSecondPuzzle());
    }

    @Test
    public void parseLine_parsesTestInputAsExpected() {
        final String testInput = "1-3 a: abcde\n" +
                "1-3 b: cdefg\n" +
                "2-9 c: ccccccccc";

        final List<Day02.PasswordPolicyAndPassword> result = Arrays.stream(testInput.split("\n")).map(new Day02()::parseLine).collect(Collectors.toList());


        assertEquals(3, result.size());
        assertEquals(new Day02.PasswordPolicyAndPassword("abcde", new Day02.PasswordPolicy('a', 1, 3)), result.get(0));
        assertEquals(new Day02.PasswordPolicyAndPassword("cdefg", new Day02.PasswordPolicy('b', 1, 3)), result.get(1));
        assertEquals(new Day02.PasswordPolicyAndPassword("ccccccccc", new Day02.PasswordPolicy('c', 2, 9)), result.get(2));
    }

    @Test
    public void validateMethodA() {
        final Day02.PasswordPolicy passwordPolicy = new Day02.PasswordPolicy('a', 1, 3);


        assertTrue(passwordPolicy.validateMethodA("abcde"));
        assertTrue(passwordPolicy.validateMethodA("a"));
        assertTrue(passwordPolicy.validateMethodA("aa"));
        assertTrue(passwordPolicy.validateMethodA("aaa"));
        assertTrue(passwordPolicy.validateMethodA("XaxaXaX"));

        assertFalse(passwordPolicy.validateMethodA(""));
        assertFalse(passwordPolicy.validateMethodA("aaaa"));
        assertFalse(passwordPolicy.validateMethodA("XaxaXaXaX"));
    }

    @Test
    public void validateMethodB() {
        final Day02.PasswordPolicy passwordPolicy = new Day02.PasswordPolicy('a', 1, 3);

        assertTrue(passwordPolicy.validateMethodB("abcde"));
        assertTrue(passwordPolicy.validateMethodB("a c"));
        assertTrue(passwordPolicy.validateMethodB("c a"));

        assertFalse(passwordPolicy.validateMethodB("aaa"));
        assertFalse(passwordPolicy.validateMethodB("ccc"));
    }

}
