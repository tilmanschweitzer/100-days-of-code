package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day07.*;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day07 extends MultiLineAdventOfCodeDay<CircuitStep, Integer> {

    @Override
    public Integer getResultOfFirstPuzzle(final List<CircuitStep> circuitSteps) {
        final Circuit circuit = new Circuit();
        circuit.applySteps(circuitSteps);
        return circuit.getValueForWire("a");
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<CircuitStep> inputCircuitSteps) {
        final ArrayList<CircuitStep> circuitSteps = new ArrayList<>(inputCircuitSteps);
        final Circuit circuit = new Circuit();

        // Overwrite wire b with value from first puzzle
        final int resultOfFirstPuzzle = (int) getResultOfFirstPuzzle(inputCircuitSteps);
        circuitSteps.add(new ProvideValueToWire(resultOfFirstPuzzle, "b"));

        circuit.applySteps(circuitSteps);
        return circuit.getValueForWire("a");
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day07-input.txt");
    }

    @Override
    protected CircuitStep parseLine(String line) {
        final List<CircuitStepParser<?>> parsers = List.of(
                new ProvideValueToWire.Parser(),
                new AndGate.Parser(),
                new OrGate.Parser(),
                new LeftShiftGate.Parser(),
                new RightShiftGate.Parser(),
                new NotGate.Parser()
        );

        for (CircuitStepParser<?> parser : parsers) {
            if (parser.matches(line)) {
                return parser.parse(line);
            }
        }

        throw new RuntimeException("Unknown circuit step: " + line);
    }

    public interface ValueProvider {
        boolean isResolved(Circuit circuit);
        int value(Circuit circuit);
    }

    public static class ValueProviderParser {

        public static ValueProvider parse(String input) {
            if (input.matches("\\d+")) {
                return new NumericValueProvider(Integer.parseInt(input));
            }
            if (input.matches("[a-zA-Z]+")) {
                return new WireValueProvider(input);
            }
            throw new RuntimeException("Could not parse value: " + input);
        }
    }

    @EqualsAndHashCode
    @ToString
    public static class NumericValueProvider implements ValueProvider {

        private final int value;

        public NumericValueProvider(int value) {
            this.value = value;
        }

        @Override
        public boolean isResolved(Circuit circuit) {
            return true;
        }

        @Override
        public int value(Circuit circuit) {
            return value;
        }
    }

    @EqualsAndHashCode
    @ToString
    public static class WireValueProvider implements ValueProvider {
        private final String wire;

        public WireValueProvider(String wire) {
            this.wire = wire;
        }

        @Override
        public boolean isResolved(Circuit circuit) {
            return circuit.hasValueOnWire(wire);
        }

        @Override
        public int value(Circuit circuit) {
            return circuit.getValueForWire(wire);
        }
    }

    public static abstract class CircuitStep {
        public abstract void applyStepToCircuit(Circuit circuit);

        public abstract boolean allInputsResolved(Circuit circuit);
    }

    public static abstract class CircuitStepParser<T extends CircuitStep> {
        public abstract boolean matches(String line);
        public abstract T parse(String line);
    }

    @EqualsAndHashCode
    @ToString
    public static abstract class SingleInputCircuitStep extends CircuitStep {
        final ValueProvider input;
        final String targetWire;

        protected SingleInputCircuitStep(ValueProvider input, String targetWire) {
            this.input = input;
            this.targetWire = targetWire;
        }

        protected SingleInputCircuitStep(int input, String targetWire) {
            this.input = new NumericValueProvider(input);
            this.targetWire = targetWire;
        }

        protected SingleInputCircuitStep(String input, String targetWire) {
            this.input = new WireValueProvider(input);
            this.targetWire = targetWire;
        }

        @Override
        public boolean allInputsResolved(Circuit circuit) {
            return input.isResolved(circuit);
        }
    }

    @EqualsAndHashCode
    @ToString
    public static abstract class DoubleInputCircuitStep extends CircuitStep {
        final ValueProvider leftInput;
        final ValueProvider rightInput;
        final String targetWire;

        protected DoubleInputCircuitStep(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            this.leftInput = leftInput;
            this.rightInput = rightInput;
            this.targetWire = targetWire;
        }

        protected DoubleInputCircuitStep(String leftInput, String rightInput, String targetWire) {
            this(new WireValueProvider(leftInput), new WireValueProvider(rightInput), targetWire);
        }

        protected DoubleInputCircuitStep(int leftInput, int rightInput, String targetWire) {
            this(new NumericValueProvider(leftInput), new NumericValueProvider(rightInput), targetWire);
        }

        @Override
        public boolean allInputsResolved(Circuit circuit) {
            return leftInput.isResolved(circuit) && rightInput.isResolved(circuit);
        }
    }

    public static class ProvideValueToWire extends SingleInputCircuitStep {
        public ProvideValueToWire(ValueProvider input, String targetWire) {
            super(input, targetWire);
        }

        public ProvideValueToWire(int input, String targetWire) {
            super(input, targetWire);
        }

        public ProvideValueToWire(String input, String targetWire) {
            super(input, targetWire);
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            circuit.setValueOnWire(targetWire, input);
        }

        public static class Parser extends CircuitStepParser<ProvideValueToWire> {
            @Override
            public boolean matches(String line) {
                return line.matches("\\w+\\s*->\\s*\\w+");
            }

            @Override
            public ProvideValueToWire parse(String line) {
                final String[] split = line.split("->");
                return new ProvideValueToWire(ValueProviderParser.parse(split[0].trim()), split[1].trim());
            }
        }
    }

    public static abstract class LogicGate extends DoubleInputCircuitStep {

        public LogicGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public LogicGate(String leftInput, String rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public LogicGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            final Integer newValue = logicOperation(leftInput, rightInput, circuit);
            circuit.setValueOnWire(targetWire, new NumericValueProvider(newValue));
        }

        public abstract Integer logicOperation(ValueProvider leftValue, ValueProvider rightValue, Circuit circuit);

    }

    public static class AndGate extends LogicGate {
        public AndGate(String leftWire, String rightWire, String targetWire) {
            super(leftWire, rightWire, targetWire);
        }

        public AndGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public AndGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public Integer logicOperation(ValueProvider leftValue, ValueProvider rightValue, Circuit circuit) {
            return leftValue.value(circuit) & rightValue.value(circuit);
        }

        public static class Parser extends CircuitStepParser<AndGate> {
            private static final Pattern AND_PATTERN = Pattern.compile("(\\w+)\\s*AND\\s*(\\w+)\\s*->\\s*(\\w+)");
            @Override
            public boolean matches(String line) {
                return AND_PATTERN.matcher(line).matches();
            }

            @Override
            public AndGate parse(String line) {
                final Matcher matcher = AND_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not parse: " + line);
                }
                return new AndGate(ValueProviderParser.parse(matcher.group(1)), ValueProviderParser.parse(matcher.group(2)), matcher.group(3));
            }
        }
    }

    public static class OrGate extends LogicGate {
        public OrGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public OrGate(String leftInput, String rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public OrGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public Integer logicOperation(ValueProvider leftValue, ValueProvider rightValue, Circuit circuit) {
            return leftValue.value(circuit) | rightValue.value(circuit);
        }

        public static class Parser extends CircuitStepParser<OrGate> {
            private static final Pattern OR_PATTERN = Pattern.compile("(\\w+)\\s*OR\\s*(\\w+)\\s*->\\s*(\\w+)");
            @Override
            public boolean matches(String line) {
                return OR_PATTERN.matcher(line).matches();
            }

            @Override
            public OrGate parse(String line) {
                final Matcher matcher = OR_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not parse: " + line);
                }
                return new OrGate(ValueProviderParser.parse(matcher.group(1)), ValueProviderParser.parse(matcher.group(2)), matcher.group(3));
            }
        }
    }

    public static class NotGate extends LogicGate {
        public NotGate(String baseWire, String targetWire) {
            super(baseWire, "", targetWire);
        }
        @Override
        public Integer logicOperation(ValueProvider baseValue, ValueProvider ignoredValue, Circuit circuit) {
            return 65536 + ~baseValue.value(circuit);

        }

        @Override
        public boolean allInputsResolved(Circuit circuit) {
            return leftInput.isResolved(circuit); // ignore right input
        }

        public static class Parser extends CircuitStepParser<NotGate> {
            private static final Pattern NOT_PATTERN = Pattern.compile("NOT\\s*(\\w+)\\s*->\\s*(\\w+)");
            @Override
            public boolean matches(String line) {
                return NOT_PATTERN.matcher(line).matches();
            }

            @Override
            public NotGate parse(String line) {
                final Matcher matcher = NOT_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not parse: " + line);
                }
                return new NotGate(matcher.group(1), matcher.group(2));
            }
        }
    }

    public static abstract class ShiftGate extends DoubleInputCircuitStep {
        public ShiftGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public ShiftGate(String leftInput, int rightInput, String targetWire) {
            super(new WireValueProvider(leftInput), new NumericValueProvider(rightInput), targetWire);
        }

        public ShiftGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            final int baseValue = leftInput.value(circuit);
            final int shiftValue = rightInput.value(circuit);
            final int newValue = shiftOperation(baseValue, shiftValue);
            circuit.setValueOnWire(targetWire, newValue);
        }

        public abstract int shiftOperation(int leftValue, int rightValue);
    }

    public static class LeftShiftGate extends ShiftGate {

        public LeftShiftGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public LeftShiftGate(String leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public LeftShiftGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public int shiftOperation(int leftValue, int rightValue) {
            return leftValue << rightValue;
        }

        public static class Parser extends CircuitStepParser<LeftShiftGate> {
            private static final Pattern LSHIFT_PATTERN = Pattern.compile("(\\w+)\\s*LSHIFT\\s*(\\w+)\\s*->\\s*(\\w+)");
            @Override
            public boolean matches(String line) {
                return LSHIFT_PATTERN.matcher(line).matches();
            }

            @Override
            public LeftShiftGate parse(String line) {
                final Matcher matcher = LSHIFT_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not parse: " + line);
                }
                final ValueProvider leftInput = ValueProviderParser.parse(matcher.group(1));
                final ValueProvider rightInput = ValueProviderParser.parse(matcher.group(2));
                return new LeftShiftGate(leftInput, rightInput, matcher.group(3));
            }
        }
    }


    public static class RightShiftGate extends ShiftGate {

        public RightShiftGate(ValueProvider leftInput, ValueProvider rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public RightShiftGate(String leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        public RightShiftGate(int leftInput, int rightInput, String targetWire) {
            super(leftInput, rightInput, targetWire);
        }

        @Override
        public int shiftOperation(int leftValue, int rightValue) {
            return leftValue >> rightValue;
        }

        public static class Parser extends CircuitStepParser<RightShiftGate> {
            private static final Pattern RSHIFT_PATTERN = Pattern.compile("(\\w+)\\s*RSHIFT\\s*(\\w+)\\s*->\\s*(\\w+)");
            @Override
            public boolean matches(String line) {
                return RSHIFT_PATTERN.matcher(line).matches();
            }

            @Override
            public RightShiftGate parse(String line) {
                final Matcher matcher = RSHIFT_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not parse: " + line);
                }
                final ValueProvider leftInput = ValueProviderParser.parse(matcher.group(1));
                final ValueProvider rightInput = ValueProviderParser.parse(matcher.group(2));
                return new RightShiftGate(leftInput, rightInput, matcher.group(3));            }
        }
    }
    public static class Circuit {

        private Map<String, Integer> wires = new HashMap<>();

        public void applyStep(CircuitStep step) {
            step.applyStepToCircuit(this);
        }

        public Map<String, Integer> getSignalsOnWire() {
            return new TreeMap<>(wires);
        }

        public void setValueOnWire(String wire, ValueProvider valueProvider) {
            setValueOnWire(wire, valueProvider.value(this));
        }

        public void setValueOnWire(String wire, int value) {
            wires.put(wire, value);
        }

        public boolean hasValueOnWire(String wire) {
            return wires.containsKey(wire);
        }

        public Integer getValueForWire(String wire) {
            return wires.get(wire);
        }

        public void applySteps(List<CircuitStep> circuitSteps) {
            List<CircuitStep> remainingCircuitSteps = circuitSteps;

            while (!remainingCircuitSteps.isEmpty()) {
                final List<CircuitStep> resolvableCircuitSteps = remainingCircuitSteps.stream().filter(circuitStep -> circuitStep.allInputsResolved(this)).collect(toUnmodifiableList());
                if (remainingCircuitSteps.isEmpty()) {
                    throw new RuntimeException("Found no resolvable circuit steps");
                }
                remainingCircuitSteps = remainingCircuitSteps.stream().filter(circuitStep -> !circuitStep.allInputsResolved(this)).collect(toUnmodifiableList());
                resolvableCircuitSteps.forEach(this::applyStep);
            }
        }
    }
}
