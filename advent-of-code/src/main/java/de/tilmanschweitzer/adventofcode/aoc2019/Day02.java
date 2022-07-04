package de.tilmanschweitzer.adventofcode.aoc2019;

import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<List<Integer>, Integer> {

    private enum OpCode {
        ADD(1),
        MULTIPLY(2),
        HALT(99);
        private final int code;

        OpCode(int code) {
            this.code = code;
        }
    }

    public static List<Integer> runIntcode(List<Integer> input) throws UnknownOpCodeException {
        final Integer[] inputArray = input.toArray(new Integer[]{});

        int currentPosition = 0;

        int currentCode = inputArray[currentPosition];
        while (currentCode != OpCode.HALT.code) {
            if (currentCode == OpCode.ADD.code || currentCode == OpCode.MULTIPLY.code) {
                final int firstIndex = inputArray[currentPosition + 1];
                final int secondsIndex = inputArray[currentPosition + 2];
                final int resultIndex = inputArray[currentPosition + 3];

                if (currentCode == OpCode.ADD.code) {
                    inputArray[resultIndex] = inputArray[firstIndex] + inputArray[secondsIndex];
                } else if (currentCode == OpCode.MULTIPLY.code) {
                    inputArray[resultIndex] = inputArray[firstIndex] * inputArray[secondsIndex];
                } else {
                    throw new UnknownOpCodeException(currentCode);
                }

                currentPosition += 4;
            } else {
                throw new UnknownOpCodeException(currentCode);
            }
            currentCode = inputArray[currentPosition];
        }

        return Arrays.stream(inputArray).collect(Collectors.toList());
    }

    public static List<Integer> runIntcodeWithNounAndVerb(final List<Integer> inputNumbers, int noun, int verb) throws UnknownOpCodeException {
        inputNumbers.set(1, noun);
        inputNumbers.set(2, verb);
        return runIntcode(inputNumbers);
    }


    public static Optional<Pair<Integer>> runIntCodeAndFindMatchingNounAndVerbForExpectedResult(final List<Integer> inputNumbers, int expectedResult) {
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                final int resultAtPosition0 = runIntcodeWithNounAndVerb(inputNumbers, noun, verb).get(0);
                if (resultAtPosition0 == expectedResult) {
                    return Optional.of(new Pair(noun, verb));
                }
            }
        }
        return Optional.empty();
    }

    // 3562624
    @Override
    public Integer getResultOfFirstPuzzle(final List<List<Integer>> inputLines) {
        if (inputLines.size() != 1) {
            throw new UnexpectedNumberOfInputLinesException(inputLines.size());
        }

        try {
            final List<Integer> integers = runIntcodeWithNounAndVerb(inputLines.get(0), 12, 2);
            return integers.get(0);
        } catch (UnknownOpCodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<List<Integer>> inputLines) {
        if (inputLines.size() != 1) {
            throw new UnexpectedNumberOfInputLinesException(inputLines.size());
        }

        try {
            final Optional<Pair<Integer>> pairOfNounAndVerbOptional = runIntCodeAndFindMatchingNounAndVerbForExpectedResult(inputLines.get(0), 19690720);
            if (pairOfNounAndVerbOptional.isEmpty()) {
                return 0;
            }
            final Pair<Integer> pairOfNounAndVerb = pairOfNounAndVerbOptional.get();

            return pairOfNounAndVerb.getLeftValue() * 100 + pairOfNounAndVerb.getRightValue();
        } catch (UnknownOpCodeException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Integer> parseLine(String line) {
        return Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2019/day02-input.txt");
    }


    public static class UnknownOpCodeException extends RuntimeException {
        public UnknownOpCodeException(int currentCode) {
            super("Unknown opcode " + currentCode);
        }
    }

    private class UnexpectedNumberOfInputLinesException extends RuntimeException {
        public UnexpectedNumberOfInputLinesException(int size) {
            super("Unexpected number of input lines  " + size);

        }
    }
}
