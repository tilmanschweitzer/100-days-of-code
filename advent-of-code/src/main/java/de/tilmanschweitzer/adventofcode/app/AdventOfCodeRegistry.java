package de.tilmanschweitzer.adventofcode.app;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventOfCodeRegistry {

    private final Map<Integer, List<MultiLineAdventOfCodeDay<?>>> adventOfCodeDaysByYear = new HashMap<>();

    public void addDay(int year, final MultiLineAdventOfCodeDay<?> adventOfCodeDay) {
        getOrCreateInternalListForYear(year).add(adventOfCodeDay);
    }

    public List<MultiLineAdventOfCodeDay<?>> getDaysForYear(int year) {
        return getOrCreateInternalListForYear(year);
    }
    private List<MultiLineAdventOfCodeDay<?>> getOrCreateInternalListForYear(int year) {
        if (!adventOfCodeDaysByYear.containsKey(year)) {
            adventOfCodeDaysByYear.put(year, new ArrayList<>());
        }
        return adventOfCodeDaysByYear.get(year);
    }
}
