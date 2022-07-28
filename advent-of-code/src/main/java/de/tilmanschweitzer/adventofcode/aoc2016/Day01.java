package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends SingleLineAdventOfCodeDay<List<Day01.Instruction>, Integer> {

    public Day01() {
        super(2016, 1);
    }

    public Integer getResultOfFirstPuzzle(final List<Instruction> instructions) {
        return distance(instructions);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Instruction> instructions) {
        return 0;
    }

    public static int distance(List<Instruction> instructions) {
        EasterBunnyHQCoordinates easterBunnyHQCoordinates = new EasterBunnyHQCoordinates(0,0);
        Instruction.Orientation currentOrientation = Instruction.Orientation.NORTH;


        for (Instruction instruction : instructions) {
            currentOrientation = currentOrientation.turn(instruction.direction);
            easterBunnyHQCoordinates = easterBunnyHQCoordinates.move(currentOrientation, instruction.steps);
        }

        return easterBunnyHQCoordinates.getManhattanDistance();
    }

    @Override
    public List<Instruction> parseLine(String line) {
        return Arrays.stream(line.split("\\s*,\\s*")).map(Instruction::parse).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day01-input.txt");
    }


    @EqualsAndHashCode
    @ToString
    public static class EasterBunnyHQCoordinates extends Coordinate {
        public EasterBunnyHQCoordinates(int x, int y) {
            super(x, y);
        }

        public EasterBunnyHQCoordinates move(Instruction.Orientation orientation, int steps) {
            final int x = this.x + steps * orientation.x;
            final int y = this.y + steps * orientation.y;
            return new EasterBunnyHQCoordinates(x, y);
        }
    }

    @EqualsAndHashCode
    @ToString
    public static class Instruction {
        public enum Direction {
            R, L
        }

        @Getter
        public enum Orientation {
            NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

            private final int x;
            private final int y;

            Orientation(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public Orientation turn(Direction direction) {
                int ordinalDirection = direction == Direction.R ? 1 : -1;
                int nextOrdinal = (this.ordinal() + ordinalDirection + Orientation.values().length) % Orientation.values().length;
                return Orientation.values()[nextOrdinal];
            }
        }

        private final Direction direction;
        private final int steps;

        public Instruction(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }

        public static Instruction parse(String instruction) {
            final Direction direction = Direction.valueOf(instruction.substring(0, 1));
            final int steps = Integer.parseInt(instruction.substring(1));
            return new Instruction(direction, steps);
        }

        public static List<Instruction> parse(String... instructions) {
            return Arrays.stream(instructions).map(Instruction::parse).collect(Collectors.toUnmodifiableList());
        }
    }

}
