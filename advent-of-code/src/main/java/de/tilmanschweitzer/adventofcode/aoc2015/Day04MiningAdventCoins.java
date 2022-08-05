package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.Hashes.md5;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day04MiningAdventCoins extends SingleLineAdventOfCodeDay<String, Long> {

    public Day04MiningAdventCoins() {
        super(2015, 4);
    }

    @Override
    public Long getResultOfFirstPuzzle(final String input) {
        return findAdventCoinForKey(input, 5);
    }

    @Override
    public Long getResultOfSecondPuzzle(final String input) {
        return findAdventCoinForKey(input, 6);
    }

    public static long findAdventCoinForKey(String key) {
        return findAdventCoinForKey(key, 5);
    }

    public static long findAdventCoinForKey(String key, int numberOfZeros) {
        final String leadingZeros = IntStream.range(0, numberOfZeros).boxed().map(i -> "0").collect(Collectors.joining());

        final Optional<Long> adventCoin = Stream.iterate(0L, i -> i + 1L)
                .filter(i -> md5(key + i).startsWith(leadingZeros))
                .findFirst();

        if (adventCoin.isEmpty()) {
            throw new RuntimeException("Found no match");
        }

        return adventCoin.get();
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
