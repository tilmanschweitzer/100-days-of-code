package de.tilmanschweitzer.adventofcode.aoc2016;

import de.tilmanschweitzer.adventofcode.aoc2016.Day01.Instruction;
import de.tilmanschweitzer.adventofcode.aoc2016.Day01.Instruction.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {


    @Test
    public void parse() {
        assertEquals(new Instruction(Direction.R, 5), Instruction.parse("R5"));
        assertEquals(new Instruction(Direction.L, 6), Instruction.parse("L6"));
    }

    @Test
    public void distanceAfterSteps() {
        assertEquals(5, Day01.distance(Instruction.parse("R2", "L3")));
        assertEquals(2, Day01.distance(Instruction.parse("R2", "R2", "R2")));
    }
}
