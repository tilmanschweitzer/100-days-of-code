package de.tilmanschweitzer.adventofcode.common.combination.validator;

import java.util.Collection;

public class CollectionSizeValidator<E> implements CombinationValidator<E> {
    private final int expectedSize;

    public CollectionSizeValidator(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    public boolean isValidCombination(Collection<E> combination) {
        return combination.size() == expectedSize;
    }

    @Override
    public boolean isInvalidPartialCombination(Collection<E> partialCombination) {
        return partialCombination.size() > expectedSize;
    }
}
