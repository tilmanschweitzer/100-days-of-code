package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day01NotQuiteLispTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(74, new Day01NotQuiteLisp().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(1795, new Day01NotQuiteLisp().getResultOfSecondPuzzle());
    }

    final Day01NotQuiteLisp day01 = new Day01NotQuiteLisp();

    @Test
    public void getFloorForInput() {
        assertEquals(0, day01.getFloorForInput(day01.parseLine("(())")));
        assertEquals(0, day01.getFloorForInput(day01.parseLine("()()")));
        assertEquals(3, day01.getFloorForInput(day01.parseLine("(((")));
        assertEquals(3, day01.getFloorForInput(day01.parseLine("(()(()(")));
        assertEquals(3, day01.getFloorForInput(day01.parseLine("))(((((")));
        assertEquals(-1, day01.getFloorForInput(day01.parseLine("())")));
        assertEquals(-1, day01.getFloorForInput(day01.parseLine("))(")));
        assertEquals(-3, day01.getFloorForInput(day01.parseLine(")))")));
        assertEquals(-3, day01.getFloorForInput(day01.parseLine(")())())")));
    }

    @Test
    public void getFirstPositionForFloor() {
        assertEquals(1, day01.getFirstPositionForFloor(day01.parseLine(")"), -1));
        assertEquals(5, day01.getFirstPositionForFloor(day01.parseLine("()())"), -1));
    }

}