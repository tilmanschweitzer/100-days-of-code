package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day19RudolphMedicine.Replacement;
import de.tilmanschweitzer.adventofcode.aoc2015.Day19RudolphMedicine.ReplacementAtPosition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19RudolphMedicineTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(518, new Day19RudolphMedicine().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(0, new Day19RudolphMedicine().getResultOfSecondPuzzle());
    }

    @Test
    void parse() {
        assertEquals(new Replacement("H", "HO"), Replacement.parse("H => HO"));
        assertEquals(new Replacement("H", "OH"), Replacement.parse("H => OH"));
        assertEquals(new Replacement("O", "HH"), Replacement.parse("O => HH"));
    }

    @Test
    void distinctReplacements() {
        final List<Replacement> replacements = Stream.of("H => HO", "H => OH", "O => HH")
                .map(Replacement::parse)
                .collect(Collectors.toUnmodifiableList());

        Set<String> result = Day19RudolphMedicine.distinctReplacements("HOH", replacements);

        assertEquals(Set.of("HOOH", "HOHO", "OHOH", "HHHH"), result);
    }

    @Test
    void findStepsToGenerate() {
        final List<Replacement> replacements = Stream.of("e => H", "e => O", "H => HO", "H => OH", "O => HH")
                .map(Replacement::parse)
                .collect(Collectors.toUnmodifiableList());

        final List<ReplacementAtPosition> result = Day19RudolphMedicine.findStepsToGenerate("e", "HOH", replacements);

        final List<ReplacementAtPosition> expectedResult = List.of(
                new ReplacementAtPosition(0, new Replacement("e", "O")),
                new ReplacementAtPosition(0, new Replacement("O", "HH")),
                new ReplacementAtPosition(0, new Replacement("H", "HO"))
        );

        assertEquals(expectedResult, result);

        final List<ReplacementAtPosition> result2 = Day19RudolphMedicine.findStepsToGenerate("e", "HOHOHO", replacements);

        assertEquals(6, result2.size());
    }
}
