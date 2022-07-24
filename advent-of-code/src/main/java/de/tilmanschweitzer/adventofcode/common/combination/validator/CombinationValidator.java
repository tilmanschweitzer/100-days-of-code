package de.tilmanschweitzer.adventofcode.common.combination.validator;

import java.util.Collection;

public interface CombinationValidator<E> {
    boolean isValidCombination(Collection<E> combination);

    boolean isInvalidPartialCombination(Collection<E> partialCombination);
}
