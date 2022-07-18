package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day13.Ring;
import de.tilmanschweitzer.adventofcode.aoc2015.Day13.SeatPreference;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {

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
        assertEquals(0, new Day13().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(0, new Day13().getResultOfSecondPuzzle());
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
        final Day13.SeatCombinationExplorer seatCombinationExplorer = new Day13.SeatCombinationExplorer(input);
        assertEquals(330, seatCombinationExplorer.happinessForSeatCombination(Ring.of("David", "Alice", "Bob", "Carol")));
    }

    @Test
    public void searchHappiestCombination() {
        final List<SeatPreference> input = Arrays.stream(testInput.split("\n")).map(SeatPreference::parse).collect(Collectors.toUnmodifiableList());
        final Day13.SeatCombinationExplorer seatCombinationExplorer = new Day13.SeatCombinationExplorer(input);
        final Ring<String> happiestCombination = new Day13.SeatCombinationExplorer(input).searchHappiestCombination();
        assertEquals(Ring.of("David", "Alice", "Bob", "Carol"), happiestCombination);
    }

    @Test
    public void ring_equalsAndHashCode() {
        assertEquals(Ring.of(2, 3, 4, 1), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(3, 4, 1, 2), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(4, 1, 2, 3), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(1, 2, 3, 4), Ring.of(1, 2, 3, 4));

        assertEquals(Ring.of(4, 3, 2, 1), Ring.of(1, 2, 3, 4));

        assertNotEquals(Ring.of(1, 3, 2, 4), Ring.of(1, 2, 3, 4));


        assertEquals(Ring.of(2, 3, 4, 1).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(3, 4, 1, 2).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(4, 1, 2, 3).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(1, 2, 3, 4).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertNotEquals(Ring.of(1, 3, 2, 4).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
    }

    @Test
    public void ring_elementAccess() {
        final Ring<Integer> ring = Ring.of(7, 6, 5);
        assertEquals(7, ring.get(0));
        assertEquals(6, ring.get(1));
        assertEquals(5, ring.get(2));
        assertEquals(7, ring.get(3));
        assertEquals(7, ring.get(99));

        assertEquals(5, ring.get(-1));
        assertEquals(6, ring.get(-2));
        assertEquals(7, ring.get(-3));
        assertEquals(7, ring.get(-99));
    }
}
