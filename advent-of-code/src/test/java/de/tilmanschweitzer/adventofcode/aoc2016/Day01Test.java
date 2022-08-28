package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.aoc2016.Day01.Instruction;
import de.tilmanschweitzer.adventofcode.aoc2016.Day01.Instruction.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(243, new Day01().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(142, new Day01().getResultOfSecondPuzzle());
    }

    @Test
    public void parse() {
        assertEquals(new Instruction(Direction.R, 5), Instruction.parse("R5"));
        assertEquals(new Instruction(Direction.L, 6), Instruction.parse("L6"));
    }

    @Test
    public void followPath() {
        assertEquals(5, Day01.followPath(Instruction.parse("R2", "L3")).getManhattanDistance());
        assertEquals(2, Day01.followPath(Instruction.parse("R2", "R2", "R2")).getManhattanDistance());
    }

    @Test
    public void findFirstIntersection() {
        assertEquals(4, Day01.findFirstIntersection(Instruction.parse("R8", "R4", "R4", "R8")).get().getManhattanDistance());
    }
}
