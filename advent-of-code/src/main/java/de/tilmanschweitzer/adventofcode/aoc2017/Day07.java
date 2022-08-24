package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;
import lombok.*;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day07 extends MultiLineAdventOfCodeDay<Day07.TowerDescription, String> {

    public Day07() {
        super(2017, 7);
    }

    public static String findBottom(List<TowerDescription> towerDescriptions) {
        final List<TowerDescription> topNodes = towerDescriptions.stream().filter(TowerDescription::hasNoSuccessors).collect(toUnmodifiableList());
        final List<TowerDescription> otherNodes = towerDescriptions.stream().filter(TowerDescription::hasSuccessors).collect(toUnmodifiableList());

        for (TowerDescription otherNode : otherNodes) {
            final Optional<TowerDescription> any = otherNodes.stream().filter(node -> node.getSuccessors().contains(otherNode.getName())).findAny();

            if (any.isEmpty()) {
                return otherNode.getName();
            }
        }

        throw new RuntimeException("Found not bottom");
    }

    public String getResultOfFirstPuzzle(final List<TowerDescription> input) {
        return findBottom(input);
    }

    @Override
    public String getResultOfSecondPuzzle(final List<TowerDescription> input) {
        return "";
    }

    @Override
    public TowerDescription parseLine(String line) {
        return TowerDescription.parse(line);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2017/day07-input.txt");
    }

    @Value
    public static class TowerDescription {
        String name;
        int weight;
        List<String> successors;

        public boolean hasSuccessors() {
            return !successors.isEmpty();
        }

        public boolean hasNoSuccessors() {
            return successors.isEmpty();
        }

        public static TowerDescription parse(String line) {
            final Pattern towerDescriptionPattern = Pattern.compile("(\\w+) \\((\\d+)\\)( -> (\\w+(, |))+)?");


            final Matcher matcher = towerDescriptionPattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse '" + line + "'");
            }

            final String name = matcher.group(1);
            final int weight = Integer.parseInt(matcher.group(2));

            if (matcher.group(3) == null) {
                return new TowerDescription(name, weight, emptyList());
            }

            final String[] successorsArray = matcher.group(3).replaceFirst("->", "").split(",");
            final List<String> successors = Arrays.stream(successorsArray).map(String::trim)
                    .filter(not(String::isBlank))
                    .collect(toUnmodifiableList());
            return new TowerDescription(name, weight, successors);

        }
    }
}
