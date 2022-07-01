package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.AdventOfCodeDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventOfCodeRegistry {

    private final Map<Integer, List<AdventOfCodeDay<?>>> adventOfCodeDaysByYear = new HashMap<>();

    public void addDay(int year, final AdventOfCodeDay<?> adventOfCodeDay) {
        getOrCreateInternalListForYear(year).add(adventOfCodeDay);
    }

    public List<AdventOfCodeDay<?>> getDaysForYear(int year) {
        return getOrCreateInternalListForYear(year);
    }
    private List<AdventOfCodeDay<?>> getOrCreateInternalListForYear(int year) {
        if (!adventOfCodeDaysByYear.containsKey(year)) {
            adventOfCodeDaysByYear.put(year, new ArrayList<>());
        }
        return adventOfCodeDaysByYear.get(year);
    }
}
