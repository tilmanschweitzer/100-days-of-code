package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day08 extends MultiLineAdventOfCodeDay<String> {

    @Override
    public long getResultOfFirstPuzzle(final List<String> input) {
        final Integer sumOfInputChars = input.stream().map(Day08::countInputChars).reduce(Integer::sum).orElse(0);
        final Integer sumOfParsedChars = input.stream().map(Day08::countParsedChars).reduce(Integer::sum).orElse(0);
        return sumOfInputChars - sumOfParsedChars;
    }

    @Override
    public long getResultOfSecondPuzzle(final List<String> inputCircuitSteps) {
        return 0;
    }

    public static int countInputChars(String s) {
        return s.length();
    }

    public static int countParsedChars(final String input) {
        return parseString(input).length();
    }

    public static String parseString(String input) {
        final InputParser inputParser = new InputParser(input);
        inputParser.addParserStep(new RemoveFirstQuote());
        inputParser.addParserStep(new RemoveLastQuote());
        inputParser.addParserStep(new UnescapeDoubleBackslash());
        inputParser.addParserStep(new UnescapeDoubleQuote());
        inputParser.addParserStep(new UnescapeUnicodeHexValue());
        return inputParser.parse();
    }

    private static String unicodeChar(String unicodeHex) {
        if (!unicodeHex.matches("\\\\x([\\da-fA-F]{2})")) {
            throw new RuntimeException("Unexpected unicode hex sequence: " + unicodeHex);
        }
        return new String(new byte[]{(byte) Integer.parseInt(unicodeHex.substring(2), 16)}, StandardCharsets.UTF_8);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day08-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    private static class InputParser {
        final String input;
        final List<InputParserStep> parserSteps = new ArrayList<>();
        public InputParser(String input) {
            this.input = input;
        }

        public void addParserStep(InputParserStep step) {
            parserSteps.add(step);
        }

        public String parse() {
            final StringBuilder stringBuilder = new StringBuilder();

            int inputIndex = 0;
            while (inputIndex < input.length()) {
                final char currentChar = input.charAt(inputIndex);
                final String remainingInput = input.substring(inputIndex);
                final StepParams stepParams = new StepParams(inputIndex, currentChar, remainingInput);

                final Optional<InputParserStep> stepOptional = parserSteps.stream().filter(step -> step.matches(stepParams)).findFirst();

                if (stepOptional.isPresent()) {
                    final InputParserStep step = stepOptional.get();
                    final String result = step.result(stepParams);
                    stringBuilder.append(result);
                    inputIndex += step.replacementLength(stepParams);
                } else {
                    stringBuilder.append(currentChar);
                    inputIndex++;
                }
            }
            return stringBuilder.toString();
        }
    }

    private static class StepParams {
        final int inputIndex;
        final char currentChar;
        final String remainingInput;

        private StepParams(int inputIndex, char currentChar, String remainingInput) {
            this.inputIndex = inputIndex;
            this.currentChar = currentChar;
            this.remainingInput = remainingInput;
        }
    }

    private interface InputParserStep {

        boolean matches(StepParams stepParams);

        String result(StepParams stepParams);

        int replacementLength(StepParams stepParams);
    }

    private static class RemoveFirstQuote implements InputParserStep {
        @Override
        public boolean matches(StepParams stepParams) {
            if (stepParams.inputIndex > 0) {
                return false;
            }
            if (stepParams.currentChar != '\"') {
                throw new RuntimeException("Unexpected first char: " + stepParams.currentChar);
            }
            return true;
        }

        @Override
        public String result(StepParams stepParams) {
            return "";
        }

        @Override
        public int replacementLength(StepParams stepParams) {
            return 1;
        }
    }

    private static class RemoveLastQuote implements InputParserStep {
        @Override
        public boolean matches(StepParams stepParams) {
            if (stepParams.remainingInput.length() > 1) {
                return false;
            }
            if (stepParams.currentChar != '\"') {
                throw new RuntimeException("Unexpected last char: " + stepParams.currentChar);
            }
            return true;
        }

        @Override
        public String result(StepParams stepParams) {
            return "";
        }

        @Override
        public int replacementLength(StepParams stepParams) {
            return 1;
        }
    }

    private static class UnescapeDoubleBackslash implements InputParserStep {
        @Override
        public boolean matches(StepParams stepParams) {
            return stepParams.currentChar == '\\' && stepParams.remainingInput.length() > 1 && stepParams.remainingInput.charAt(1) == '\\';
        }

        @Override
        public String result(StepParams stepParams) {
            return "\\";
        }

        @Override
        public int replacementLength(StepParams stepParams) {
            return 2;
        }
    }

    private static class UnescapeDoubleQuote implements InputParserStep {
        @Override
        public boolean matches(StepParams stepParams) {
            return stepParams.currentChar == '\\' && stepParams.remainingInput.length() > 1 && stepParams.remainingInput.charAt(1) == '\"';
        }

        @Override
        public String result(StepParams stepParams) {
            return "\"";
        }

        @Override
        public int replacementLength(StepParams stepParams) {
            return 2;
        }
    }

    private static class UnescapeUnicodeHexValue implements InputParserStep {
        @Override
        public boolean matches(StepParams stepParams) {
            return stepParams.currentChar == '\\' && stepParams.remainingInput.length() >= 4 && stepParams.remainingInput.substring(0, 4).matches("\\\\x([\\da-fA-F]{2})");
        }

        @Override
        public String result(StepParams stepParams) {
            return unicodeChar(stepParams.remainingInput.substring(0, 4));
        }

        @Override
        public int replacementLength(StepParams stepParams) {
            return 4;
        }
    }
}
