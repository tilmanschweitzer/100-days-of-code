package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day13PerfectDinnerHappiness.SeatPreference;
import de.tilmanschweitzer.adventofcode.common.Ring;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13PerfectDinnerHappinessTest {

    final static String testInput = "Alice would gain 54 happiness units by sitting next to Bob.\n" +
            "Alice would lose 79 happiness units by sitting next to Carol.\n" +
            "Alice would lose 2 happiness units by sitting next to David.\n" +
            "Bob would gain 83 happiness units by sitting next to Alice.\n" +
            "Bob would lose 7 happiness units by sitting next to Carol.\n" +
            "Bob would lose 63 happiness units by sitting next to David.\n" +
            "Carol would lose 62 happiness units by sitting next to Alice.\n" +
            "Carol would gain 60 happiness units by sitting next to Bob.\n" +
            "Carol would gain 55 happiness units by sitting next to David.\n" +
            "David would gain 46 happiness units by sitting next to Alice.\n" +
            "David would lose 7 happiness units by sitting next to Bob.\n" +
            "David would gain 41 happiness units by sitting next to Carol.";

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(664, new Day13PerfectDinnerHappiness().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(640, new Day13PerfectDinnerHappiness().getResultOfSecondPuzzle());
    }

    @Test
    public void parseSeatPreference() {
        assertEquals(new SeatPreference("Alice", "Bob", 54), SeatPreference.parse("Alice would gain 54 happiness units by sitting next to Bob."));
        assertEquals(new SeatPreference("Alice", "Carol", -79), SeatPreference.parse("Alice would lose 79 happiness units by sitting next to Carol."));
        assertEquals(new SeatPreference("Carol", "David", 55), SeatPreference.parse("Carol would gain 55 happiness units by sitting next to David."));
    }

    @Test
    public void happinessForSeatCombination() {
        final List<SeatPreference> input = Arrays.stream(testInput.split("\n")).map(SeatPreference::parse).collect(Collectors.toUnmodifiableList());
        final Day13PerfectDinnerHappiness.SeatCombinationExplorer seatCombinationExplorer = new Day13PerfectDinnerHappiness.SeatCombinationExplorer(input);
        assertEquals(330, seatCombinationExplorer.happinessForSeatCombination(Ring.of("David", "Alice", "Bob", "Carol")));
    }

    @Test
    public void searchHappiestCombination() {
        final List<SeatPreference> input = Arrays.stream(testInput.split("\n")).map(SeatPreference::parse).collect(Collectors.toUnmodifiableList());
        final Ring<String> happiestCombination = new Day13PerfectDinnerHappiness.SeatCombinationExplorer(input).searchHappiestCombination();
        assertEquals(Ring.of("David", "Alice", "Bob", "Carol"), happiestCombination);
    }
}
