package de.tilmanschweitzer.adventofcode.aoc2019;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.List;


import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day01 extends MultiLineAdventOfCodeDay<Integer> {

    @Override
    public long getResultOfFirstPuzzle(final List<Integer> inputNumbers) {
        return inputNumbers.stream().map(Day01::getFuelForMass).reduce(Long::sum).orElse(0L);
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Integer> inputNumbers) {
        return inputNumbers.stream().map(Day01::getTotalFuelForMass).reduce(Long::sum).orElse(0L);
    }


    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2019/day01-input.txt");
    }


    public static long getFuelForMass(long mass) {
        return mass / 3 - 2;
    }

    public static long getTotalFuelForMass(long mass) {
        final long fuel = getFuelForMass(mass);

        if (fuel <= 0) {
            return 0;
        }
        return fuel + getTotalFuelForMass(fuel);
    }

}
