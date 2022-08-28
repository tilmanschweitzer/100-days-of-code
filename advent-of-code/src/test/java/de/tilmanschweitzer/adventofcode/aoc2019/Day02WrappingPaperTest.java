package de.tilmanschweitzer.adventofcode.aoc2019;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02WrappingPaperTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(3562624, new Day02().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(8298, new Day02().getResultOfSecondPuzzle());
    }

    @Test
    public void interpret_testInput1() throws Day02.UnknownOpCodeException {
        final List<Integer> input = List.of(1, 0, 0, 0, 99);
        final List<Integer> result = Day02.runIntcode(input);
        final List<Integer> expectedResult = List.of(2, 0, 0, 0, 99);

        assertEquals(expectedResult, result);
    }

    @Test
    public void interpret_testInput2() throws Day02.UnknownOpCodeException {
        final List<Integer> input = List.of(2, 3, 0, 3, 99);
        final List<Integer> result = Day02.runIntcode(input);
        final List<Integer> expectedResult = List.of(2, 3, 0, 6, 99);

        assertEquals(expectedResult, result);
    }

    @Test
    public void interpret_testInput3() throws Day02.UnknownOpCodeException {
        final List<Integer> input = List.of(2, 4, 4, 5, 99, 0);
        final List<Integer> result = Day02.runIntcode(input);
        final List<Integer> expectedResult = List.of(2, 4, 4, 5, 99, 9801);

        assertEquals(expectedResult, result);
    }

    @Test
    public void interpret_testInput4() throws Day02.UnknownOpCodeException {
        final List<Integer> input = List.of(1, 1, 1, 4, 99, 5, 6, 0, 99);
        final List<Integer> result = Day02.runIntcode(input);
        final List<Integer> expectedResult = List.of(30, 1, 1, 4, 2, 5, 6, 0, 99);

        assertEquals(expectedResult, result);
    }


    @Test
    public void interpret_testInput5() throws Day02.UnknownOpCodeException {
        final List<Integer> input = List.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        final List<Integer> result = Day02.runIntcode(input);
        final List<Integer> expectedResult = List.of(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50);

        assertEquals(expectedResult, result);
    }
}
