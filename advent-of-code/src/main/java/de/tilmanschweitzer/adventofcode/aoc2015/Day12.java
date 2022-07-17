package de.tilmanschweitzer.adventofcode.aoc2015;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day12 extends SingleLineAdventOfCodeDay<String, Long> {

    public Day12() {
        super(2015, 12);
    }
    public static class JsonNumberExtractor {
        final Predicate<JsonNode> skipCondition;

        public JsonNumberExtractor() {
            this(JsonNumberExtractor::alwaysFalse);
        }

        public JsonNumberExtractor(Predicate<JsonNode> skipCondition) {
            this.skipCondition = skipCondition;
        }

        public List<Long> extractAllNumbersFromJson(String s) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                final JsonNode jsonNode = objectMapper.readValue(s, JsonNode.class);
                return extractAllNumbersFromJson(jsonNode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Long> extractAllNumbersFromJson(JsonNode jsonNode) {
            if (skipCondition.test(jsonNode)) {
                return Collections.emptyList();
            }
            if (jsonNode.isNumber()) {
                return List.of(jsonNode.longValue());
            }
            if (jsonNode.isArray()) {
                return iteratorToStream(jsonNode.elements())
                        .map(this::extractAllNumbersFromJson)
                        .flatMap(List::stream)
                        .collect(Collectors.toUnmodifiableList());
            }
            if (jsonNode.isObject()) {
                return iteratorToStream(jsonNode.fields())
                        .map(Map.Entry::getValue)
                        .map(this::extractAllNumbersFromJson)
                        .flatMap(List::stream)
                        .collect(Collectors.toUnmodifiableList());
            }
            return Collections.emptyList();
        }

        public Long sumAllNumbersInJson(String s) {
            return extractAllNumbersFromJson(s).stream().reduce(Long::sum).orElse(0L);
        }
        public static <T> Stream<T>  iteratorToStream(Iterator<T> iterator) {
            final Iterable<T> iterable = () -> iterator;
            return StreamSupport.stream(iterable.spliterator(), false);
        }

        public static boolean isObjectNodeWithRedValue(JsonNode jsonNode) {
            if (!jsonNode.isObject()) {
                return false;
            }
            return iteratorToStream(jsonNode.fields()).map(Map.Entry::getValue).anyMatch((value) -> value.isTextual() && value.textValue().equals("red"));
        }

        public static <T> boolean alwaysFalse(T object) {
            return false;
        }
    }
    public static List<Long> extractAllNumbersFromString(String s) {
        final String[] split = s.split("[^-\\d]+");
        return Arrays.stream(split).filter(Predicate.not(String::isBlank)).map(Long::parseLong).collect(Collectors.toUnmodifiableList());
    }

    public static Long sumAllNumbersInString(String s) {
        return extractAllNumbersFromString(s).stream().reduce(Long::sum).orElse(0L);
    }

    @Override
    public Long getResultOfFirstPuzzle(final String input) {
        return sumAllNumbersInString(input);
    }

    @Override
    public Long getResultOfSecondPuzzle(final String input) {
        return new JsonNumberExtractor(JsonNumberExtractor::isObjectNodeWithRedValue).sumAllNumbersInJson(input);
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day12-input.txt");
    }
}
