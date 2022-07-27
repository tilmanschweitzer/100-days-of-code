package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.collect.Lists;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<ReplacementAtPosition> findStepsToGenerate(String start, String molecule, List<Replacement> replacements) {
        final Map<String, List<Replacement>> replacementsByReplacement = replacements.stream().collect(Collectors.groupingBy(Replacement::getReplacement));

        final List<List<ReplacementAtPosition>> possibleSteps = findReplacements(start, molecule, Collections.emptyList(), replacementsByReplacement).collect(Collectors.toUnmodifiableList());

        for (List<ReplacementAtPosition> possibleStep : possibleSteps) {
            System.out.println(possibleStep);
        }

        return possibleSteps.stream().sorted(Comparator.comparingInt(List::size)).findFirst().orElse(Collections.emptyList());
    }

    public static Stream<List<ReplacementAtPosition>> findReplacements(String start, String currentString, List<ReplacementAtPosition> currentSteps, Map<String, List<Replacement>> replacementsByReplacement ) {
        if (currentString.equals(start)) {
            return Stream.of(Lists.reverse(currentSteps));
        }

        if (currentString.contains(start) && !currentString.equals(start)) {
            return Stream.empty();
        }

        if (currentSteps.size() > 1000) {
            System.out.println("Max steps reached: " + currentString + " " + currentSteps);
            return Stream.empty();
        }


        final Set<String> replacements = replacementsByReplacement.keySet();

        final Set<List<ReplacementAtPosition>> results = new HashSet<>();

        for (final String replacementString : replacements) {
            String restMolecule = currentString;
            String parsedMolecule = "";
            int matcherIndex = restMolecule.indexOf(replacementString);
            while (matcherIndex >= 0) {
                for (Replacement replacement : replacementsByReplacement.get(replacementString)) {
                    final String result = parsedMolecule + restMolecule.replaceFirst(replacementString, replacement.getMatch());
                    final int replacementPosition = matcherIndex + parsedMolecule.length();
                    final List<ReplacementAtPosition> nextSteps = new ArrayList<>(currentSteps);
                    nextSteps.add(new ReplacementAtPosition(replacementPosition, replacement));
                    findReplacements(start, result, nextSteps, replacementsByReplacement).forEach(results::add);
                }
                final int parsedUntil = matcherIndex + replacementString.length();
                parsedMolecule = parsedMolecule + restMolecule.substring(0, parsedUntil);
                restMolecule = restMolecule.substring(parsedUntil);
                matcherIndex = restMolecule.indexOf(replacementString);
            }
        }

        return results.stream();
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<String> input) {
        final List<Replacement> replacements = input.subList(0, input.size() - 2).stream().map(Replacement::parse).collect(Collectors.toUnmodifiableList());
        final String molecule = input.get(input.size() - 1);
        return distinctReplacements(molecule, replacements).size();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<String> input) {
        final List<Replacement> replacements = input.subList(0, input.size() - 2).stream().map(Replacement::parse).collect(Collectors.toUnmodifiableList());
        final String molecule = input.get(input.size() - 1);
        return findStepsToGenerate("e", molecule, replacements).size();
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
    public static class ReplacementAtPosition {
        final int position;
        final Replacement replacement;

        public ReplacementAtPosition(int position, Replacement replacement) {
            this.position = position;
            this.replacement = replacement;
        }


        @Override
        public String toString() {
            return "{"+ position + ", " + replacement + "}";
        }
    }

    @EqualsAndHashCode
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

        @Override
        public String toString() {
            return match + " => " + replacement;
        }
    }
}
