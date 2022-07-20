package de.tilmanschweitzer.adventofcode.common;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class CombinationsTest {

    @Test
    public void allCombinations() {
        assertEquals(Set.of(Collections.emptyList()), Combinations.allCombinations(emptySet()));
        assertEquals(Set.of(List.of(1)), Combinations.allCombinations(Set.of(1)));
        assertEquals(Set.of(List.of(1, 2), List.of(2, 1)), Combinations.allCombinations(Set.of(1,  2)));
        assertEquals(Set.of(List.of(1, 2, 3), List.of(1, 3, 2), List.of(2, 1, 3), List.of(2, 3, 1), List.of(3, 1, 2), List.of(3, 2, 1)), Combinations.allCombinations(Set.of(1,  2, 3)));

        assertEquals(24, Combinations.allCombinations(Set.of(1, 2, 3, 4)).size());
        assertEquals(120, Combinations.allCombinations(Set.of(1, 2, 3, 4, 5)).size());
    }
}
