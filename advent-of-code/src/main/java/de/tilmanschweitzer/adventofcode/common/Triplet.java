package de.tilmanschweitzer.adventofcode.common;

import de.tilmanschweitzer.adventofcode.aoc2020.Day01;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Triplet(" + firstValue + ", " + secondValue + ", " + thirdValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triplet<?> pair = (Triplet<?>) o;
        return Objects.equals(firstValue, pair.firstValue) && Objects.equals(secondValue, pair.secondValue) && Objects.equals(thirdValue, pair.thirdValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstValue, secondValue, thirdValue);
    }
}
