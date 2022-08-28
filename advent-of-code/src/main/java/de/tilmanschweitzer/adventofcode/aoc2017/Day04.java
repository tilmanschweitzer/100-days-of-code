package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.common.Converters;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day04 extends MultiLineAdventOfCodeDay<String, Long> {

    public Day04() {
        super(2017, 4);
    }

    @Override
    public Long getResultOfFirstPuzzle(final List<String> passPhrase) {
        return passPhrase.stream().filter(Day04::isValidPassPhrase).count();
    }

    @Override
    public Long getResultOfSecondPuzzle(final List<String> passPhrase) {
        return passPhrase.stream().filter(Day04::isValidPassPhraseV2).count();
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day04-input.txt");
    }

    public static boolean isValidPassPhrase(List<String> words) {
        return words.size() == words.stream().distinct().count();
    }

    public static boolean isValidPassPhrase(String passPhrase) {
        final List<String> words = splitPhraseIntoWords(passPhrase);
        return isValidPassPhrase(words);
    }


    public static boolean isValidPassPhraseV2(List<String> words) {
        final Set<List<Character>> uniqueWordsSortedByChars = words.stream()
                .map(Day04::wordToSortedCharList)
                .collect(Collectors.toUnmodifiableSet());
        return words.size() == uniqueWordsSortedByChars.size();
    }


    public static boolean isValidPassPhraseV2(String passPhrase) {
        final List<String> words = splitPhraseIntoWords(passPhrase);
        return isValidPassPhraseV2(words);
    }

    private static List<Character> wordToSortedCharList(String word) {
        return Converters.stringToCharStream(word).sorted().collect(Collectors.toUnmodifiableList());
    }


    public static List<String> splitPhraseIntoWords(String line) {
        return Arrays.stream(line.split("\\s+")).collect(Collectors.toList());
    }
}
