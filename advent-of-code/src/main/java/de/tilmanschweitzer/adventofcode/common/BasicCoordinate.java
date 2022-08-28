package de.tilmanschweitzer.adventofcode.common;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
public class BasicCoordinate extends Coordinate {

    public BasicCoordinate(int x, int y) {
        super(x, y);
    }

    public static BasicCoordinate of(int x, int y) {
        return new BasicCoordinate(x, y);
    }

    public static BasicCoordinate of(Coordinate coordinate) {
        return new BasicCoordinate(coordinate.getX(), coordinate.getY());
    }

    public static Set<BasicCoordinate> of(int... coordinates) {
        Preconditions.checkArgument(coordinates.length % 2 == 0, "Only even number of values is allowed");
        return IntStream.range(0, coordinates.length / 2).boxed().map(index -> {
            int x = coordinates[index * 2];
            int y = coordinates[index * 2 + 1];
            return new BasicCoordinate(x, y);
        }).collect(Collectors.toUnmodifiableSet());
    }

    public Set<BasicCoordinate> getAllNeighborCoordinates() {
        return Set.of(up(), down(), right(), left(), upLeft(), upRight(), downLeft(), downRight());
    }

    public BasicCoordinate up() {
        return BasicCoordinate.of(x, y - 1);
    }

    public BasicCoordinate down() {
        return BasicCoordinate.of(x, y + 1);
    }

    public BasicCoordinate right() {
        return BasicCoordinate.of(x + 1, y);
    }

    public BasicCoordinate left() {
        return BasicCoordinate.of(x - 1, y);
    }


    public BasicCoordinate upLeft() {
        return BasicCoordinate.of(x - 1, y - 1);
    }

    public BasicCoordinate upRight() {
        return BasicCoordinate.of(x + 1, y - 1);
    }

    public BasicCoordinate downLeft() {
        return BasicCoordinate.of(x - 1, y + 1);
    }

    public BasicCoordinate downRight() {
        return BasicCoordinate.of(x + 1, y + 1);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
