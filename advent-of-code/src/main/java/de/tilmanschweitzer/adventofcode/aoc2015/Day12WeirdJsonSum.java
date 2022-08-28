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

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day12WeirdJsonSum extends SingleLineAdventOfCodeDay<String, Integer> {

    public Day12WeirdJsonSum() {
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

        public List<Integer> extractAllNumbersFromJson(String s) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                final JsonNode jsonNode = objectMapper.readValue(s, JsonNode.class);
                return extractAllNumbersFromJson(jsonNode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Integer> extractAllNumbersFromJson(JsonNode jsonNode) {
            if (skipCondition.test(jsonNode)) {
                return Collections.emptyList();
            }
            if (jsonNode.isNumber()) {
                return List.of(jsonNode.intValue());
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

        public Integer sumAllNumbersInJson(String s) {
            return sum(extractAllNumbersFromJson(s));
        }

        public static <T> Stream<T> iteratorToStream(Iterator<T> iterator) {
            final Iterable<T> iterable = () -> iterator;
            return StreamSupport.stream(iterable.spliterator(), false);
        }

        public static boolean isObjectNodeWithRedValue(JsonNode jsonNode) {
            if (!jsonNode.isObject()) {
                return false;
            }
            return iteratorToStream(jsonNode.fields()).map(Map.Entry::getValue).anyMatch((value) -> value.isTextual() && Objects.equals(value.textValue(), "red"));
        }

        public static <T> boolean alwaysFalse(T object) {
            return false;
        }
    }

    public static List<Integer> extractAllNumbersFromString(String s) {
        final String[] split = s.split("[^-\\d]+");
        return Arrays.stream(split).filter(Predicate.not(String::isBlank)).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
    }

    public static Integer sumAllNumbersInString(String s) {
        return sum(extractAllNumbersFromString(s));
    }

    @Override
    public Integer getResultOfFirstPuzzle(final String input) {
        return sumAllNumbersInString(input);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final String input) {
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
