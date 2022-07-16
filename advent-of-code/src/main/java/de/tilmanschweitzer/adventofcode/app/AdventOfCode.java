package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.AdventOfCodeDay;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.util.List;

public class AdventOfCode {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Specify year, day and puzzle of the challenge");
            System.exit(1);
        }

        final int year = Integer.parseInt(args[0]);
        final int day = Integer.parseInt(args[1]);
        final int puzzle = Integer.parseInt(args[2]);

        final AdventOfCodeRegistry registry = createRegistryWithExistingDays();

        final List<AdventOfCodeDay<?, ?>> listOfDays = registry.getDaysForYear(year);

        if (listOfDays.isEmpty()) {
            System.err.println("Year " + year + " not available");
            System.exit(1);
        }

        if (listOfDays.size() < day) {
            System.err.println("Day " + day + " not implemented for year " + year + " yet");
            System.exit(1);
        }

        final int dayIndex = day - 1;
        final AdventOfCodeDay<?, ?> selectedDay = listOfDays.get(dayIndex);

        if (puzzle == 1) {
            selectedDay.runFirstPuzzle();
        } else if (puzzle == 2) {
            selectedDay.runSecondPuzzle();
        } else {
            System.err.println("Each day has only 2 puzzles, but you tried to select puzzle " + puzzle);
            System.exit(1);
        }
    }

    private static AdventOfCodeRegistry createRegistryWithExistingDays() {
        final AdventOfCodeRegistry registry = new AdventOfCodeRegistry();

        // 2020
        registry.addDay(2020, new de.tilmanschweitzer.adventofcode.aoc2020.Day01());
        registry.addDay(2020, new de.tilmanschweitzer.adventofcode.aoc2020.Day02());
        registry.addDay(2020, new de.tilmanschweitzer.adventofcode.aoc2020.Day03());
        registry.addDay(2020, new de.tilmanschweitzer.adventofcode.aoc2020.Day04());

        // 2019
        registry.addDay(2019, new de.tilmanschweitzer.adventofcode.aoc2019.Day01());
        registry.addDay(2019, new de.tilmanschweitzer.adventofcode.aoc2019.Day02());
        registry.addDay(2019, new de.tilmanschweitzer.adventofcode.aoc2019.Day03());

        // 2018
        registry.addDay(2018, new de.tilmanschweitzer.adventofcode.aoc2018.Day01());

        // 2015
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day01());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day02());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day03());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day04());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day05());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day06());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day07());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day08());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day09());
        registry.addDay(2015, new de.tilmanschweitzer.adventofcode.aoc2015.Day10());

        return registry;
    }
}
