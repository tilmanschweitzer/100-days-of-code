package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day06 extends SingleLineAdventOfCodeDay<List<Integer>, Integer> {

    public Day06() {
        super(2017, 6);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Integer> input) {
        return MemoryBlocks.from(input).findCombinationsUntilInfiniteLoopIsDetected().size();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Integer> input) {
        return MemoryBlocks.from(input).findNumberOfCyclesInInfinite();
    }

    @Override
    public List<Integer> parseLine(String line) {
        return Arrays.stream(line.split("\\s+")).filter(Predicate.not(String::isBlank)).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day06-input.txt");
    }

    public static class MemoryBlocks {

        final List<MemoryBlock> memoryBlocks;

        public MemoryBlocks(List<MemoryBlock> memoryBlocks) {
            this.memoryBlocks = memoryBlocks;
        }

        public static MemoryBlocks from(List<Integer> integers) {
            return new MemoryBlocks(integers.stream().map(MemoryBlock::new).collect(Collectors.toUnmodifiableList()));
        }

        public Set<List<Integer>> findCombinationsUntilInfiniteLoopIsDetected() {
            final Set<List<Integer>> existingConfigurations = new HashSet<>();

            while (!existingConfigurations.contains(currentConfiguration())) {
                existingConfigurations.add(currentConfiguration());
                redistribute();
            }

            return existingConfigurations;
        }

        public int findNumberOfCyclesInInfinite() {
            final Set<List<Integer>> existingConfigurations = new HashSet<>();
            final List<List<Integer>> orderedExistingConfigurations = new ArrayList<>();
            while (!existingConfigurations.contains(currentConfiguration())) {
                existingConfigurations.add(currentConfiguration());
                orderedExistingConfigurations.add(currentConfiguration());
                redistribute();
            }

            final int firstOccurrence = orderedExistingConfigurations.indexOf(currentConfiguration());
            return orderedExistingConfigurations.size() - firstOccurrence;
        }

        public List<Integer> currentConfiguration() {
            return memoryBlocks.stream().map(MemoryBlock::getValue).collect(Collectors.toUnmodifiableList());
        }

        public void redistribute() {
            final List<Integer> currentConfiguration = currentConfiguration();
            final int maxValue = Collections.max(currentConfiguration);
            final int indexOfMaxValue = currentConfiguration.indexOf(maxValue);
            final int baseValueForRedistribution = maxValue / memoryBlocks.size();
            final int restValueForRedistribution = maxValue % memoryBlocks.size();

            memoryBlocks.get(indexOfMaxValue).reset();

            for (int i = 1; i <= memoryBlocks.size(); i++) {
                final int redistributionIndex = (indexOfMaxValue + i) % memoryBlocks.size();
                final int redistributionValue;
                if (i <= restValueForRedistribution) {
                    redistributionValue = baseValueForRedistribution + 1;
                } else {
                    redistributionValue = baseValueForRedistribution;
                }
                memoryBlocks.get(redistributionIndex).add(redistributionValue);
            }

        }
    }


    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class MemoryBlock {
        protected int value;

        public void reset() {
            value = 0;
        }

        public void add(int value) {
            this.value += value;
        }
    }

}
