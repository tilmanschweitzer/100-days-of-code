package de.tilmanschweitzer.adventofcode.common.combination;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.common.combination.validator.CollectionSizeValidator;
import de.tilmanschweitzer.adventofcode.common.combination.validator.CombinationValidator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

public class OrderedCombinations {

    public static <E> Set<List<E>> allCombinationsAsSet(Collection<E> elements, CombinationValidator<E> combinationValidator) {
        return allCombinations(elements, combinationValidator).collect(Collectors.toUnmodifiableSet());
    }

    public static <E> Set<List<E>> allCombinationsAsSet(Collection<E> elements) {
        return allCombinations(elements).collect(Collectors.toUnmodifiableSet());
    }

    private static <E> Stream<List<E>>  allCombinations(Collection<E> elements) {
        return allCombinations(elements, new CollectionSizeValidator<>(elements.size()));
    }

    public static <E> Stream<List<E>> allCombinations(Collection<E> elements, CombinationValidator<E> combinationValidator) {
        return searchCombinations(elements, emptyList(), combinationValidator);
    }

    private static <E> Stream<List<E>> searchCombinations(Collection<E> availableElements, List<E> partialCombination, CombinationValidator<E> combinationValidator) {
        if (combinationValidator.isValidCombination(partialCombination)) {
            return Stream.of(partialCombination);
        }
        if (combinationValidator.isInvalidPartialCombination(partialCombination)) {
            return Stream.empty();
        }
        return availableElements.stream().parallel().map(availableElement -> {
            final List<E> nextPartialCombination = new ImmutableList.Builder<E>().addAll(partialCombination).add(availableElement).build();
            final Collection<E> nextAvailableElements = new ArrayList<>(availableElements);
            nextAvailableElements.remove(availableElement);
            return searchCombinations(nextAvailableElements, nextPartialCombination, combinationValidator);
        }).flatMap(Streams::concat);
    }
}
