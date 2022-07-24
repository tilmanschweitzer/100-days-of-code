package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day12WeirdJsonSum.JsonNumberExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day12WeirdJsonSumTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(191164, new Day12WeirdJsonSum().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(87842, new Day12WeirdJsonSum().getResultOfSecondPuzzle());
    }

    @Test
    public void extractAllNumbersFromString() {
        assertEquals(List.of(1, 2, 3), Day12WeirdJsonSum.extractAllNumbersFromString("[1,2,3]"));
        assertEquals(List.of(2, 4), Day12WeirdJsonSum.extractAllNumbersFromString("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3), Day12WeirdJsonSum.extractAllNumbersFromString("[[[3]]]"));
        assertEquals(List.of(4, -1), Day12WeirdJsonSum.extractAllNumbersFromString("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInString() {
        assertEquals(6, Day12WeirdJsonSum.sumAllNumbersInString("[1,2,3]"));
        assertEquals(6, Day12WeirdJsonSum.sumAllNumbersInString("{\"a\":2,\"b\":4}"));
        assertEquals(3, Day12WeirdJsonSum.sumAllNumbersInString("[[[3]]]"));
        assertEquals(0, Day12WeirdJsonSum.sumAllNumbersInString("{\"a\":[-1,1]}"));
        assertEquals(0, Day12WeirdJsonSum.sumAllNumbersInString("[-1,{\"a\":1}]"));
        assertEquals(0, Day12WeirdJsonSum.sumAllNumbersInString("[]"));
        assertEquals(0, Day12WeirdJsonSum.sumAllNumbersInString("{}"));
    }

    @Test
    public void extractAllNumbersFromJson() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor();
        assertEquals(List.of(1, 2, 3), jsonNumberExtractor.extractAllNumbersFromJson("[1,2,3]"));
        assertEquals(List.of(2, 4), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3), jsonNumberExtractor.extractAllNumbersFromJson("[[[3]]]"));
        assertEquals(List.of(4, -1), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInJson() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor();
        assertEquals(6, jsonNumberExtractor.sumAllNumbersInJson("[1,2,3]"));
        assertEquals(6, jsonNumberExtractor.sumAllNumbersInJson("{\"a\":2,\"b\":4}"));
        assertEquals(3, jsonNumberExtractor.sumAllNumbersInJson("[[[3]]]"));
        assertEquals(0, jsonNumberExtractor.sumAllNumbersInJson("{\"a\":[-1,1]}"));
        assertEquals(0, jsonNumberExtractor.sumAllNumbersInJson("[-1,{\"a\":1}]"));
        assertEquals(0, jsonNumberExtractor.sumAllNumbersInJson("[]"));
        assertEquals(0, jsonNumberExtractor.sumAllNumbersInJson("{}"));
    }

    @Test
    public void extractAllNumbersFromJsonSkippingRedNodes() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor(JsonNumberExtractor::isObjectNodeWithRedValue);
        assertEquals(List.of(1, 2, 3), jsonNumberExtractor.extractAllNumbersFromJson("[1,2,3]"));
        assertEquals(List.of(2, 4), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3), jsonNumberExtractor.extractAllNumbersFromJson("[[[3]]]"));
        assertEquals(List.of(4, -1), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInJsonSkippingRedNodes() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor(JsonNumberExtractor::isObjectNodeWithRedValue);
        assertEquals(6, jsonNumberExtractor.sumAllNumbersInJson("[1,2,3]"));
        assertEquals(4, jsonNumberExtractor.sumAllNumbersInJson("[1,{\"c\":\"red\",\"b\":2},3]"));
        assertEquals(0, jsonNumberExtractor.sumAllNumbersInJson("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"));
        assertEquals(6, jsonNumberExtractor.sumAllNumbersInJson("[1,\"red\",5]"));
    }
}
