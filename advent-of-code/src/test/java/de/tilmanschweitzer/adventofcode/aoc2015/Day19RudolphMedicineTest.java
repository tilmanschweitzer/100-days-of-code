package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day19RudolphMedicine.Replacement;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day19RudolphMedicineTest {

    @Test
    public void parse() {
        assertEquals(new Replacement("H", "HO"), Replacement.parse("H => HO"));
        assertEquals(new Replacement("H", "OH"), Replacement.parse("H => OH"));
        assertEquals(new Replacement("O", "HH"), Replacement.parse("O => HH"));
    }

    @Test
    public void distinctReplacements() {
        final List<Replacement> replacements = Stream.of("H => HO", "H => OH", "O => HH")
                .map(Replacement::parse)
                .collect(Collectors.toUnmodifiableList());

        Set<String> result = Day19RudolphMedicine.distinctReplacements("HOH", replacements);

        assertEquals(Set.of("HOOH", "HOHO", "OHOH", "HHHH"), result);
    }
}
