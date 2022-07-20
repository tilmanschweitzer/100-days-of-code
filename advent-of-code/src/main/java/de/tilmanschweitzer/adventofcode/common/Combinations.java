package de.tilmanschweitzer.adventofcode.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Combinations {

    public static <T> Set<List<T>> allCombinations(Set<T> elements) {
        if (elements.isEmpty()) {
            return Set.of(emptyList());
        }
        if (elements.size() == 1) {
            return Set.of(elements.stream().collect(toUnmodifiableList()));
        }

        final Set<List<T>> allCombinations = new HashSet<>();

        searchCombinations(allCombinations, elements, emptyList());

        return allCombinations;
    }

    private static <T> void searchCombinations(Set<List<T>> resultList, Set<T> elements, List<T> partialCombination) {
        if (partialCombination.size() == elements.size()) {
            resultList.add(partialCombination);
        } else {
            final List<T> availableElements = elements.stream().filter(element -> !partialCombination.contains(element)).collect(toUnmodifiableList());
            for (T availableElement : availableElements) {
                final List<T> nextPartialCombination = Streams.concat(partialCombination.stream(), Stream.of(availableElement)).collect(toUnmodifiableList());
                searchCombinations(resultList, elements, nextPartialCombination);
            }
        }
    }
}
