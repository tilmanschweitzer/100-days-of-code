package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day12.JsonNumberExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    @Test
    public void extractAllNumbersFromString() {
        assertEquals(List.of(1L, 2L, 3L), Day12.extractAllNumbersFromString("[1,2,3]"));
        assertEquals(List.of(2L, 4L), Day12.extractAllNumbersFromString("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3L), Day12.extractAllNumbersFromString("[[[3]]]"));
        assertEquals(List.of(4L, -1L), Day12.extractAllNumbersFromString("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInString() {
        assertEquals(6L, Day12.sumAllNumbersInString("[1,2,3]"));
        assertEquals(6L, Day12.sumAllNumbersInString("{\"a\":2,\"b\":4}"));
        assertEquals(3L, Day12.sumAllNumbersInString("[[[3]]]"));
        assertEquals(0L, Day12.sumAllNumbersInString("{\"a\":[-1,1]}"));
        assertEquals(0L, Day12.sumAllNumbersInString("[-1,{\"a\":1}]"));
        assertEquals(0L, Day12.sumAllNumbersInString("[]"));
        assertEquals(0L, Day12.sumAllNumbersInString("{}"));
    }

    @Test
    public void extractAllNumbersFromJson() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor();
        assertEquals(List.of(1L, 2L, 3L), jsonNumberExtractor.extractAllNumbersFromJson("[1,2,3]"));
        assertEquals(List.of(2L, 4L), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3L), jsonNumberExtractor.extractAllNumbersFromJson("[[[3]]]"));
        assertEquals(List.of(4L, -1L), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInJson() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor();
        assertEquals(6L, jsonNumberExtractor.sumAllNumbersInJson("[1,2,3]"));
        assertEquals(6L, jsonNumberExtractor.sumAllNumbersInJson("{\"a\":2,\"b\":4}"));
        assertEquals(3L, jsonNumberExtractor.sumAllNumbersInJson("[[[3]]]"));
        assertEquals(0L, jsonNumberExtractor.sumAllNumbersInJson("{\"a\":[-1,1]}"));
        assertEquals(0L, jsonNumberExtractor.sumAllNumbersInJson("[-1,{\"a\":1}]"));
        assertEquals(0L, jsonNumberExtractor.sumAllNumbersInJson("[]"));
        assertEquals(0L, jsonNumberExtractor.sumAllNumbersInJson("{}"));
    }

    @Test
    public void extractAllNumbersFromJsonSkippingRedNodes() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor(JsonNumberExtractor::isObjectNodeWithRedValue);
        assertEquals(List.of(1L, 2L, 3L), jsonNumberExtractor.extractAllNumbersFromJson("[1,2,3]"));
        assertEquals(List.of(2L, 4L), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":2,\"b\":4}"));
        assertEquals(List.of(3L), jsonNumberExtractor.extractAllNumbersFromJson("[[[3]]]"));
        assertEquals(List.of(4L, -1L), jsonNumberExtractor.extractAllNumbersFromJson("{\"a\":{\"b\":4},\"c\":-1}"));
    }

    @Test
    public void sumAllNumbersInJsonSkippingRedNodes() {
        final JsonNumberExtractor jsonNumberExtractor = new JsonNumberExtractor(JsonNumberExtractor::isObjectNodeWithRedValue);
        assertEquals(6L, jsonNumberExtractor.sumAllNumbersInJson("[1,2,3]"));
        assertEquals(4L, jsonNumberExtractor.sumAllNumbersInJson("[1,{\"c\":\"red\",\"b\":2},3]"));
        assertEquals(0L, jsonNumberExtractor.sumAllNumbersInJson("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"));
        assertEquals(6L, jsonNumberExtractor.sumAllNumbersInJson("[1,\"red\",5]"));
    }
}
