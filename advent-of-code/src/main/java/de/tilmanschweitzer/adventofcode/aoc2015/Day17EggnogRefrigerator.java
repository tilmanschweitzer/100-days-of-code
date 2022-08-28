package de.tilmanschweitzer.adventofcode.aoc2015;


import de.tilmanschweitzer.adventofcode.common.combination.UnorderedCombinations;
import de.tilmanschweitzer.adventofcode.common.combination.validator.SumValidator;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day17EggnogRefrigerator extends MultiLineAdventOfCodeDay<Day17EggnogRefrigerator.Refrigerator, Integer> {

    public Day17EggnogRefrigerator() {
        super(2015, 17);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Refrigerator> refrigerators) {
        final Set<Set<Refrigerator>> combinations = findCombinations(150, refrigerators);
        return combinations.size();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Refrigerator> refrigerators) {
        final Set<Set<Refrigerator>> combinations = findCombinations(150, refrigerators);
        final Integer minNumberOfContainers = combinations.stream().map(Set::size).reduce(Integer::min).orElse(Integer.MAX_VALUE);
        final Set<Set<Refrigerator>> minimalCombinations = combinations.stream().filter(containers -> containers.size() == minNumberOfContainers).collect(Collectors.toUnmodifiableSet());
        return minimalCombinations.size();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day17-input.txt");
    }

    @Override
    protected Refrigerator parseLine(String line) {
        return Refrigerator.of(Integer.parseInt(line));
    }

    public static Set<Set<Refrigerator>> findCombinations(int liters, List<Refrigerator> refrigerators) {
        return UnorderedCombinations.allCombinationsAsSet(refrigerators, new SumValidator<>(liters, Refrigerator::getSize));
    }

    @EqualsAndHashCode
    @Getter
    public static class Refrigerator {
        private static int latestId = 0;

        private final int size;
        private final int id;

        private Refrigerator(int size) {
            this.size = size;
            this.id = ++latestId;
        }

        public static Refrigerator of(int size) {
            return new Refrigerator(size);
        }

        @Override
        public String toString() {
            return size +
                    " (id:" + id +
                    ')';
        }
    }

}
