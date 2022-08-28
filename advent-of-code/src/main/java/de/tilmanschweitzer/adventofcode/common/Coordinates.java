package de.tilmanschweitzer.adventofcode.common;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Coordinates {

    public static Stream<Coordinate> coordinateStreamWithinGrid(int width, int height) {
        return coordinateStreamWithWidth(width).limit(width * height);
    }

    public static Stream<Coordinate> coordinateStreamWithWidth(int width) {
        final AtomicInteger index = new AtomicInteger();

        return Stream.generate(() -> {
            final int x = index.intValue() % width;
            final int y = index.intValue() / width;
            index.incrementAndGet();
            return new BasicCoordinate(x, y);
        });
    }
}
