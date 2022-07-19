package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.sql.Array;
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
            searchHappiestCombination(Ring.empty());

            return currentHappiestCombination;

        }

        private void searchHappiestCombination(Ring<String> combinationCandidate) {
            if (combinationCandidate.size() == allPersons.size()) {
                final int candidateHapiness = happinessForSeatCombination(combinationCandidate);
                if (candidateHapiness > currentHighestHappiness) {
                    currentHighestHappiness = candidateHapiness;
                    currentHappiestCombination = combinationCandidate;
                }
            }
            final List<String> availableNextPersons = allPersons.stream().filter(person -> !combinationCandidate.contains(person)).collect(Collectors.toUnmodifiableList());
            for (String availableNextPerson : availableNextPersons) {
                searchHappiestCombination(combinationCandidate.append(availableNextPerson));
            }
        }

    }

    @ToString
    public static class Ring<T> {
        private List<T> elements;

        private Ring(T... elements) {
            this.elements = Arrays.stream(elements).collect(Collectors.toUnmodifiableList());
        }

        private Ring(List<T> elements) {
            this.elements = elements.stream().collect(Collectors.toUnmodifiableList());
        }

        public static <T> Ring<T> of(T... elements) {
            return new Ring<>(elements);
        }

        public static <T> Ring<T> empty() {
            return new Ring<T>();
        }


        public static <T> List<T> normalize(List<T> elements) {
            return normalizeOrder(normalizeFirstElement(elements));
        }

        public static <T> List<T> normalizeFirstElement(List<T> elements) {
            if (elements.isEmpty()) {
                return Collections.emptyList();
            }

            // Normalize the first element by hashCode
            final Optional<Integer> offsetOptional = indexByLowestHashCode(elements);
            if (offsetOptional.isEmpty()) {
                throw new RuntimeException("Unexpected empty index");
            }
            final int offset = offsetOptional.get();
            final List<T> newList = new ArrayList<>(elements.size());
            newList.addAll(elements.subList(offset, elements.size()));
            newList.addAll(elements.subList(0, offset));
            return newList.stream().collect(Collectors.toUnmodifiableList());
        }

        public static <T> List<T> normalizeOrder(List<T> elements) {
            if (elements.isEmpty()) {
                return Collections.emptyList();
            }
            if (elements.size() == 1) {
                return elements;
            }

            final ArrayList<T> newList = new ArrayList<>(elements);

            // Normalize the direction of the elements by hashCode
            final int secondElementHashCode = newList.get(1).hashCode();
            final int lastElementHashCode = newList.get(newList.size() - 1).hashCode();
            if (secondElementHashCode >= lastElementHashCode) {
                Collections.reverse(newList);
                return normalizeFirstElement(newList);
            }
            return newList.stream().collect(Collectors.toUnmodifiableList());
        }

        public boolean isEmpty() {
            return elements.isEmpty();
        }

        private static Optional<Integer> indexByLowestHashCode(List<?> elements) {
            final Integer lowestHashCode = elements.stream().map(Object::hashCode).reduce(Integer::min).orElse(0);
            final Optional<?> first = elements.stream().filter(element -> element.hashCode() == lowestHashCode).findFirst();
            if (first.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(elements.indexOf(first.get()));
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ring<?> ring = (Ring<?>) o;

            if (elements.isEmpty() && ring.elements.isEmpty()) {
                return true;
            }

            return Objects.equals(normalize(elements), normalize(ring.elements));
        }

        @Override
        public int hashCode() {
            return Objects.hash(normalize(elements));
        }

        public int size() {
            return elements.size();
        }

        public T get(int index) {
            final int normalizedIndex = index >= 0 ? index % elements.size() : (elements.size() + (index % elements.size())) % elements.size();
            return elements.get(normalizedIndex);
        }

        public boolean contains(T element) {
            return elements.contains(element);
        }

        public Ring<T> append(T element) {
            final ArrayList<T> elements = new ArrayList<>(this.elements);
            elements.add(element);
            return new Ring<T>(elements);
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
