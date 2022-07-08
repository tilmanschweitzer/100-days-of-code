package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day07 extends MultiLineAdventOfCodeDay<Day07.CircuitStep> {

    @Override
    public long getResultOfFirstPuzzle(final List<CircuitStep> input) {
        final Circuit circuit = new Circuit();
        input.forEach(circuit::applyStep);
        return circuit.getValueForWire("a");
    }

    @Override
    public long getResultOfSecondPuzzle(final List<CircuitStep> input) {
        return 0;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day07-input.txt");
    }

    @Override
    protected CircuitStep parseLine(String line) {
        final List<CircuitStepParser<?>> parsers = List.of(
                new ProvideValueToWire.Parser(),
                new ProvideWireToWire.Parser(),
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

    public static abstract class CircuitStep {
        public abstract void applyStepToCircuit(Circuit circuit);
    }

    public static abstract class CircuitStepParser<T extends CircuitStep> {
        public abstract boolean matches(String line);
        public abstract T parse(String line);
    }

    public static class ProvideValueToWire extends CircuitStep {
        final String wire;
        final int value;

        public ProvideValueToWire(int value, String wire) {
            this.value = value;
            this.wire = wire;
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            circuit.setValueOnWire(wire, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProvideValueToWire that = (ProvideValueToWire) o;
            return value == that.value && Objects.equals(wire, that.wire);
        }

        @Override
        public int hashCode() {
            return Objects.hash(wire, value);
        }

        @Override
        public String toString() {
            return "ProvideValueToWireStep{" +
                    "wire='" + wire + '\'' +
                    ", value=" + value +
                    '}';
        }

        public static class Parser extends CircuitStepParser<ProvideValueToWire> {
            @Override
            public boolean matches(String line) {
                return line.matches("\\d+\\s*->\\s*\\w+");
            }

            @Override
            public ProvideValueToWire parse(String line) {
                final String[] split = line.split("->");
                return new ProvideValueToWire(Integer.parseInt(split[0].trim()), split[1].trim());
            }
        }
    }

    public static class ProvideWireToWire extends CircuitStep {
        final String baseWire;
        final String targetWire;

        public ProvideWireToWire(String baseWire, String targetWire) {
            this.baseWire = baseWire;
            this.targetWire = targetWire;
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            circuit.setValueOnWire(targetWire, circuit.getValueForWire(baseWire));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProvideWireToWire that = (ProvideWireToWire) o;
            return Objects.equals(baseWire, that.baseWire) && Objects.equals(targetWire, that.targetWire);
        }

        @Override
        public int hashCode() {
            return Objects.hash(baseWire, targetWire);
        }

        public static class Parser extends CircuitStepParser<ProvideWireToWire> {
            @Override
            public boolean matches(String line) {
                return line.matches("[a-zA-Z]+\\s*->\\s*\\w+");
            }

            @Override
            public ProvideWireToWire parse(String line) {
                final String[] split = line.split("->");
                return new ProvideWireToWire(split[0].trim(), split[1].trim());
            }
        }
    }

    public static abstract class LogicGate extends CircuitStep {
        final String leftWire;
        final String rightWire;
        final String targetWire;

        public LogicGate(String leftWire, String rightWire, String targetWire) {
            this.leftWire = leftWire;
            this.rightWire = rightWire;
            this.targetWire = targetWire;
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            final Integer leftValue = circuit.getValueForWire(leftWire);
            final Integer rightValue = circuit.getValueForWire(rightWire);
            final Integer newValue = logicOperation(leftValue, rightValue);

            circuit.setValueOnWire(targetWire, newValue);
        }

        public abstract Integer logicOperation(Integer leftValue, Integer rightValue);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogicGate andGate = (LogicGate) o;
            return Objects.equals(leftWire, andGate.leftWire) && Objects.equals(rightWire, andGate.rightWire) && Objects.equals(targetWire, andGate.targetWire);
        }
        @Override
        public int hashCode() {
            return Objects.hash(leftWire, rightWire, targetWire);
        }
    }

    public static class AndGate extends LogicGate {
        public AndGate(String leftWire, String rightWire, String targetWire) {
            super(leftWire, rightWire, targetWire);
        }

        @Override
        public Integer logicOperation(Integer leftValue, Integer rightValue) {
            return leftValue & rightValue;
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
                return new AndGate(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
    }

    public static class OrGate extends LogicGate {
        public OrGate(String leftWire, String rightWire, String targetWire) {
            super(leftWire, rightWire, targetWire);
        }

        @Override
        public Integer logicOperation(Integer leftValue, Integer rightValue) {
            return leftValue | rightValue;
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
                return new OrGate(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
    }

    public static class NotGate extends LogicGate {
        public NotGate(String baseWire, String targetWire) {
            super(baseWire, "", targetWire);
        }

        @Override
        public Integer logicOperation(Integer baseValue, Integer ignored) {
            return 65536 + ~baseValue;
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

    public static abstract class ShiftGate extends CircuitStep {
        final String baseWire;
        final int shiftValue;
        final String targetWire;

        protected ShiftGate(String baseWire, int shiftValue, String targetWire) {
            this.baseWire = baseWire;
            this.shiftValue = shiftValue;
            this.targetWire = targetWire;
        }

        @Override
        public void applyStepToCircuit(Circuit circuit) {
            final int baseValue = circuit.getValueForWire(baseWire);
            final int newValue = shiftOperation(baseValue, shiftValue);
            circuit.setValueOnWire(targetWire, newValue);
        }

        public abstract int shiftOperation(int leftValue, int rightValue);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ShiftGate shiftGate = (ShiftGate) o;
            return Objects.equals(baseWire, shiftGate.baseWire) && Objects.equals(shiftValue, shiftGate.shiftValue) && Objects.equals(targetWire, shiftGate.targetWire);
        }
        @Override
        public int hashCode() {
            return Objects.hash(baseWire, shiftValue, targetWire);
        }
    }

    public static class LeftShiftGate extends ShiftGate {

        public LeftShiftGate(String baseWire, int shiftValue, String targetWire) {
            super(baseWire, shiftValue, targetWire);
        }

        @Override
        public int shiftOperation(int leftValue, int rightValue) {
            return leftValue << rightValue;
        }

        public static class Parser extends CircuitStepParser<LeftShiftGate> {
            private static final Pattern LSHIFT_PATTERN = Pattern.compile("(\\w+)\\s*LSHIFT\\s*(\\d+)\\s*->\\s*(\\w+)");
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
                return new LeftShiftGate(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
            }
        }
    }


    public static class RightShiftGate extends ShiftGate {

        public RightShiftGate(String baseWire, int shiftValue, String targetWire) {
            super(baseWire, shiftValue, targetWire);
        }

        @Override
        public int shiftOperation(int leftValue, int rightValue) {
            return leftValue >> rightValue;
        }

        public static class Parser extends CircuitStepParser<RightShiftGate> {
            private static final Pattern RSHIFT_PATTERN = Pattern.compile("(\\w+)\\s*RSHIFT\\s*(\\d+)\\s*->\\s*(\\w+)");
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
                return new RightShiftGate(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
            }
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

        public void setValueOnWire(String wire, int value) {
            wires.put(wire, value);
        }

        public Integer getValueForWire(String wire) {
            if (!wires.containsKey(wire)) {
                throw new RuntimeException("Now value for wire: " + wire);
            }
            return wires.get(wire);
        }
    }
}
