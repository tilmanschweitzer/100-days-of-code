package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends SingleLineAdventOfCodeDay<List<Character>, Integer> {

    public Day01() {
        super(2015, 1);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Character> inputNumbers) {
        return getFloorForInput(inputNumbers);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Character> inputNumbers) {
        return getFirstPositionForFloor(inputNumbers, -1);
    }

    @Override
    public List<Character> parseLine(String line) {
        return Arrays.stream(line.split("")).map(c -> c.charAt(0)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day01-input.txt");
    }

    public int getFloorForInput(List<Character> line) {
        return sum(line.stream().map(Day01::floorMoveForChar));
    }

    public int getFirstPositionForFloor(List<Character> line, int searchedFloor) {
        int currentFloor = 0;
        int position = 0;
        for (Character character : line) {
            currentFloor += floorMoveForChar(character);
            position++;
            if (currentFloor == searchedFloor) {
                return position;
            }
        }
        throw new RuntimeException("No position found for searched floor: " + searchedFloor);
    }

    private static int floorMoveForChar(char instruction) {
        if (instruction == '(') {
            return 1;
        }
        if (instruction == ')') {
            return -1;
        }
        throw new RuntimeException("Unexpected character: " + instruction);
    }
}
