package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day16AuntSue.AuntSue;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day16AuntSueTest {

    @Test
    public void parse() {
        assertEquals(new AuntSue(1, Map.of("cars", 9, "akitas", 3, "goldfish", 0)), AuntSue.parse("Sue 1: cars: 9, akitas: 3, goldfish: 0"));
        assertEquals(new AuntSue(2, Map.of("akitas", 9, "children", 3, "samoyeds", 9)), AuntSue.parse("Sue 2: akitas: 9, children: 3, samoyeds: 9"));
        assertEquals(new AuntSue(3, Map.of("trees", 6, "cars", 6, "children", 4)), AuntSue.parse("Sue 3: trees: 6, cars: 6, children: 4"));
    }

    @Test
    public void contradicts() {
        final AuntSue auntSue = AuntSue.parse("Sue 1: cars: 9, akitas: 3, goldfish: 0");

        // No contradictions
        assertFalse(auntSue.contradictsResultFromMFCSAM(Map.of()));
        assertFalse(auntSue.contradictsResultFromMFCSAM(Map.of("cars", 9)));
        assertFalse(auntSue.contradictsResultFromMFCSAM(Map.of("children", 3)));

        // With contradictions
        assertTrue(auntSue.contradictsResultFromMFCSAM(Map.of("cars", 8)));
        assertTrue(auntSue.contradictsResultFromMFCSAM(Map.of("akitas", 0)));
        assertTrue(auntSue.contradictsResultFromMFCSAM(Map.of("goldfish", 1)));
        assertTrue(auntSue.contradictsResultFromMFCSAM(Map.of("cars", 9, "akitas", 3, "goldfish", 1)));

        assertTrue(auntSue.contradictsResultFromMFCSAM(Day16AuntSue.resultFromMFCSAM));

    }
}
