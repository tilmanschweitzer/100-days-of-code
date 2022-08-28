package de.tilmanschweitzer.adventofcode.aoc2017;

import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.Value;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class Day07 extends MultiLineAdventOfCodeDay<Day07.TowerDescription, String> {

    public Day07() {
        super(2017, 7);
    }

    public static TowerDescription findBottom(List<TowerDescription> towerDescriptions) {
        final List<TowerDescription> otherNodes = towerDescriptions.stream().filter(TowerDescription::hasSuccessors).collect(toUnmodifiableList());

        for (TowerDescription otherNode : otherNodes) {
            final Optional<TowerDescription> any = otherNodes.stream().filter(node -> node.getSuccessors().contains(otherNode.getName())).findAny();

            if (any.isEmpty()) {
                return otherNode;
            }
        }

        throw new RuntimeException("Found not bottom");
    }

    public static TowerDescription getUpdatedTowerDescriptionToBalanceWeight(List<TowerDescription> towerDescriptions) {
        final TowerDescription bottom = findBottom(towerDescriptions);
        final Map<String, TowerDescription> towerDescriptionsByName = TowerDescription.towerDescriptionsByName(towerDescriptions);


        final TowerDescription unbalancedTowerDescription = findUnbalancedTowerDescription(towerDescriptionsByName, bottom);


        final Optional<TowerDescription> parentOptional = towerDescriptions.stream().filter(towerDescription -> towerDescription.getSuccessors().contains(unbalancedTowerDescription.getName())).findFirst();

        if (parentOptional.isEmpty()) {
            throw new RuntimeException("No parent for unbalanced node found");
        }

        final TowerDescription parent = parentOptional.get();

        final Optional<Result> resultOptional = matchingAndNonMatchingWeight(towerDescriptionsByName, parent);

        if (resultOptional.isEmpty()) {
            throw new RuntimeException("Expected to have exactly one element with a different weight.");
        }

        final Result result = resultOptional.get();

        final int diff = result.getMatchingWeight() - result.getNonMatchingWeight();

        final int newWeight = result.getNonMatchingNode().getWeight() + diff;
        return new TowerDescription(result.getNonMatchingNode().getName(), newWeight, result.getNonMatchingNode().getSuccessors());
    }

    @Value
    private static class Result {
        int matchingWeight;
        int nonMatchingWeight;
        TowerDescription nonMatchingNode;
    }

    private static Optional<Result> matchingAndNonMatchingWeight(Map<String, TowerDescription> towerDescriptionsByName, TowerDescription node) {
        final Map<String, Integer> weightByName = node.getSuccessors().stream()
                .collect(Collectors.toMap(identity(), successorName -> weightOfNode(towerDescriptionsByName, successorName)));


        final Map<Integer, Long> countByWeight = weightByName.values().stream().collect(groupingBy(identity(), counting()));

        if (countByWeight.size() != 2) {
            return Optional.empty();
        }

        final Optional<Integer> nonMatchingWeightOptional
                = countByWeight.entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).findFirst();

        final Optional<Integer> matchingWeightOptional
                = countByWeight.entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).findFirst();

        if (nonMatchingWeightOptional.isEmpty() || matchingWeightOptional.isEmpty()) {
            return Optional.empty();
        }

        final Integer nonMatchingWeight = nonMatchingWeightOptional.get();
        final Integer matchingWeight = matchingWeightOptional.get();

        final String nameForNonMatchingWeight = weightByName.entrySet().stream().filter(entry -> entry.getValue() == nonMatchingWeight).findFirst().get().getKey();
        final TowerDescription descriptionForNonMatchingWeight = towerDescriptionsByName.get(nameForNonMatchingWeight);

        return Optional.of(new Result(matchingWeight, nonMatchingWeight, descriptionForNonMatchingWeight));
    }

    public static TowerDescription findUnbalancedTowerDescription(List<TowerDescription> towerDescriptions) {
        return findUnbalancedTowerDescription(TowerDescription.towerDescriptionsByName(towerDescriptions), findBottom(towerDescriptions));
    }

    public static TowerDescription findUnbalancedTowerDescription(Map<String, TowerDescription> towerDescriptionsByName, TowerDescription node) {
        if (!hasUnbalancesSuccessors(towerDescriptionsByName, node)) {
            return node;
        }

        final Optional<Result> matchingAndNonMatchingWeightOptional = matchingAndNonMatchingWeight(towerDescriptionsByName, node);

        if (matchingAndNonMatchingWeightOptional.isEmpty()) {
            return node;
        }

        return findUnbalancedTowerDescription(towerDescriptionsByName, matchingAndNonMatchingWeightOptional.get().getNonMatchingNode());

    }

    public static boolean hasUnbalancesSuccessors(List<TowerDescription> towerDescriptions, String nodeName) {
        final Map<String, TowerDescription> towerDescriptionsByName = TowerDescription.towerDescriptionsByName(towerDescriptions);
        TowerDescription node = towerDescriptionsByName.get(nodeName);
        return hasUnbalancesSuccessors(towerDescriptionsByName, node);
    }

    public static boolean hasUnbalancesSuccessors(Map<String, TowerDescription> towerDescriptionsByName, TowerDescription node) {
        final Set<Integer> successorWeights = node.getSuccessors().stream().map(successorName -> weightOfNode(towerDescriptionsByName, successorName)).collect(toSet());
        return successorWeights.size() > 1;
    }

    public static Integer weightOfNode(List<TowerDescription> towerDescriptions, String node) {
        return weightOfNode(TowerDescription.towerDescriptionsByName(towerDescriptions), node);
    }

    private static Integer weightOfNode(Map<String, TowerDescription> towerDescriptionsByName, String name) {
        return weightsOfNode(towerDescriptionsByName, name).reduce(Integer::sum).orElse(0);
    }

    public static Stream<Integer> weightsOfNode(List<TowerDescription> towerDescriptions, String node) {
        return weightsOfNode(TowerDescription.towerDescriptionsByName(towerDescriptions), node);
    }

    private static Stream<Integer> weightsOfNode(Map<String, TowerDescription> towerDescriptionsByName, String name) {
        final TowerDescription currentTower = towerDescriptionsByName.get(name);
        if (currentTower.hasNoSuccessors()) {
            return Stream.of(currentTower.getWeight());
        }
        final List<Integer> successorWeights = currentTower.getSuccessors().stream()
                .map(successor -> weightsOfNode(towerDescriptionsByName, successor))
                .reduce(Streams::concat)
                .orElse(Stream.empty())
                .collect(toUnmodifiableList());

        return Streams.concat(Stream.of(currentTower.getWeight()), successorWeights.stream());
    }

    @Override
    public String getResultOfFirstPuzzle(final List<TowerDescription> input) {
        return findBottom(input).getName();
    }

    @Override
    public String getResultOfSecondPuzzle(final List<TowerDescription> input) {
        final TowerDescription bottom = Day07.findBottom(input);

        for (String successor : bottom.getSuccessors()) {
            System.out.println(Day07.weightsOfNode(input, successor).collect(toUnmodifiableList()));
        }
        return getUpdatedTowerDescriptionToBalanceWeight(input).getWeight() + "";
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

        public static Map<String, TowerDescription> towerDescriptionsByName(List<TowerDescription> towerDescriptions) {
            return towerDescriptions.stream().collect(Collectors.toMap(TowerDescription::getName, identity()));
        }

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
