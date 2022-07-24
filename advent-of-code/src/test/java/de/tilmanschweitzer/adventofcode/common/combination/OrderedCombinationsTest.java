package de.tilmanschweitzer.adventofcode.common.combination;

import de.tilmanschweitzer.adventofcode.common.combination.validator.CollectionSizeValidator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class OrderedCombinationsTest {

    @Test
    public void allCombinationsAsSet() {
        assertEquals(Set.of(Collections.emptyList()), OrderedCombinations.allCombinationsAsSet(emptySet()));
        assertEquals(Set.of(List.of(1)), OrderedCombinations.allCombinationsAsSet(Set.of(1)));
        assertEquals(Set.of(List.of(1, 2), List.of(2, 1)), OrderedCombinations.allCombinationsAsSet(Set.of(1, 2)));
        assertEquals(Set.of(List.of(1, 2, 3), List.of(1, 3, 2), List.of(2, 1, 3), List.of(2, 3, 1), List.of(3, 1, 2), List.of(3, 2, 1)), OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3)));

        assertEquals(24, OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3, 4)).size());
        assertEquals(120, OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3, 4, 5)).size());
        assertEquals(720, OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3, 4, 5, 6)).size());
        assertEquals(40320, OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3, 4, 5, 6, 7, 8)).size());
        assertEquals(362880, OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).size());
    }

    @Test
    public void allCombinationsWithTwoElementsAsSet() {
        final Set<List<Integer>> result = OrderedCombinations.allCombinationsAsSet(Set.of(1, 2, 3), new CollectionSizeValidator<>(2));
        final Set<List<Integer>> expectedResult = Set.of(List.of(1, 2), List.of(1, 3), List.of(2, 1), List.of(2, 3), List.of(3, 1), List.of(3, 2));
        assertEquals(expectedResult, result);
    }
}
