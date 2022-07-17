package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.collect.Sets;
import de.tilmanschweitzer.adventofcode.common.Coordinate;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day03 extends SingleLineAdventOfCodeDay<List<Day03.Move>, Integer> {

    public Day03() {
        super(2015, 3);
    }

    public static int numberOfHousesFromMoves(final List<Move> moves) {
        return housesFromMoves(moves).size();
    }

    public static int numberOfHousesFromMovesFromSantaAndRoboSanta(final List<Move> moves) {
        // Santa only moves the instructions with even index
        final List<Move> santaMoves = IntStream.range(0, moves.size()).boxed()
                .filter(index -> index % 2 == 0)
                .map(moves::get)
                .collect(Collectors.toUnmodifiableList());

        // RoboSanta only moves the instructions with odd index
        final List<Move> roboSantaMoves = IntStream.range(0, moves.size()).boxed()
                .filter(index -> index % 2 == 1)
                .map(moves::get)
                .collect(Collectors.toUnmodifiableList());

        final Set<House> santaHouses = housesFromMoves(santaMoves);
        final Set<House> roboSantaHouses = housesFromMoves(roboSantaMoves);

        return Sets.union(santaHouses, roboSantaHouses).size();
    }

    public static Set<House> housesFromMoves(final List<Move> moves) {
        final Set<House> houses = new HashSet<>();
        House currentHouse = new House(0, 0);
        houses.add(currentHouse);
        for (final Move move : moves) {
            final House nextHouse = currentHouse.nextHouse(move);
            houses.add(nextHouse);
            currentHouse = nextHouse;
        }
        return houses;
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Move> moves) {
        return numberOfHousesFromMoves(moves);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Move> moves) {
        return numberOfHousesFromMovesFromSantaAndRoboSanta(moves);
    }

    @Override
    public List<Move> parseLine(String line) {
        return Arrays.stream(line.split("")).map(c -> c.charAt(0)).map(Move::fromInput).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day03-input.txt");
    }


    public static class House extends Coordinate {

        public House(int x, int y) {
            super(x, y);
        }

        public House nextHouse(Move move) {
            return new House(this.x + move.getX(), this.y + move.getY());
        }
    }

    public static class Move extends Coordinate {
        public static final Move NORTH = new Move(0, 1);
        public static final Move SOUTH = new Move(0, -1);
        public static final Move WEST = new Move(-1, 0);
        public static final Move EAST = new Move(1, 0);

        public Move(int x, int y) {
            super(x, y);
        }

        public static Move fromInput(char input) {
            if (input == '^') {
                return NORTH;
            }
            if (input == 'v') {
                return SOUTH;
            }
            if (input == '>') {
                return EAST;
            }
            if (input == '<') {
                return WEST;
            }
            throw new RuntimeException("Unexpected move input: " + input);
        }

        public static List<Move> fromInput(String input) {
            return IntStream.range(0, input.length()).boxed()
                    .map(index -> input.charAt(index))
                    .map(Move::fromInput)
                    .collect(Collectors.toUnmodifiableList());
        }
    }

}
