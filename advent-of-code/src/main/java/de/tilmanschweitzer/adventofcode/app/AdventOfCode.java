package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.AdventOfCodeDay;

import java.util.Optional;
import java.util.ServiceLoader;

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

        final Optional<AdventOfCodeDay<?, ?>> selectedDayOptional = registry.getForYearAndDay(year, day);

        if (selectedDayOptional.isEmpty()) {
            System.err.println("Day " + day + " not implemented for year " + year + " yet");
            System.exit(1);
        }

        final AdventOfCodeDay<?, ?> selectedDay = selectedDayOptional.get();

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

        final ServiceLoader<AdventOfCodeDay> serviceLoader = ServiceLoader.load(AdventOfCodeDay.class);

        for (AdventOfCodeDay adventOfCodeDay : serviceLoader) {
            registry.addDay(adventOfCodeDay);
        }

        return registry;
    }
}
