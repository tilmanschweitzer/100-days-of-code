package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.base.Predicate;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day16AuntSue extends MultiLineAdventOfCodeDay<Day16AuntSue.AuntSue, Integer> {

    public Day16AuntSue() {
        super(2015, 16);
    }

    public static final Map<String, Integer> resultFromMFCSAM = Map.of(
            "children", 3,
            "cats", 7,
            "samoyeds", 2,
            "pomeranians", 3,
            "akitas", 0,
            "vizslas", 0,
            "goldfish", 5,
            "trees", 3,
            "cars", 2,
            "perfumes", 1
    );

    public static final Map<String, Predicate<Integer>> resultFromMFCSAMWithRanges = Map.of(
            "children", equalTo(3),
            "cats",  greaterThan(7),
            "samoyeds", equalTo(2),
            "pomeranians", lessThan(3),
            "akitas", equalTo(0),
            "vizslas", equalTo(0),
            "goldfish", lessThan(5),
            "trees", greaterThan(3),
            "cars", equalTo(2),
            "perfumes", equalTo(1)
    );

    public static Predicate<Integer> equalTo(int expected) {
        return actual -> actual == expected;
    }

    public static Predicate<Integer> lessThan(int expected) {
        return actual -> actual < expected;
    }

    public static Predicate<Integer> greaterThan(int expected) {
        return actual -> actual > expected;
    }


    @Override
    public Integer getResultOfFirstPuzzle(final List<AuntSue> auntSues) {
        final Stream<AuntSue> auntSueStream = auntSues.stream().filter(auntSue -> !auntSue.contradictsResultFromMFCSAM(resultFromMFCSAM));
        final List<AuntSue> result = auntSueStream.collect(Collectors.toList());
        if (result.size() != 1) {
            throw new RuntimeException("Expected exactly one result");
        }
        return result.get(0).getNumber();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<AuntSue> auntSues) {
        final Stream<AuntSue> auntSueStream = auntSues.stream().filter(auntSue -> !auntSue.contradictsResultFromMFCSAMWithRanges(resultFromMFCSAMWithRanges));
        final List<AuntSue> result = auntSueStream.collect(Collectors.toList());
        if (result.size() != 1) {
            throw new RuntimeException("Expected exactly one result");
        }
        return result.get(0).getNumber();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day16-input.txt");
    }

    @Override
    protected AuntSue parseLine(String line) {
        return AuntSue.parse(line);
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    public static class AuntSue {

        final int number;
        final Map<String, Integer> rememberedFacts;

        public AuntSue(int number, Map<String, Integer> rememberedFacts) {
            this.number = number;
            this.rememberedFacts = rememberedFacts;
        }


        public boolean contradictsResultFromMFCSAM(Map<String, Integer> resultFromMFCSAM) {
            for (Map.Entry<String, Integer> rememberedFact : rememberedFacts.entrySet()) {
                final String rememberedFactKey = rememberedFact.getKey();
                if (resultFromMFCSAM.containsKey(rememberedFactKey) && !resultFromMFCSAM.get(rememberedFactKey).equals(rememberedFact.getValue())) {
                    return true;
                }
            }
            return false; // No contradictions found
        }

        public boolean contradictsResultFromMFCSAMWithRanges(Map<String, Predicate<Integer>> resultFromMFCSAM) {
            for (Map.Entry<String, Integer> rememberedFact : rememberedFacts.entrySet()) {
                final String rememberedFactKey = rememberedFact.getKey();
                if (resultFromMFCSAM.containsKey(rememberedFactKey) && !resultFromMFCSAM.get(rememberedFactKey).test(rememberedFact.getValue())) {
                    return true;
                }
            }
            return false; // No contradictions found
        }

        public static AuntSue parse(String line) {
            final Pattern parsePattern = Pattern.compile("(\\d+): (\\w+): (-?\\d+), (\\w+): (-?\\d+), (\\w+): (-?\\d+)");

            final Matcher matcher = parsePattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse line: " + line);
            }

            final int number = Integer.parseInt(matcher.group(1));
            final String firstFact = matcher.group(2);
            final int firstFactValue = Integer.parseInt(matcher.group(3));
            final String secondFact = matcher.group(4);
            final int secondFactValue = Integer.parseInt(matcher.group(5));
            final String thirdFact = matcher.group(6);
            final int thirdFactValue = Integer.parseInt(matcher.group(7));

            return new AuntSue(number, Map.of(firstFact, firstFactValue, secondFact, secondFactValue, thirdFact, thirdFactValue));
        }
    }
}
