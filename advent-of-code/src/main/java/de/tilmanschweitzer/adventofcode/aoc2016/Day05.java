package de.tilmanschweitzer.adventofcode.aoc2016;

import com.google.common.hash.Hashing;
import de.tilmanschweitzer.adventofcode.common.Hashes;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.Hashes.md5;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day05 extends SingleLineAdventOfCodeDay<String, String> {

    public Day05() {
        super(2016, 5);
    }

    @Override
    public String getResultOfFirstPuzzle(final String input) {
        return findPasswordForDoorId(input, 5);
    }

    @Override
    public String getResultOfSecondPuzzle(final String input) {
        return "";
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day05-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }

    public static String findPasswordForDoorId(String doorId, int numberOfZeros) {
        final String leadingZeros = IntStream.range(0, numberOfZeros).boxed().map(i -> "0").collect(Collectors.joining());

        return Stream.iterate(0L, i -> i + 1L)
                .map(i -> md5(doorId + i))
                .filter(hash -> hash.startsWith(leadingZeros))
                .limit(8)
                .map(s -> s.substring(numberOfZeros, numberOfZeros + 1))
                .collect(Collectors.joining());
    }
}
