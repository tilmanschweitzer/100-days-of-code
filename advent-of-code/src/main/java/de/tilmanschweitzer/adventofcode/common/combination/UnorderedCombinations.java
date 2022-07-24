package de.tilmanschweitzer.adventofcode.common.combination;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.common.combination.validator.CombinationValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.util.Collections.emptySet;

public class UnorderedCombinations {

    public static <E> Set<Set<E>> allCombinationsAsSet(Collection<E> elements, CombinationValidator<E> combinationValidator) {
        return searchUnorderedCombinations(new ArrayList<>(elements), emptySet(), combinationValidator).collect(Collectors.toUnmodifiableSet());
    }

    private static <E> Stream<Set<E>> searchUnorderedCombinations(List<E> availableElements, Set<E> partialCombination, CombinationValidator<E> combinationValidator) {
        if (combinationValidator.isValidCombination(partialCombination)) {
            return Stream.of(partialCombination);
        }
        if (combinationValidator.isInvalidPartialCombination(partialCombination)) {
            return Stream.empty();
        }

        return IntStream.range(0, availableElements.size()).boxed().map(availableElementIndex -> {
            final E availableElement = availableElements.get(availableElementIndex);
            final Set<E> nextPartialCombination = new ImmutableSet.Builder<E>().addAll(partialCombination).add(availableElement).build();
            final List<E> nextAvailableElements = new ArrayList<>(availableElements).subList(availableElementIndex, availableElements.size());
            nextAvailableElements.remove(availableElement);
            return searchUnorderedCombinations(nextAvailableElements, nextPartialCombination, combinationValidator);
        }).flatMap(Streams::concat);
    }

}
