package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.AdventOfCodeDay;

import java.util.*;

public class AdventOfCodeRegistry {

    private final Map<AdventOfCodeDay.YearAndDay, AdventOfCodeDay<?, ?>> adventOfCodeDaysByYearAndDay = new HashMap<>();

    public void addDay(final AdventOfCodeDay<?, ?> adventOfCodeDay) {
        adventOfCodeDaysByYearAndDay.put(adventOfCodeDay.getYearAndDay(), adventOfCodeDay);
    }

    public Optional<AdventOfCodeDay<?, ?>> getForYearAndDay(int year, int day) {
        final AdventOfCodeDay.YearAndDay yearAndDay = new AdventOfCodeDay.YearAndDay(year, day);
        return Optional.ofNullable(adventOfCodeDaysByYearAndDay.get(yearAndDay));
    }
}
