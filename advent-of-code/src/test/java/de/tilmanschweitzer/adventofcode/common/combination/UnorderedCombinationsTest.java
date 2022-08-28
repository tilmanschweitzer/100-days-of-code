package de.tilmanschweitzer.adventofcode.common.combination;

import de.tilmanschweitzer.adventofcode.common.combination.validator.SumValidator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnorderedCombinationsTest {

    @Test
    public void matchingUnorderedCombinationsAsSet() {
        final SumValidator<Integer> integerSumValidator = new SumValidator<>(7, Function.identity());

        final Set<Set<Integer>> result = UnorderedCombinations.allCombinationsAsSet(List.of(1, 2, 3, 4, 5, 6), integerSumValidator);
        final Set<Set<Integer>> expectedResult = Set.of(Set.of(1, 2, 4), Set.of(4, 3), Set.of(1, 6), Set.of(2, 5));

        assertEquals(expectedResult, result);
    }
}
