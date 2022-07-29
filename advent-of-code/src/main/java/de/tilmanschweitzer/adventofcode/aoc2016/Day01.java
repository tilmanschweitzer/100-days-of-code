package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends SingleLineAdventOfCodeDay<List<Day01.Instruction>, Integer> {

    public Day01() {
        super(2016, 1);
    }

    public Integer getResultOfFirstPuzzle(final List<Instruction> instructions) {
        return followPath(instructions).getManhattanDistance();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Instruction> instructions) {
        return findFirstIntersection(instructions).get().getManhattanDistance();
    }

    public static Position followPath(List<Instruction> instructions) {
        Position position = new Position(0,0);
        Instruction.Orientation currentOrientation = Instruction.Orientation.NORTH;

        for (Instruction instruction : instructions) {
            currentOrientation = currentOrientation.turn(instruction.direction);
            position = position.move(currentOrientation, instruction.steps);
        }

        return position;
    }

    public static Optional<Position> findFirstIntersection(List<Instruction> instructions) {
        Set<Position> allPositions = new HashSet<>();
        Position currentPosition = new Position(0,0);
        allPositions.add(currentPosition);

        Instruction.Orientation currentOrientation = Instruction.Orientation.NORTH;

        for (Instruction instruction : instructions) {
            currentOrientation = currentOrientation.turn(instruction.direction);
            for (int i = 0; i < instruction.steps; i++) {
                currentPosition = currentPosition.move(currentOrientation, 1);
                if (allPositions.contains(currentPosition)) {
                    return Optional.of(currentPosition);
                }
                allPositions.add(currentPosition);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Instruction> parseLine(String line) {
        return Arrays.stream(line.split("\\s*,\\s*")).map(Instruction::parse).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day01-input.txt");
    }


    public static class Position extends Coordinate {
        public Position(int x, int y) {
            super(x, y);
        }

        public Position move(Instruction.Orientation orientation, int steps) {
            final int x = this.x + steps * orientation.x;
            final int y = this.y + steps * orientation.y;
            return new Position(x, y);
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
