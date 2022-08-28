package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day07BobbyTablesLogicGates.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day07BobbyTablesLogicGatesTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(46065, new Day07BobbyTablesLogicGates().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(14134, new Day07BobbyTablesLogicGates().getResultOfSecondPuzzle());
    }

    @Test
    void testProvideValueToWireStepParser_matches() {
        final CircuitStepParser<?> parser = new ProvideValueToWire.Parser();
        assertTrue(parser.matches("123 -> x"));
        assertTrue(parser.matches("456 -> y"));
        assertTrue(parser.matches("lx -> a"));
        assertTrue(parser.matches("i -> j"));

        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    void testProvideValueToWireStepParser_parse() {
        final CircuitStepParser<?> parser = new ProvideValueToWire.Parser();
        assertEquals(new ProvideValueToWire(123, "x"), parser.parse("123 -> x"));
        assertEquals(new ProvideValueToWire(456, "y"), parser.parse("456 -> y"));
        assertEquals(new ProvideValueToWire("lx", "a"), parser.parse("lx -> a"));
        assertEquals(new ProvideValueToWire("i", "j"), parser.parse("i -> j"));
    }

    @Test
    void testAndGateParser_matches() {
        final CircuitStepParser<?> parser = new AndGate.Parser();
        assertTrue(parser.matches("x AND y -> d"));
        assertTrue(parser.matches("1 AND 2 -> k"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    void testAndGateParser_parse() {
        final CircuitStepParser<?> parser = new AndGate.Parser();
        assertEquals(new AndGate("x", "y", "d"), parser.parse("x AND y -> d"));
        assertEquals(new AndGate(1, 2, "k"), parser.parse("1 AND 2 -> k"));
    }

    @Test
    void testOrGateParser_matches() {
        final CircuitStepParser<?> parser = new OrGate.Parser();
        assertTrue(parser.matches("x OR y -> e"));
        assertTrue(parser.matches("3 OR 4 -> l"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x LSHIFT 2 -> f"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    void testOrGateParser_parse() {
        final CircuitStepParser<?> parser = new OrGate.Parser();
        assertEquals(new OrGate("x", "y", "e"), parser.parse("x OR y -> e"));
        assertEquals(new OrGate(3, 4, "l"), parser.parse("3 OR 4 -> l"));
    }

    @Test
    void testLeftShiftGateParser_matches() {
        final CircuitStepParser<?> parser = new LeftShiftGate.Parser();
        assertTrue(parser.matches("x LSHIFT 2 -> f"));

        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    void testShiftGateParser_parse() {
        final CircuitStepParser<?> parser = new LeftShiftGate.Parser();
        assertEquals(new LeftShiftGate("x", 2, "f"), parser.parse("x LSHIFT 2 -> f"));
        assertEquals(new LeftShiftGate(5, 2, "m"), parser.parse("5 LSHIFT 2 -> m"));
    }

    @Test
    void testRightShiftGateParser_matches() {
        final CircuitStepParser<?> parser = new RightShiftGate.Parser();
        assertTrue(parser.matches("y RSHIFT 2 -> g"));
        assertTrue(parser.matches("10 RSHIFT 2 -> n"));


        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
        assertFalse(parser.matches("NOT x -> h"));
    }

    @Test
    void testLeftShiftGateParser_parse() {
        final CircuitStepParser<?> parser = new RightShiftGate.Parser();
        assertEquals(new RightShiftGate("y", 2, "g"), parser.parse("y RSHIFT 2 -> g"));
        assertEquals(new RightShiftGate(10, 2, "n"), parser.parse("10 RSHIFT 2 -> n"));
    }

    @Test
    void testNotGateParser_matches() {
        final CircuitStepParser<?> parser = new NotGate.Parser();
        assertTrue(parser.matches("NOT x -> h"));

        assertFalse(parser.matches("y RSHIFT 2 -> g"));
        assertFalse(parser.matches("123 -> x"));
        assertFalse(parser.matches("456 -> y"));
        assertFalse(parser.matches("x AND y -> d"));
        assertFalse(parser.matches("x OR y -> e"));
    }

    @Test
    void testNotGateParser_parse() {
        final CircuitStepParser<?> parser = new NotGate.Parser();
        assertEquals(new NotGate("x", "h"), parser.parse("NOT x -> h"));
    }

    @Test
    void testCircuit_applyStep() {
        Circuit circuit = new Circuit();
        circuit.applyStep(new ProvideValueToWire(123, "x"));
        circuit.applyStep(new ProvideValueToWire(456, "y"));
        circuit.applyStep(new AndGate("x", "y", "d"));
        circuit.applyStep(new OrGate("x", "y", "e"));
        circuit.applyStep(new LeftShiftGate("x", 2, "f"));
        circuit.applyStep(new RightShiftGate("y", 2, "g"));
        circuit.applyStep(new NotGate("x", "h"));
        circuit.applyStep(new NotGate("y", "i"));
        circuit.applyStep(new ProvideValueToWire("i", "j"));

        assertEquals("{d=72, e=507, f=492, g=114, h=65412, i=65079, j=65079, x=123, y=456}", circuit.getSignalsOnWire().toString());
    }

    @Test
    void testParseLine() {
        final String input = "123 -> x\n" +
                "456 -> y\n" +
                "x AND y -> d\n" +
                "x OR y -> e\n" +
                "x LSHIFT 2 -> f\n" +
                "y RSHIFT 2 -> g\n" +
                "NOT x -> h\n" +
                "NOT y -> i\n" +
                "i -> j\n" +
                "1 AND 2 -> k\n" +
                "3 OR 4 -> l\n" +
                "5 LSHIFT 2 -> m\n" +
                "10 RSHIFT 2 -> n";

        final List<CircuitStep> circuitSteps = Arrays.stream(input.split("\n")).map(new Day07BobbyTablesLogicGates()::parseLine).collect(Collectors.toUnmodifiableList());

        final List<CircuitStep> expectedCircuitSteps = List.of(
                new ProvideValueToWire(123, "x"),
                new ProvideValueToWire(456, "y"),
                new AndGate("x", "y", "d"),
                new OrGate("x", "y", "e"),
                new LeftShiftGate("x", 2, "f"),
                new RightShiftGate("y", 2, "g"),
                new NotGate("x", "h"),
                new NotGate("y", "i"),
                new ProvideValueToWire("i", "j"),
                new AndGate(1, 2, "k"),
                new OrGate(3, 4, "l"),
                new LeftShiftGate(5, 2, "m"),
                new RightShiftGate(10, 2, "n")
        );

        assertEquals(expectedCircuitSteps, circuitSteps);
    }
}
