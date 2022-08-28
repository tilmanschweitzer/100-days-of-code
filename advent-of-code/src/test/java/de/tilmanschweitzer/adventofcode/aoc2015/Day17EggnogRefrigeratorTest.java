package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day17EggnogRefrigerator.Refrigerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17EggnogRefrigeratorTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(1638, new Day17EggnogRefrigerator().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(17, new Day17EggnogRefrigerator().getResultOfSecondPuzzle());
    }

    @Test
    void findCombinations() {
        final Refrigerator refrigerator20 = Refrigerator.of(20);
        final Refrigerator refrigerator15 = Refrigerator.of(15);
        final Refrigerator refrigerator10 = Refrigerator.of(10);
        final Refrigerator refrigerator5A = Refrigerator.of(5);
        final Refrigerator refrigerator5B = Refrigerator.of(5);

        final Set<Set<Refrigerator>> expectedResult = Set.of(
                Set.of(refrigerator15, refrigerator10),
                Set.of(refrigerator20, refrigerator5A),
                Set.of(refrigerator20, refrigerator5B),
                Set.of(refrigerator15, refrigerator5A, refrigerator5B)
        );

        assertEquals(expectedResult, Day17EggnogRefrigerator.findCombinations(25, List.of(refrigerator5A, refrigerator5B, refrigerator10, refrigerator15, refrigerator20)));
    }

}
