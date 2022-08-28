package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01NotQuiteLispTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(74, new Day01NotQuiteLisp().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(1795, new Day01NotQuiteLisp().getResultOfSecondPuzzle());
    }

    final Day01NotQuiteLisp day01 = new Day01NotQuiteLisp();

    @Test
    @ParameterizedTest
    @ValueSource(strings = { "(())", "()()" })
    void getFloorForInput_balanced(String balancedInput) {
        assertEquals(0, day01.getFloorForInput(day01.parseLine(balancedInput)));
    }

    @Test
    @ParameterizedTest
    @ValueSource(strings = { "(((", "(()(()(", "(()(()(" })
    void getFloorForInput_openingParenthesisOverweight(String openingOverweight) {
        assertEquals(3, day01.getFloorForInput(day01.parseLine(openingOverweight)));
    }

    @Test
    @ParameterizedTest
    @ValueSource(strings = { ")))", ")())())" })
    void getFloorForInput_closingParenthesisOverweight(String closingOverweight) {
        assertEquals(-3, day01.getFloorForInput(day01.parseLine(closingOverweight)));
    }

    @Test
    void getFirstPositionForFloor() {
        assertEquals(1, day01.getFirstPositionForFloor(day01.parseLine(")"), -1));
        assertEquals(5, day01.getFirstPositionForFloor(day01.parseLine("()())"), -1));
    }

}
