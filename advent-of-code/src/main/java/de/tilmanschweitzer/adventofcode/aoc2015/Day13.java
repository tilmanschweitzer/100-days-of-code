package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.common.Combinations;
import de.tilmanschweitzer.adventofcode.common.Ring;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day13 extends MultiLineAdventOfCodeDay<Day13.SeatPreference, Integer> {

    public Day13() {
        super(2015, 13);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<SeatPreference> input) {
        final SeatCombinationExplorer seatCombinationExplorer = new SeatCombinationExplorer(input);
        final Ring<String> happiestCombination = seatCombinationExplorer.searchHappiestCombination();
        return seatCombinationExplorer.happinessForSeatCombination(happiestCombination);
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<SeatPreference> input) {
        final List<SeatPreference> newInput = new ArrayList(input);
        final Set<String> allPersons = input.stream().map(SeatPreference::getPerson).collect(Collectors.toSet());

        for (String person : allPersons) {
            newInput.add(new SeatPreference("Me", person, 0));
            newInput.add(new SeatPreference(person, "Me", 0));

        }

        final SeatCombinationExplorer seatCombinationExplorer = new SeatCombinationExplorer(newInput);
        final Ring<String> happiestCombination = seatCombinationExplorer.searchHappiestCombination();
        return seatCombinationExplorer.happinessForSeatCombination(happiestCombination);
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day13-input.txt");
    }

    @Override
    protected SeatPreference parseLine(String line) {
        return SeatPreference.parse(line);
    }


    public static class SeatCombinationExplorer {
        final Set<String> allPersonsAsSet;
        final List<String> allPersons;
        final Map<PersonPartnerPair, SeatPreference> seatPreferencesByPersonAndPartner;

        int currentHighestHappiness = Integer.MIN_VALUE;
        Ring<String> currentHappiestCombination = Ring.empty();

        public SeatCombinationExplorer(List<SeatPreference> seatPreferences) {
            allPersonsAsSet = seatPreferences.stream().map(SeatPreference::getPerson).collect(Collectors.toUnmodifiableSet());
            allPersons = allPersonsAsSet.stream().collect(Collectors.toUnmodifiableList());
            seatPreferencesByPersonAndPartner = seatPreferences.stream()
                    .collect(Collectors.toMap(SeatPreference::getPersonPartnerPair, Function.identity()));
        }

        public int happinessForSeatCombination(Ring<String> combination) {
            return IntStream.range(0, combination.size()).boxed().map(index -> {
                final String person = combination.get(index);
                final String rightPartner = combination.get(index + 1);
                final String leftPartner = combination.get(index - 1);

                final int rightHappiness = seatPreferencesByPersonAndPartner.get(new PersonPartnerPair(person, rightPartner)).getHappiness();
                final int leftHappiness = seatPreferencesByPersonAndPartner.get(new PersonPartnerPair(person, leftPartner)).getHappiness();
                return rightHappiness + leftHappiness;
            }).reduce(Integer::sum).orElse(0);
        }

        public Ring<String> searchHappiestCombination() {
            final Set<Ring<String>> allCombinations = Combinations.allCombinations(allPersonsAsSet).stream().map(Ring::of).collect(Collectors.toSet());

            for (Ring<String> combinationCandidate : allCombinations) {
                final int candidateHappiness = happinessForSeatCombination(combinationCandidate);

                if (candidateHappiness > currentHighestHappiness) {
                    currentHighestHappiness = candidateHappiness;
                    currentHappiestCombination = combinationCandidate;
                }
            }

            return currentHappiestCombination;

        }

    }

    @EqualsAndHashCode
    @ToString
    @Getter
    public static class PersonPartnerPair {
        final String person;
        final String partner;

        public PersonPartnerPair(String person, String partner) {
            this.person = person;
            this.partner = partner;
        }
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    public static class SeatPreference {
        final PersonPartnerPair personPartnerPair;
        final int happiness;

        public SeatPreference(String person, String partner, int happiness) {
            this.personPartnerPair = new PersonPartnerPair(person, partner);
            this.happiness = happiness;
        }

        public String getPerson() {
            return personPartnerPair.getPerson();
        }

        public String getPartner() {
            return personPartnerPair.getPartner();
        }

        public static SeatPreference parse(String line) {
            final Pattern parsePattern = Pattern.compile("(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)");

            final Matcher matcher = parsePattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse line: " + line);
            }

            final String person = matcher.group(1);
            final int gainOrLooseFactor = matcher.group(2).equals("gain") ? 1 : -1 ;
            final int happinessChange = gainOrLooseFactor * Integer.parseInt(matcher.group(3));
            final String partner = matcher.group(4);
            return new SeatPreference(person, partner, happinessChange);
        }
    }
}
