package de.tilmanschweitzer.adventofcode.common;

import de.tilmanschweitzer.adventofcode.aoc2015.Day13;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
public class Ring<T> {
    private List<T> elements;

    private Ring(T... elements) {
        this.elements = Arrays.stream(elements).collect(Collectors.toUnmodifiableList());
    }

    private Ring(Collection<T> elements) {
        this.elements = elements.stream().collect(Collectors.toUnmodifiableList());
    }

    public static <T> Ring<T> of(T... elements) {
        return new Ring<>(elements);
    }

    public static <T> Ring<T> of(Collection<T> elements) {
        return new Ring<>(elements);
    }

    public static <T> Ring<T> empty() {
        return new Ring<T>();
    }


    public static <T> List<T> normalize(List<T> elements) {
        return normalizeOrder(normalizeFirstElement(elements));
    }

    public static <T> List<T> normalizeFirstElement(List<T> elements) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }

        // Normalize the first element by hashCode
        final Optional<Integer> offsetOptional = indexByLowestHashCode(elements);
        if (offsetOptional.isEmpty()) {
            throw new RuntimeException("Unexpected empty index");
        }
        final int offset = offsetOptional.get();
        final List<T> newList = new ArrayList<>(elements.size());
        newList.addAll(elements.subList(offset, elements.size()));
        newList.addAll(elements.subList(0, offset));
        return newList.stream().collect(Collectors.toUnmodifiableList());
    }

    public static <T> List<T> normalizeOrder(List<T> elements) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }
        if (elements.size() == 1) {
            return elements;
        }

        final ArrayList<T> newList = new ArrayList<>(elements);

        // Normalize the direction of the elements by hashCode
        final int secondElementHashCode = newList.get(1).hashCode();
        final int lastElementHashCode = newList.get(newList.size() - 1).hashCode();
        if (secondElementHashCode >= lastElementHashCode) {
            Collections.reverse(newList);
            return normalizeFirstElement(newList);
        }
        return newList.stream().collect(Collectors.toUnmodifiableList());
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private static Optional<Integer> indexByLowestHashCode(List<?> elements) {
        final Integer lowestHashCode = elements.stream().map(Object::hashCode).reduce(Integer::min).orElse(0);
        final Optional<?> first = elements.stream().filter(element -> element.hashCode() == lowestHashCode).findFirst();
        if (first.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(elements.indexOf(first.get()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ring<?> ring = (Ring<?>) o;

        if (elements.isEmpty() && ring.elements.isEmpty()) {
            return true;
        }

        return Objects.equals(normalize(elements), normalize(ring.elements));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(elements));
    }

    public int size() {
        return elements.size();
    }

    public T get(int index) {
        final int normalizedIndex = index >= 0 ? index % elements.size() : (elements.size() + (index % elements.size())) % elements.size();
        return elements.get(normalizedIndex);
    }

    public boolean contains(T element) {
        return elements.contains(element);
    }

    public Ring<T> append(T element) {
        final ArrayList<T> elements = new ArrayList<>(this.elements);
        elements.add(element);
        return new Ring<T>(elements);
    }
}
