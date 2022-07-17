package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.hash.Hashing;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day04 extends SingleLineAdventOfCodeDay<String, Long> {

    public Day04() {
        super(2015, 4);
    }

    public static String md5(String input) {
        return Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static long findAdventCoinForKey(String key) {
        int currentNumber = 1;

        while(!md5(key + currentNumber).startsWith("00000")) {
            currentNumber++;
        }

        return currentNumber;
    }

    public static long findAdventCoinForKey(String key, int numberOfZeros) {
        int currentNumber = 1;

        final String leadingZeros = IntStream.range(0, numberOfZeros).boxed().map(i -> "0").collect(Collectors.joining());

        while(!md5(key + currentNumber).startsWith(leadingZeros)) {
            currentNumber++;
        }

        return currentNumber;
    }

    @Override
    public Long getResultOfFirstPuzzle(final String input) {
        return findAdventCoinForKey(input, 5);
    }

    @Override
    public Long getResultOfSecondPuzzle(final String input) {
        return findAdventCoinForKey(input, 6);
    }


    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day04-input.txt");
    }


    @Override
    protected String parseLine(String line) {
        return line;
    }
}
