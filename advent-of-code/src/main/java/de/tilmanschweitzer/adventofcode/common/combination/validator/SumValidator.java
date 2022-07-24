package de.tilmanschweitzer.adventofcode.common.combination.validator;

import java.util.Collection;
import java.util.function.Function;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;

public class SumValidator<E> implements CombinationValidator<E> {
    private final int expectedSum;
    private final Function<? super E, Integer> getValue;

    public SumValidator(int expectedSum, Function<? super E, Integer> getValue) {
        this.expectedSum = expectedSum;
        this.getValue = getValue;
    }

    @Override
    public boolean isValidCombination(Collection<E> combination) {
        return sum(combination.stream().map(getValue)) == expectedSum;
    }

    @Override
    public boolean isInvalidPartialCombination(Collection<E> partialCombination) {
        return sum(partialCombination.stream().map(getValue)) > expectedSum;
    }
}
