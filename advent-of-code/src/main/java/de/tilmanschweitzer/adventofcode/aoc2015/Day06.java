package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightInstruction.Instruction.*;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day06 extends MultiLineAdventOfCodeDay<Day06.LightInstruction, Long> {

    @Override
    public Long getResultOfFirstPuzzle(final List<LightInstruction> input) {
        final LightGrid lightGrid = new BasicLightGrid(1000);
        input.forEach(lightGrid::applyLightInstruction);
        return lightGrid.count();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<LightInstruction> input) {
        final LightGrid lightGrid = new BrightnessLightGrid(1000);
        input.forEach(lightGrid::applyLightInstruction);
        return lightGrid.count();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day06-input.txt");
    }

    @Override
    protected LightInstruction parseLine(String line) {
        return LightInstruction.parseLightInstruction(line);
    }

    @EqualsAndHashCode
    @ToString
    public static class LightInstruction {
        enum Instruction {
            TURN_ON("turn on"),
            TURN_OFF("turn off"),
            TOGGLE("toggle");

            final String inputText;

            Instruction(String inputText) {
                this.inputText = inputText;
            }

            public static Instruction parseInstruction(String line) {
                for (LightInstruction.Instruction instruction : LightInstruction.Instruction.values()) {
                    if (line.startsWith(instruction.inputText)) {
                        return instruction;
                    }
                }
                throw new RuntimeException("Could not parse instruction from line: " + line);
            }
        }
        final LightCoordinate startLight;
        final LightCoordinate endLight;
        final Instruction instruction;

        public LightInstruction(LightCoordinate startLightCoordinate, LightCoordinate endLightCoordinate, Instruction instruction) {
            this.startLight = startLightCoordinate;
            this.endLight = endLightCoordinate;
            this.instruction = instruction;
        }

        public LightInstruction(Pair<LightCoordinate> lightPair, Instruction instruction) {
            this(lightPair.getLeftValue(), lightPair.getRightValue(), instruction);
        }

        public static LightInstruction parseLightInstruction(String line) {
            final Instruction instruction = LightInstruction.Instruction.parseInstruction(line);
            return new LightInstruction(LightCoordinate.parseLightCoordinatePair(line.replace(instruction.inputText, "")), instruction);
        }
    }
    public static class LightCoordinate extends Coordinate {
        public LightCoordinate(int x, int y) {
            super(x, y);
        }

        private static Pair<LightCoordinate> parseLightCoordinatePair(String restOfLine) {
            final List<LightCoordinate> lightsList = Arrays.stream(restOfLine.split("through")).map(coordinateString -> {
                final List<Integer> coordinates = Arrays.stream(coordinateString.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toUnmodifiableList());

                Preconditions.checkArgument(coordinates.size() == 2);
                return new LightCoordinate(coordinates.get(0), coordinates.get(1));
            }).collect(Collectors.toUnmodifiableList());

            Preconditions.checkArgument(lightsList.size() == 2);
            return new Pair<>(lightsList.get(0), lightsList.get(1));
        }
    }

    @EqualsAndHashCode
    public static abstract class LightGrid<T> {
        final Map<LightCoordinate, T> lightGrid;
        final int gridSize;

        public LightGrid(int gridSize) {
            this.gridSize = gridSize;
            this.lightGrid = new Hashtable<>();
            IntStream.range(0, gridSize * gridSize).forEach((index) -> {
                final LightCoordinate currentCoordinate = new LightCoordinate(index / gridSize, index % gridSize);
                lightGrid.put(currentCoordinate, initialValue());
                Preconditions.checkArgument(lightGrid.containsKey(new LightCoordinate(index / gridSize, index % gridSize)));
            });
        }

        protected abstract T initialValue();


        public void applyLightInstruction(LightInstruction lightInstruction) {
            for (int x = lightInstruction.startLight.getX(); x <= lightInstruction.endLight.getX(); x++) {
                for (int y = lightInstruction.startLight.getY(); y <= lightInstruction.endLight.getY(); y++) {
                    final LightCoordinate currentLight = new LightCoordinate(x, y);
                    if (!lightGrid.containsKey(currentLight)) {
                        throw new RuntimeException(currentLight + " is not part of the grid (size: " + gridSize + ")");
                    }
                    if (lightInstruction.instruction == TURN_ON) {
                        turnOn(currentLight);
                    } else if (lightInstruction.instruction == TURN_OFF) {
                        turnOff(currentLight);
                    } else if (lightInstruction.instruction == TOGGLE){
                        toggle(currentLight);
                    } else {
                        throw new RuntimeException("Unknown light instruction: " + lightInstruction);
                    }
                }
            }
        }

        protected abstract void toggle(LightCoordinate currentLight);

        protected abstract void turnOff(LightCoordinate currentLight);

        protected abstract void turnOn(LightCoordinate currentLight);

        @Override
        public String toString() {
            return IntStream.range(0, gridSize).boxed()
                    .map(row-> IntStream.range(0, gridSize).boxed()
                            .map(col-> getDisplayStringForCoordinate(new LightCoordinate(row, col)))
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining("\n"));
        }

        protected abstract String getDisplayStringForCoordinate(LightCoordinate lightCoordinate);

        public abstract long count();
    }

    public static class BasicLightGrid extends LightGrid<Boolean> {
        public BasicLightGrid(int gridSize) {
            super(gridSize);
        }

        @Override
        protected Boolean initialValue() {
            return false;
        }

        @Override
        protected void toggle(LightCoordinate currentLight) {
            lightGrid.put(currentLight, !lightGrid.get(currentLight));
        }

        @Override
        protected void turnOff(LightCoordinate currentLight) {
            lightGrid.put(currentLight, false);
        }

        @Override
        protected void turnOn(LightCoordinate currentLight) {
            lightGrid.put(currentLight, true);
        }

        @Override
        protected String getDisplayStringForCoordinate(LightCoordinate lightCoordinate) {
            return lightGrid.get(lightCoordinate) ? "*" : " ";
        }

        @Override
        public long count() {
            return lightGrid.values().stream().filter(Boolean::booleanValue).count();
        }
    }


    public static class BrightnessLightGrid extends LightGrid<Integer> {
        public BrightnessLightGrid(int gridSize) {
            super(gridSize);
        }

        @Override
        protected Integer initialValue() {
            return 0;
        }

        @Override
        protected void toggle(LightCoordinate currentLight) {
            lightGrid.put(currentLight, lightGrid.get(currentLight) + 2);
        }

        @Override
        protected void turnOff(LightCoordinate currentLight) {
            lightGrid.put(currentLight, Math.max(lightGrid.get(currentLight) - 1, 0));
        }

        @Override
        protected void turnOn(LightCoordinate currentLight) {
            lightGrid.put(currentLight, lightGrid.get(currentLight) + 1);
        }

        @Override
        protected String getDisplayStringForCoordinate(LightCoordinate lightCoordinate) {
            final int value = lightGrid.get(lightCoordinate);
            return value == 0 ? " " : value + "";
        }

        @Override
        public long count() {
            return lightGrid.values().stream().reduce(Integer::sum).orElse(0);
        }
    }
}
