package de.tilmanschweitzer.adventofcode.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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

}
