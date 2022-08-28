package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.aoc2017.Day08.Condition;
import de.tilmanschweitzer.adventofcode.aoc2017.Day08.Instruction;
import de.tilmanschweitzer.adventofcode.aoc2017.Day08.Operation;
import de.tilmanschweitzer.adventofcode.aoc2017.Day08.Operation.OperationType;
import de.tilmanschweitzer.adventofcode.aoc2017.Day08.Registers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    @Test
    void parse() {
        final Instruction expectedResult1 = new Instruction("b", new Operation(OperationType.INC, 5), new Condition("a", Condition.CompareOperation.GREATER_THEN, 1));
        assertEquals(expectedResult1, Instruction.parse("b inc 5 if a > 1"));

        final Instruction expectedResult2 = new Instruction("l", new Operation(OperationType.INC, -913), new Condition("uz", Condition.CompareOperation.LESS_THEN_EQUALS, -4758));
        assertEquals(expectedResult2, Instruction.parse("l inc -913 if uz <= -4758"));
    }

    @Test
    void executeInstructions() {
        final String testInput = "b inc 5 if a > 1\n" +
                "a inc 1 if b < 5\n" +
                "c dec -10 if a >= 1\n" +
                "c inc -20 if c == 10";

        final List<Instruction> instructions = Arrays.stream(testInput.split("\n")).map(Instruction::parse).collect(Collectors.toUnmodifiableList());

        final Registers registers = new Registers();
        registers.execute(instructions);

        assertEquals("Day08.Registers(registers={a=1, c=-10})", registers.toString());
        assertEquals(1, registers.getLargestValueInAnyRegister());
    }
}
