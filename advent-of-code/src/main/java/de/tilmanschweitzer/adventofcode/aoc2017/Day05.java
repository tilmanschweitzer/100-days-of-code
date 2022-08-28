package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day05 extends MultiLineAdventOfCodeDay<Integer, Long> {

    public Day05() {
        super(2017, 5);
    }

    @Override
    public Long getResultOfFirstPuzzle(final List<Integer> input) {
        return countStepsToExit(input.stream().map(StrangeJumpInstruction::new).collect(Collectors.toList()));
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<Integer> input) {
        return countStepsToExit(input.stream().map(EventStrangerJumpInstruction::new).collect(Collectors.toList()));
    }

    @Override
    public Integer parseLine(String line) {
        return Integer.parseInt(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day05-input.txt");
    }

    public static Long countStepsToExit(List<AbstractJumpInstruction> instructions) {
        return Stream.iterate(0, index -> index < instructions.size(), index -> instructions.get(index).jump(index))
                .count();
    }

    @Getter
    @AllArgsConstructor
    public abstract static class AbstractJumpInstruction {
        protected int offset;

        protected abstract Integer getAndUpdateOffset();

        public Integer jump(Integer index) {
            return index + getAndUpdateOffset();
        }
    }

    public static class StrangeJumpInstruction extends AbstractJumpInstruction {
        public StrangeJumpInstruction(int offset) {
            super(offset);
        }

        @Override
        protected Integer getAndUpdateOffset() {
            return offset++;
        }
    }

    public static class EventStrangerJumpInstruction extends AbstractJumpInstruction {
        public EventStrangerJumpInstruction(int offset) {
            super(offset);
        }

        @Override
        protected Integer getAndUpdateOffset() {
            if (offset >= 3) {
                return offset--;
            }
            return offset++;
        }
    }
}
