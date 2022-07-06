package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightInstruction.Instruction.*;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day06 extends MultiLineAdventOfCodeDay<Day06.LightInstruction> {

    @Override
    public long getResultOfFirstPuzzle(final List<LightInstruction> input) {
        final LightGrid lightGrid = new LightGrid(1000);
        input.forEach(lightGrid::applyLightInstruction);
        return lightGrid.countLights();
    }

    @Override
    public long getResultOfSecondPuzzle(final List<LightInstruction> input) {
        return 0;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day06-input.txt");
    }

    @Override
    protected LightInstruction parseLine(String line) {
        return LightInstruction.parseLightInstruction(line);
    }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LightInstruction that = (LightInstruction) o;
            return startLight.equals(that.startLight) && endLight.equals(that.endLight) && instruction == that.instruction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startLight, endLight, instruction);
        }

        @Override
        public String toString() {
            return "LightInstruction{" +
                    "startLight=" + startLight +
                    ", endLight=" + endLight +
                    ", instruction=" + instruction +
                    '}';
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

    public static class LightGrid {
        final Map<LightCoordinate, Boolean> lightGrid;
        final int gridSize;

        public LightGrid(int gridSize) {
            this.gridSize = gridSize;
            this.lightGrid = new Hashtable<>();
            IntStream.range(0, gridSize * gridSize).forEach((index) -> {
                final LightCoordinate currentCoordinate = new LightCoordinate(index / gridSize, index % gridSize);
                lightGrid.put(currentCoordinate, false);
                Preconditions.checkArgument(lightGrid.containsKey(new LightCoordinate(index / gridSize, index % gridSize)));
            });
        }

        public void applyLightInstruction(LightInstruction lightInstruction) {
            for (int x = lightInstruction.startLight.getX(); x <= lightInstruction.endLight.getX(); x++) {
                for (int y = lightInstruction.startLight.getY(); y <= lightInstruction.endLight.getY(); y++) {
                    final LightCoordinate currentLight = new LightCoordinate(x, y);
                    if (!lightGrid.containsKey(currentLight)) {
                        throw new RuntimeException(currentLight + " is not part of the grid (size: " + gridSize + ")");
                    }
                    if (lightInstruction.instruction == TURN_ON) {
                        lightGrid.put(currentLight, true);
                    } else if (lightInstruction.instruction == TURN_OFF) {
                        lightGrid.put(currentLight, false);
                    } else if (lightInstruction.instruction == TOGGLE){
                        lightGrid.put(currentLight, !lightGrid.get(currentLight));
                    } else {
                        throw new RuntimeException("Unknown light instruction: " + lightInstruction);
                    }
                }
            }
        }

        @Override
        public String toString() {
            return IntStream.range(0, gridSize).boxed()
                    .map(row-> IntStream.range(0, gridSize).boxed()
                            .map(col-> lightGrid.get(new LightCoordinate(row, col)) ? "*" : " ")
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining("\n"));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LightGrid lightGrid1 = (LightGrid) o;
            return gridSize == lightGrid1.gridSize && Objects.equals(lightGrid, lightGrid1.lightGrid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lightGrid, gridSize);
        }

        public long countLights() {
            return lightGrid.values().stream().filter(Boolean::booleanValue).count();
        }
    }
}
