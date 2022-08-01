package de.tilmanschweitzer.adventofcode.common;

import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@EqualsAndHashCode
public class Triplet<T> {
    final T firstValue;
    final T secondValue;

    final T thirdValue;

    public Triplet(T firstValue, T secondValue, T thirdValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public T getSecondValue() {
        return secondValue;
    }

    public T getThirdValue() {
        return thirdValue;
    }

    public Stream<T> values() {
        return Stream.of(firstValue, secondValue, thirdValue);
    }

    @Override
    public String toString() {
        return "Triplet(" + firstValue + ", " + secondValue + ", " + thirdValue + ")";
    }
}
