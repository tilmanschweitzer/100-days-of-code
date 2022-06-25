package de.tilmanschweitzer.adventofcode.aoc2020.app;

import java.util.List;

public class AdventOfCode {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Specify day and puzzle of the challenge");
            System.exit(1);
        }

        final int day = Integer.parseInt(args[0]);
        final int puzzle = Integer.parseInt(args[1]);

        final List<AdventOfCodeDay<?>> listOfDays = List.of(
                new Day01(),
                new Day02(),
                new Day03(),
                new Day04()
        );

        if (listOfDays.size() < day) {
            System.err.println("Day " + day + " not implemented yet");
            System.exit(1);
        }

        final int dayIndex = day - 1;
        final AdventOfCodeDay<?> selectedDay = listOfDays.get(dayIndex);

        if (puzzle == 1) {
            selectedDay.runFirstPuzzle();
        } else if (puzzle == 2) {
            selectedDay.runSecondPuzzle();
        } else {
            System.err.println("Each day has only 2 puzzles, but you tried to select puzzle " + puzzle);
            System.exit(1);
        }
    }
}
