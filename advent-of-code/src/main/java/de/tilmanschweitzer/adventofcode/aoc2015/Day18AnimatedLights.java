package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day18AnimatedLights extends MultiLineAdventOfCodeDay<String, Long> {

    public Day18AnimatedLights() {
        super(2015, 18);
    }


    @Override
    protected Long getResultOfFirstPuzzle(List<String> input) {
        AnimatedLightGrid currentGrid = AnimatedLightGrid.parse(input);
        for (int i = 0; i < 100; i++) {
            currentGrid = currentGrid.nextGrid();
        }

        return currentGrid.count();
    }

    @Override
    protected Long getResultOfSecondPuzzle(List<String> input) {
        AnimatedLightGrid currentGrid = AnimatedLightGrid.parse(input);
        currentGrid.turnOnCorners();;
        for (int i = 0; i < 100; i++) {
            currentGrid = currentGrid.nextGrid();
            currentGrid.turnOnCorners();;
        }
        return currentGrid.count();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day18-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    public static class AnimatedLightGrid extends AbstractAnimatedLightGrid<Boolean> {

        public AnimatedLightGrid(int gridSize) {
            super(gridSize);
        }

        @Override
        public Boolean initialValue() {
            return false;
        }

        public static AnimatedLightGrid parse(String lines) {
            return parse(Arrays.stream(lines.split("\n")).collect(Collectors.toUnmodifiableList()));
        }

        public static AnimatedLightGrid parse(List<String> lines) {
            int gridSize = lines.size();
            final AnimatedLightGrid animatedLightGrid = new AnimatedLightGrid(gridSize);

            animatedLightGrid.forEachCoordinate(lightCoordinate -> {
                final char c = lines.get(lightCoordinate.getY()).charAt(lightCoordinate.getX());
                if (c == '#') {
                    animatedLightGrid.turnOn(lightCoordinate);
                }
            });

            return animatedLightGrid;
        }

        public void turnOn(LightCoordinate lightCoordinate) {
            lightGrid.put(lightCoordinate, true);
        }

        public void turnOnCorners() {
            int lastIndex = gridSize - 1;
            turnOn(LightCoordinate.of(0, 0));
            turnOn(LightCoordinate.of(0, lastIndex));
            turnOn(LightCoordinate.of(lastIndex, 0));
            turnOn(LightCoordinate.of(lastIndex, lastIndex));
        }

        @Override
        protected String getDisplayStringForCoordinate(LightCoordinate lightCoordinate) {
            return lightGrid.get(lightCoordinate) ? "#" : ".";
        }

        @Override
        public long count() {
            return allCoordinates().stream().filter(this::isOn).count();
        }

        public int countNeighbors(LightCoordinate lightCoordinate, boolean state) {
            return (int) getNeighbors(lightCoordinate).stream().filter(neighbor -> lightGrid.get(neighbor) == state).count();
        }
        public Set<LightCoordinate> getNeighbors(int x, int y) {
            return getNeighbors(LightCoordinate.of(x, y));
        }

        public Set<LightCoordinate> getNeighbors(LightCoordinate lightCoordinate) {
            return IntStream.range(0, 9).boxed().map(index -> {
                int x = lightCoordinate.getX() - 1 + index % 3;
                int y = lightCoordinate.getY() - 1 + index / 3;
                return new LightCoordinate(x, y);
            }).filter(neighborCoordinate -> lightGrid.containsKey(neighborCoordinate) && !lightCoordinate.equals(neighborCoordinate))
                    .collect(Collectors.toUnmodifiableSet());
        }

        public boolean isOn(LightCoordinate lightCoordinate) {
            return lightGrid.get(lightCoordinate);
        }

        public AnimatedLightGrid nextGrid() {
            final AnimatedLightGrid nextGrid = new AnimatedLightGrid(gridSize);

            forEachCoordinate(lightCoordinate -> {
                final int turnedOnNeighbors = countNeighbors(lightCoordinate, true);
                if (isOn(lightCoordinate)) {
                    if (turnedOnNeighbors == 2 || turnedOnNeighbors == 3) {
                        nextGrid.turnOn(lightCoordinate);
                    }
                } else {
                    if (turnedOnNeighbors == 3) {
                        nextGrid.turnOn(lightCoordinate);
                    }
                }
            });
            return nextGrid;
        }
    }

    public static class LightCoordinate extends Coordinate {
        public LightCoordinate(int x, int y) {
            super(x, y);
        }

        public static LightCoordinate of(int x, int y) {
            return new LightCoordinate(x, y);
        }
    }

    @EqualsAndHashCode
    public static abstract class AbstractAnimatedLightGrid<T> {
        final Map<LightCoordinate, T> lightGrid;
        final int gridSize;

        public AbstractAnimatedLightGrid(int gridSize) {
            this.gridSize = gridSize;
            this.lightGrid = new Hashtable<>();
            forEachCoordinate(lightCoordinate -> lightGrid.put(lightCoordinate, initialValue()));
        }

        public void forEachCoordinate(Consumer<LightCoordinate> coordinateConsumer) {
            allCoordinates().forEach(coordinateConsumer);
        }

        public Set<LightCoordinate> allCoordinates() {
            return IntStream.range(0, gridSize * gridSize)
                    .boxed()
                    .map(index -> new LightCoordinate(index / gridSize, index % gridSize))
                    .collect(Collectors.toSet());
        }

        @Override
        public String toString() {
            return IntStream.range(0, gridSize).boxed()
                    .map(row-> IntStream.range(0, gridSize).boxed()
                            .map(col-> getDisplayStringForCoordinate(new LightCoordinate(col, row)))
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining("\n"));
        }

        public abstract T initialValue();

        protected abstract String getDisplayStringForCoordinate(LightCoordinate lightCoordinate);

        public abstract long count();
    }

}
