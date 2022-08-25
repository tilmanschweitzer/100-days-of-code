package de.tilmanschweitzer.adventofcode.aoc2017;

import de.tilmanschweitzer.adventofcode.aoc2017.Day07.TowerDescription;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

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

    final List<TowerDescription> towerDescriptions = Arrays.stream(testInput.split("\n")).map(TowerDescription::parse).collect(toUnmodifiableList());

    final String linearTestInput = "a (1) -> b\n" +
            "b (2) -> c\n" +
            "c (3) -> d\n" +
            "d (4) -> e\n" +
            "e (5)";

    final List<TowerDescription> linearTowerDescriptions = Arrays.stream(linearTestInput.split("\n")).map(TowerDescription::parse).collect(toUnmodifiableList());


    final String unbalanceInLeaf = "a (1) -> b, c, d\n" +
            "b (2) -> e, f, g\n" +
            "c (2) -> h, i, j\n" +
            "d (2) -> k, l, m\n" +
            "e (3)\n" +
            "f (3)\n" +
            "g (3)\n" +
            "h (3)\n" +
            "i (3)\n" +
            "j (3)\n" +
            "k (3)\n" +
            "l (3)\n" +
            "m (4)";

    final List<TowerDescription> unbalanceInLeafTowerDescriptions = Arrays.stream(unbalanceInLeaf.split("\n")).map(TowerDescription::parse).collect(toUnmodifiableList());


    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals("mkxke", new Day07().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals("268", new Day07().getResultOfSecondPuzzle());
    }

    @Test
    public void parse() {
        assertEquals(new TowerDescription("abcd", 10, emptyList()), TowerDescription.parse("abcd (10)"));
        assertEquals(new TowerDescription("xyz", 444, List.of("a", "b")), TowerDescription.parse("xyz (444) -> a, b"));
    }


    @Test
    public void findBottom() {
        assertEquals("tknk", Day07.findBottom(towerDescriptions).getName());
    }


    @Test
    public void weightOfNode() {
        assertEquals(251, Day07.weightOfNode(towerDescriptions, "ugml"));
        assertEquals(243, Day07.weightOfNode(towerDescriptions, "padx"));
        assertEquals(243, Day07.weightOfNode(towerDescriptions, "fwft"));
        assertEquals(15, Day07.weightOfNode(linearTowerDescriptions, "a"));
    }


    @Test
    public void weightsOfNode() {
        assertEquals(251, Day07.weightsOfNode(towerDescriptions, "ugml").collect(toUnmodifiableList()));
        assertEquals(243, Day07.weightsOfNode(towerDescriptions, "padx").collect(toUnmodifiableList()));
        assertEquals(243, Day07.weightsOfNode(towerDescriptions, "fwft").collect(toUnmodifiableList()));
        assertEquals(List.of(1, 2, 3, 4, 5), Day07.weightsOfNode(linearTowerDescriptions, "a").collect(toUnmodifiableList()));
    }


    @Test
    public void getUpdatedTowerDescriptionToBalanceWeight() {
        assertEquals(60, Day07.getUpdatedTowerDescriptionToBalanceWeight(towerDescriptions).getWeight());
    }


    @Test
    public void findUnbalancedTowerDescription() {
        assertEquals("ugml", Day07.findUnbalancedTowerDescription(towerDescriptions).getName());
        assertEquals("m", Day07.findUnbalancedTowerDescription(unbalanceInLeafTowerDescriptions).getName());
    }

    @Test
    public void hasUnbalancesSuccessors() {
        assertTrue(Day07.hasUnbalancesSuccessors(unbalanceInLeafTowerDescriptions, "a"));
        assertFalse(Day07.hasUnbalancesSuccessors(unbalanceInLeafTowerDescriptions, "b"));
        assertFalse(Day07.hasUnbalancesSuccessors(unbalanceInLeafTowerDescriptions, "c"));
        assertTrue(Day07.hasUnbalancesSuccessors(unbalanceInLeafTowerDescriptions, "d"));
    }


}
