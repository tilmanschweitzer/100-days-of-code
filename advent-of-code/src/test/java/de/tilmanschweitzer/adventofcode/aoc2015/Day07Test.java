package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day07.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    @Test
    public void testProvideValueToWireStepParser_matches() {
        final CircuitStepParser<?> parser = new ProvideValueToWire.Parser();
        assertTrue(parser.matches("123 -> x"));
        assertTrue(parser.matches("456 -> y"));

        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testProvideValueToWireStepParser_parse() {
        final CircuitStepParser<?> parser = new ProvideValueToWire.Parser();
        assertEquals(new ProvideValueToWire(123, "x"), parser.parse("123 -> x"));
        assertEquals(new ProvideValueToWire(456, "y"), parser.parse("456 -> y"));
    }

    @Test
    public void testProvideWireToWireStepParser_matches() {
        final CircuitStepParser<?> parser = new ProvideWireToWire.Parser();
        assertTrue(parser.matches("lx -> a"));
        assertTrue(parser.matches("i -> j"));

        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testProvideWireToWireStepParser_parse() {
        final CircuitStepParser<?> parser = new ProvideWireToWire.Parser();
        assertEquals(new ProvideWireToWire("lx", "a"), parser.parse("lx -> a"));
        assertEquals(new ProvideWireToWire("i", "j"), parser.parse("i -> j"));
    }

    @Test
    public void testAndGateParser_matches() {
        final CircuitStepParser<?> parser = new AndGate.Parser();
        assertTrue(parser.matches("x AND y -> d"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testAndGateParser_parse() {
        final CircuitStepParser<?> parser = new AndGate.Parser();
        assertEquals(new AndGate("x", "y", "d"), parser.parse("x AND y -> d"));
    }

    @Test
    public void testOrGateParser_matches() {
        final CircuitStepParser<?> parser = new OrGate.Parser();
        assertTrue(parser.matches("x OR y -> e"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testOrGateParser_parse() {
        final CircuitStepParser<?> parser = new OrGate.Parser();
        assertEquals(new OrGate("x", "y", "e"), parser.parse("x OR y -> e"));
    }

    @Test
    public void testLeftShiftGateParser_matches() {
        final CircuitStepParser<?> parser = new LeftShiftGate.Parser();
        assertTrue(parser.matches("x LSHIFT 2 -> f"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testShiftGateParser_parse() {
        final CircuitStepParser<?> parser = new LeftShiftGate.Parser();
        assertEquals(new LeftShiftGate("x", 2, "f"), parser.parse("x LSHIFT 2 -> f"));
    }

    @Test
    public void testRightShiftGateParser_matches() {
        final CircuitStepParser<?> parser = new RightShiftGate.Parser();
        assertTrue(parser.matches("y RSHIFT 2 -> g"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    public void testLeftShiftGateParser_parse() {
        final CircuitStepParser<?> parser = new RightShiftGate.Parser();
        assertEquals(new RightShiftGate("y", 2, "g"), parser.parse("y RSHIFT 2 -> g"));
    }

    @Test
    public void testNotGateParser_matches() {
        final CircuitStepParser<?> parser = new NotGate.Parser();
        assertTrue(parser.matches("NOT x -> h"));

        assertFalse(parser.matches("y RSHIFT 2 -> g"));
        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
    }

    @Test
    public void testNotGateParser_parse() {
        final CircuitStepParser<?> parser = new NotGate.Parser();
        assertEquals(new NotGate("x", "h"), parser.parse("NOT x -> h"));
    }

    @Test
    public void testCircuit_applyStep() {
        Circuit circuit = new Circuit();
        circuit.applyStep(new ProvideValueToWire(123, "x"));
        circuit.applyStep(new ProvideValueToWire(456, "y"));
        circuit.applyStep(new AndGate("x", "y", "d"));
        circuit.applyStep(new OrGate("x", "y", "e"));
        circuit.applyStep(new LeftShiftGate("x", 2, "f"));
        circuit.applyStep(new RightShiftGate("y", 2, "g"));
        circuit.applyStep(new NotGate("x",  "h"));
        circuit.applyStep(new NotGate("y",  "i"));
        circuit.applyStep(new ProvideWireToWire("i",  "j"));


        assertEquals("{d=72, e=507, f=492, g=114, h=65412, i=65079, j=65079, x=123, y=456}", circuit.getSignalsOnWire().toString());
    }

    @Test
    public void testParseLine() {
        final String input = "123 -> x\n" +
                "456 -> y\n" +
                "x AND y -> d\n" +
                "x OR y -> e\n" +
                "x LSHIFT 2 -> f\n" +
                "y RSHIFT 2 -> g\n" +
                "NOT x -> h\n" +
                "NOT y -> i\n" +
                "i -> j";

        final List<CircuitStep> circuitSteps = Arrays.stream(input.split("\n")).map(new Day07()::parseLine).collect(Collectors.toUnmodifiableList());

        final List<CircuitStep> expectedCircuitSteps = List.of(
                new ProvideValueToWire(123, "x"),
                new ProvideValueToWire(456, "y"),
                new AndGate("x", "y", "d"),
                new OrGate("x", "y", "e"),
                new LeftShiftGate("x", 2, "f"),
                new RightShiftGate("y", 2, "g"),
                new NotGate("x",  "h"),
                new NotGate("y", "i"),
                new ProvideWireToWire("i", "j")
        );

        assertEquals(expectedCircuitSteps, circuitSteps);
    }
}
