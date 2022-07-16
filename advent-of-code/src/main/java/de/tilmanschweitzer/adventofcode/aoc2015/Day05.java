package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day05 extends MultiLineAdventOfCodeDay<String, Long> {

    public static boolean isVowel(String s) {
        Preconditions.checkArgument(s.length() == 1);
        return "aeiou".contains(s);
    }
    public static boolean isNiceString(String input) {
        final String[] split = input.split("");
        final boolean containsAtLeastThreeVowels =  Arrays.stream(split).filter(Day05::isVowel).count() >= 3;
        final boolean containsAtLeastOneDoubleLetter = IntStream.range(0, input.length() - 1).anyMatch(index -> split[index].equals(split[index + 1]));
        final boolean containsForbiddenCombinations = List.of("ab", "cd", "pq", "xy").stream().anyMatch(forbiddenCombination -> input.contains(forbiddenCombination));
        return containsAtLeastThreeVowels && containsAtLeastOneDoubleLetter && !containsForbiddenCombinations;
    }

    public static boolean isNiceStringVersion2(String input) {
        final String[] split = input.split("");
        final List<String> twoLetterPairs = IntStream.range(0, input.length() - 1)
                .boxed()
                .map(index -> input.substring(index, index + 2))
                .collect(toUnmodifiableList());

        final boolean containsNonOverlappingTwoLetterPairTwice = twoLetterPairs.stream().anyMatch(twoLetterPair -> {
            final int firstIndexOf = twoLetterPairs.indexOf(twoLetterPair);
            final int lastIndexOf = twoLetterPairs.lastIndexOf(twoLetterPair);
            return Math.abs(firstIndexOf - lastIndexOf) > 1;
        });

        final List<String> threeLetterCombos = IntStream.range(0, input.length() - 2)
                .boxed()
                .map(index -> input.substring(index, index + 3))
                .collect(toUnmodifiableList());

        final boolean containsOneLetterRepeating = threeLetterCombos.stream().anyMatch(threeLetterCombo -> threeLetterCombo.charAt(0) == threeLetterCombo.charAt(2));

        return containsNonOverlappingTwoLetterPairTwice && containsOneLetterRepeating;
    }

    @Override
    public Long getResultOfFirstPuzzle(final List<String> input) {
        return input.stream().filter(Day05::isNiceString).count();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<String> input) {
        return input.stream().filter(Day05::isNiceStringVersion2).count();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day05-input.txt");
    }


    @Override
    protected String parseLine(String line) {
        return line;
    }
}
