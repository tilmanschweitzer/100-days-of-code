package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.aoc2017.Day07.TowerDescription;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals("", new Day07().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals("", new Day07().getResultOfSecondPuzzle());
    }


    @Test
    public void parse() {
        assertEquals(new TowerDescription("abcd", 10, emptyList()), TowerDescription.parse("abcd (10)"));
        assertEquals(new TowerDescription("xyz", 444, List.of("a", "b")), TowerDescription.parse("xyz (444) -> a, b"));
    }


    @Test
    public void findBottom() {
        final String testInput = "pbga (66)\n" +
                "xhth (57)\n" +
                "ebii (61)\n" +
                "havc (66)\n" +
                "ktlj (57)\n" +
                "fwft (72) -> ktlj, cntj, xhth\n" +
                "qoyq (66)\n" +
                "padx (45) -> pbga, havc, qoyq\n" +
                "tknk (41) -> ugml, padx, fwft\n" +
                "jptl (61)\n" +
                "ugml (68) -> gyxo, ebii, jptl\n" +
                "gyxo (61)\n" +
                "cntj (57)";

        final List<TowerDescription> towerDescriptions = Arrays.stream(testInput.split("\n")).map(TowerDescription::parse).collect(Collectors.toUnmodifiableList());


        assertEquals("tknk", Day07.findBottom(towerDescriptions));
    }

}
