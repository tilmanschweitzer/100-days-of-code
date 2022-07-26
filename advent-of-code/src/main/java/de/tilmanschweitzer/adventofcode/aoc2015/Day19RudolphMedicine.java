package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day19RudolphMedicine extends MultiLineAdventOfCodeDay<String, Integer> {

    public Day19RudolphMedicine() {
        super(2015, 19);
    }

    public static Set<String> distinctReplacements(final String molecule, List<Replacement> replacements) {
        final Map<String, List<Replacement>> replacementsByMatch = replacements.stream().collect(Collectors.groupingBy(Replacement::getMatch));
        final Set<String> results = new HashSet<>();


        final Set<String> matchers = replacementsByMatch.keySet();

        for (final String matcher : matchers) {
            String restMolecule = molecule;
            String parsedMolecule = "";
            int matcherIndex = restMolecule.indexOf(matcher);
            while (matcherIndex >= 0) {
                for (Replacement replacement : replacementsByMatch.get(matcher)) {
                    final String result = parsedMolecule + restMolecule.replaceFirst(matcher, replacement.getReplacement());
                    results.add(result);
                }
                final int parsedUntil = matcherIndex + matcher.length();
                parsedMolecule = parsedMolecule + restMolecule.substring(0, parsedUntil);
                restMolecule = restMolecule.substring(parsedUntil);
                matcherIndex = restMolecule.indexOf(matcher);
            }
        }

        return results;
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<String> input) {
        final List<Replacement> replacements = input.subList(0, input.size() - 2).stream().map(Replacement::parse).collect(Collectors.toUnmodifiableList());
        final String molecule = input.get(input.size() - 1);
        return distinctReplacements(molecule, replacements).size();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<String> inputNumbers) {
        return 0;
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day19-input.txt");
    }



    @EqualsAndHashCode
    @ToString
    @Getter
    public static class Replacement {
        final String match;
        final String replacement;

        public Replacement(String match, String replacement) {
            this.match = match;
            this.replacement = replacement;
        }

        public static Replacement parse(String line) {
            final Pattern pattern = Pattern.compile("(\\w+) => (\\w+)");
            final Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse line: " + line);
            }
            return new Replacement(matcher.group(1), matcher.group(2));
        }
    }
}
