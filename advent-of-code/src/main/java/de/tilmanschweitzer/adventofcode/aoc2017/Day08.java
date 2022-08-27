package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.ToString;
import lombok.Value;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day08 extends MultiLineAdventOfCodeDay<Day08.Instruction, Integer> {

    public Day08() {
        super(2017, 8);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Instruction> input) {
        final Registers registers = new Registers();
        registers.execute(input);
        return registers.getLargestValueInAnyRegister();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Instruction> input) {
        final Registers registers = new Registers();
        registers.execute(input);
        return registers.getHighestRegisteredValue();
    }

    @Override
    public Instruction parseLine(String line) {
        return Instruction.parse(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day08-input.txt");
    }

    @Value
    public static class Condition {
        String register;
        CompareOperation compareOperation;
        int value;

        public boolean check(Registers registers) {
            return compareOperation.function.apply(registers.getRegisterValue(register), value);
        }

        enum CompareOperation {
            EQUALS("==", (a, b) -> a.equals(b)),
            NOT_EQUALS("!=", (a, b) -> !a.equals(b)),
            LESS_THEN("<", (a, b) -> a.compareTo(b) < 0),
            GREATER_THEN(">", (a, b) -> a.compareTo(b) > 0),
            LESS_THEN_EQUALS("<=", (a, b) -> a.compareTo(b) <= 0),
            GREATER_THEN_EQUALS(">=", (a, b) -> a.compareTo(b) >= 0);

            static Map<String, CompareOperation> operationByRepresentation = Arrays.stream(CompareOperation.values())
                    .collect(Collectors.toMap(CompareOperation::getRepresentation, Function.identity()));

            final String representation;
            final BiFunction<Integer, Integer, Boolean> function;

            public String getRepresentation() {
                return representation;
            }

            CompareOperation(String representation, BiFunction<Integer, Integer, Boolean> function) {
                this.representation = representation;
                this.function = function;
            }
        }
    }

    @Value
    public static class Operation  {
        OperationType type;
        int value;

        public int execute(int currentValue) {
            if (type == OperationType.INC) {
                return currentValue + value;
            }
            if (type == OperationType.DEC) {
                return currentValue - value;
            }
            throw new RuntimeException("Unknoen operation type: " + type);
        }

        enum OperationType {
            INC, DEC
        }
    }

    @ToString
    public static class Registers {
        public static int DEFAULT_VALUE = 0;

        int highestRegisteredValue = DEFAULT_VALUE;

        final Map<String, Integer> registers = new HashMap<>();

        public Registers() {
            registers.put("a", DEFAULT_VALUE);
        }


        public int getHighestRegisteredValue() {
            return highestRegisteredValue;
        }

        public int getRegisterValue(String registerName) {
            if (!registers.containsKey(registerName)) {
                return 0;
            }
            return registers.get(registerName);
        }

        private void updateRegisterValue(String registerName, int value) {
            registers.put(registerName, value);
            if (value > highestRegisteredValue) {
                highestRegisteredValue = value;
            }
        }

        public void execute(Instruction instruction) {
            if (instruction.condition.check(this)) {
                final int registerValue = getRegisterValue(instruction.getRegister());
                final int newRegisterValue = instruction.operation.execute(registerValue);
                updateRegisterValue(instruction.getRegister(), newRegisterValue);
            }
        }

        public void execute(List<Instruction> instructions) {
            instructions.forEach(this::execute);
        }

        public Integer getLargestValueInAnyRegister() {
            return registers.values().stream().reduce(Integer::max).orElse(DEFAULT_VALUE);
        }
    }

    @Value
    public static class Instruction {
        String register;
        Operation operation;
        Condition condition;

        public static Instruction parse(String line) {
            final Pattern towerDescriptionPattern = Pattern.compile("(\\w+) (\\w+) (-?\\d+) if (\\w+) ([<>=!]+) (-?\\d+)");

            final Matcher matcher = towerDescriptionPattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse '" + line + "'");
            }

            String register = matcher.group(1);
            Operation.OperationType operationType = Operation.OperationType.valueOf(matcher.group(2).toUpperCase());
            int operationValue = Integer.parseInt(matcher.group(3));
            Operation operation = new Operation(operationType, operationValue);

            String conditionRegister = matcher.group(4);
            Condition.CompareOperation compareOperation = Condition.CompareOperation.operationByRepresentation.get(matcher.group(5));
            int conditionValue = Integer.parseInt(matcher.group(6));
            Condition condition = new Condition(conditionRegister, compareOperation, conditionValue);


            return new Instruction(register, operation, condition);
        }
    }
}
