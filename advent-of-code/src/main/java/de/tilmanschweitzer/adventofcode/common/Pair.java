package de.tilmanschweitzer.adventofcode.common;

import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
public class Pair<T> {
    final T leftValue;
    final T rightValue;

    public Pair(T leftValue, T rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public static <T> Pair<T> of(T leftValue, T rightValue) {
        return new Pair<>(leftValue, rightValue);
    }

    public static <T> Pair<T>  of(Stream<T> valueStream) {
        final List<T> values = valueStream.collect(Collectors.toUnmodifiableList());
        if (values.size() != 2) {
            throw new IllegalArgumentException("Pair must be created from 2 values but got " + values.size() + " values");
        }
        return new Pair<>(values.get(0), values.get(1));
    }
    public T getLeftValue() {
        return leftValue;
    }

    public T getRightValue() {
        return rightValue;
    }


    public Stream<T> values() {
        return Stream.of(leftValue, rightValue);
    }

    @Override
    public String toString() {
        return "Pair(" + leftValue + ", " + rightValue + ")";
    }

}
