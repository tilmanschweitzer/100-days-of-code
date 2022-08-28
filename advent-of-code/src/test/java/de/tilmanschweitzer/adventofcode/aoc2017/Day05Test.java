package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.aoc2017.Day05.AbstractJumpInstruction;
import de.tilmanschweitzer.adventofcode.aoc2017.Day05.EventStrangerJumpInstruction;
import de.tilmanschweitzer.adventofcode.aoc2017.Day05.StrangeJumpInstruction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(378980, new Day05().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(26889114, new Day05().getResultOfSecondPuzzle());
    }

    @Test
    void countStepsToExitWithStrangeJumpInstruction() {
        final List<AbstractJumpInstruction> instructions = Stream.of(0, 3, 0, 1, -3).map(StrangeJumpInstruction::new).collect(Collectors.toList());
        assertEquals(5, Day05.countStepsToExit(instructions));
    }

    @Test
    void countStepsToExitWithEventStrangerJumpInstruction() {
        final List<AbstractJumpInstruction> instructions = Stream.of(0, 3, 0, 1, -3).map(EventStrangerJumpInstruction::new).collect(Collectors.toList());
        assertEquals(10, Day05.countStepsToExit(instructions));
    }

}
