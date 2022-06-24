package de.tilmanschweitzer.adventofcode.aoc2020.app;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    @Test
    public void findMatchingNumberPair_returns1721and299forTestInput() {
        final List<Integer> testInput = List.of(1721, 979, 366, 299, 675, 1456);

        final Optional<Day01.Pair<Integer>> result = Day01.findMatchingNumberPair(testInput, 2020);

        assertTrue(result.isPresent());
        assertEquals(new Day01.Pair<>(1721, 299), result.get());
    }

    @Test
    public void findMatchingNumberPair_worksWithOnlyTwoValues() {
        final List<Integer> testInput = List.of(2010, 10);

        final Optional<Day01.Pair<Integer>> result = Day01.findMatchingNumberPair(testInput, 2020);

        assertTrue(result.isPresent());
        assertEquals(new Day01.Pair<>(2010, 10), result.get());
    }

    @Test
    public void findMatchingNumberPair_returnsEmptyOptionalForLessThan2Numbers() {
        final List<Integer> testInput = List.of(2010);

        final Optional<Day01.Pair<Integer>> result = Day01.findMatchingNumberPair(testInput, 2020);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findMatchingNumberPair_returnsFirstPairIfMultipleOptionsMatch() {
        final List<Integer> testInput = List.of(2010, 2000, 20, 10);

        final Optional<Day01.Pair<Integer>> result = Day01.findMatchingNumberPair(testInput, 2020);

        assertTrue(result.isPresent());
        assertEquals(new Day01.Pair<>(2010, 10), result.get());
    }

    @Test
    public void findMatchingNumberTriplet_returns1721and299forTestInput() {
        final List<Integer> testInput = List.of(1721, 979, 366, 299, 675, 1456);

        final Optional<Day01.Triplet<Integer>> result = Day01.findMatchingNumberTriplet(testInput, 2020);

        assertTrue(result.isPresent());
        assertEquals(new Day01.Triplet<>(979, 366, 675), result.get());
    }
}
