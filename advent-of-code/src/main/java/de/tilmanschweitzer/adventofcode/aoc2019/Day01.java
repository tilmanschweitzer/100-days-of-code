package de.tilmanschweitzer.adventofcode.aoc2019;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.List;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends MultiLineAdventOfCodeDay<Integer, Integer> {

    public Day01() {
        super(2019, 1);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return sum(inputNumbers.stream().map(Day01::getFuelForMass));
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return sum(inputNumbers.stream().map(Day01::getTotalFuelForMass));
    }

    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2019/day01-input.txt");
    }


    public static int getFuelForMass(int mass) {
        return mass / 3 - 2;
    }

    public static int getTotalFuelForMass(int mass) {
        final int fuel = getFuelForMass(mass);

        if (fuel <= 0) {
            return 0;
        }
        return fuel + getTotalFuelForMass(fuel);
    }

}
