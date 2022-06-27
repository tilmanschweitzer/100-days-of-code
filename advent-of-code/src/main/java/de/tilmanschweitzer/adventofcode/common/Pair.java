package de.tilmanschweitzer.adventofcode.common;

import de.tilmanschweitzer.adventofcode.aoc2020.Day01;

import java.util.Objects;

public class Pair<T> {
    final T leftValue;
    final T rightValue;

    public Pair(T leftValue, T rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public T getLeftValue() {
        return leftValue;
    }

    public T getRightValue() {
        return rightValue;
    }

    @Override
    public String toString() {
        return "Pair(" + leftValue + ", " + rightValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?> pair = (Pair<?>) o;
        return Objects.equals(leftValue, pair.leftValue) && Objects.equals(rightValue, pair.rightValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftValue, rightValue);
    }
}
