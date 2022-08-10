package de.tilmanschweitzer.adventofcode.aoc2016;

import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day07 extends MultiLineAdventOfCodeDay<String, Long> {

    public Day07() {
        super(2016, 7);
    }

    @Override
    public Long getResultOfFirstPuzzle(final List<String> input) {
        return input.stream().filter(Day07::hasSupportForTLS).count();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<String> input) {
        return input.stream().filter(Day07::hasSupportForSSL).count();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2016/day07-input.txt");
    }

    @Override
    protected String parseLine(String line) {
        return line;
    }


    public static boolean hasSupportForTLS(String s) {
        if (getStringsInsideBrackets(s).stream().anyMatch(Day07::containsAbbaCombination)) {
            return false;
        }
        return getStringsOutsideBrackets(s).stream().anyMatch(Day07::containsAbbaCombination);
    }

    public static boolean hasSupportForSSL(String s) {
        final Set<Pair<Character>> pairsInsideBrackets = getStringsInsideBrackets(s).stream()
                .map(Day07::findAbaCombination)
                .flatMap(Streams::concat).collect(Collectors.toUnmodifiableSet());

        if (pairsInsideBrackets.isEmpty()) {
            return false;
        }

        final Set<Pair<Character>> pairsOutsideBrackets = getStringsOutsideBrackets(s).stream()
                .map(Day07::findAbaCombination)
                .flatMap(Streams::concat).collect(Collectors.toUnmodifiableSet());

        if (pairsOutsideBrackets.isEmpty()) {
            return false;
        }

        return pairsInsideBrackets.stream().map(Pair::flip).anyMatch(pairsOutsideBrackets::contains);
    }

    public static List<String> getStringsInsideBrackets(String s) {
        final Pattern groupsWithinBrackets = Pattern.compile("\\[(\\w+)]");
        return getAllMatchGroupsByIndex(groupsWithinBrackets.matcher(s), 1);
    }

    public static List<String> getStringsOutsideBrackets(String s) {
        final Pattern groupsWithinBrackets = Pattern.compile("(^|])(\\w+)(\\[|$)");
        return getAllMatchGroupsByIndex(groupsWithinBrackets.matcher(s), 2);
    }

    private static List<String> getAllMatchGroupsByIndex(Matcher matcher, int groupIndex) {
        final List<String> foundGroups = new ArrayList<>();
        while (matcher.find()) {
            foundGroups.add(matcher.group(groupIndex));
        }
        return foundGroups.stream().collect(Collectors.toUnmodifiableList());
    }


    public static boolean containsAbbaCombination(String input) {
        return IntStream.range(0, input.length() - 3).boxed().anyMatch(startIndex -> {
            final char firstChar = input.charAt(startIndex);
            final char secondChar = input.charAt(startIndex + 1);
            final char thirdChar = input.charAt(startIndex + 2);
            final char forthChar = input.charAt(startIndex + 3);
            return firstChar == forthChar && secondChar == thirdChar && firstChar != secondChar;
        });
    }

    public static Stream<Pair<Character>> findAbaCombination(String input) {
        return IntStream.range(0, input.length() - 2).boxed()
                .map(startIndex -> input.substring(startIndex, startIndex + 3))
                .filter(sequence -> {
                    final char firstChar = sequence.charAt(0);
                    final char secondChar = sequence.charAt(1);
                    final char thirdChar = sequence.charAt(2);
                    return firstChar == thirdChar && firstChar != secondChar;
                }).map(sequence -> Pair.of(sequence.charAt(0), sequence.charAt(1)));
    }

}
