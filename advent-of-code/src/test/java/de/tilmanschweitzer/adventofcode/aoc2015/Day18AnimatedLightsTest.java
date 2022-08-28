package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day18AnimatedLights.AnimatedLightGrid;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day18AnimatedLights.LightCoordinate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18AnimatedLightsTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(768, new Day18AnimatedLights().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(781, new Day18AnimatedLights().getResultOfSecondPuzzle());
    }

    static final String input = ".#.#.#\n" +
            "...##.\n" +
            "#....#\n" +
            "..#...\n" +
            "#.#..#\n" +
            "####..";

    @Test
    public void parse() {
        final AnimatedLightGrid animatedLightGrid = AnimatedLightGrid.parse(input);
        assertEquals(input, animatedLightGrid.toString());
    }

    @Test
    public void getNeighbors() {
        final AnimatedLightGrid animatedLightGrid = AnimatedLightGrid.parse(input);
        assertEquals(Set.of(of(0, 1), of(1, 1), of(1, 0)), animatedLightGrid.getNeighbors(0, 0));
        assertEquals(Set.of(of(2, 2), of(2, 3), of(2, 4), of(3, 2), of(3, 4), of(4, 2), of(4, 3), of(4, 4)), animatedLightGrid.getNeighbors(3, 3));
    }

    @Test
    public void countNeighbors() {
        final AnimatedLightGrid animatedLightGrid = AnimatedLightGrid.parse(input);
        assertEquals(1, animatedLightGrid.countNeighbors(of(0, 0), true));
        assertEquals(2, animatedLightGrid.countNeighbors(of(1, 1), true));
        assertEquals(2, animatedLightGrid.countNeighbors(of(0, 4), true));
        assertEquals(4, animatedLightGrid.countNeighbors(of(4, 0), true));
    }

    @Test
    public void nextGrid() {
        final AnimatedLightGrid animatedLightGrid = AnimatedLightGrid.parse(input);

        final AnimatedLightGrid animatedLightGridAfterStep1 = animatedLightGrid.nextGrid();
        final String expectedGridAfterStep1 = "..##..\n" +
                "..##.#\n" +
                "...##.\n" +
                "......\n" +
                "#.....\n" +
                "#.##..";
        assertEquals(expectedGridAfterStep1, animatedLightGridAfterStep1.toString());

        final AnimatedLightGrid animatedLightGridAfterStep2 = animatedLightGridAfterStep1.nextGrid();
        final String expectedGridAfterStep2 = "..###.\n" +
                "......\n" +
                "..###.\n" +
                "......\n" +
                ".#....\n" +
                ".#....";

        assertEquals(expectedGridAfterStep2, animatedLightGridAfterStep2.toString());

        final AnimatedLightGrid animatedLightGridAfterStep3 = animatedLightGridAfterStep2.nextGrid();
        final String expectedGridAfterStep3 = "...#..\n" +
                "......\n" +
                "...#..\n" +
                "..##..\n" +
                "......\n" +
                "......";

        assertEquals(expectedGridAfterStep3, animatedLightGridAfterStep3.toString());

        final AnimatedLightGrid animatedLightGridAfterStep4 = animatedLightGridAfterStep3.nextGrid();
        final String expectedGridAfterStep4 = "......\n" +
                "......\n" +
                "..##..\n" +
                "..##..\n" +
                "......\n" +
                "......";

        assertEquals(expectedGridAfterStep4, animatedLightGridAfterStep4.toString());
    }


    @Test
    public void count() {
        final AnimatedLightGrid animatedLightGrid = AnimatedLightGrid.parse(input);
        final AnimatedLightGrid animatedLightGridAfterStep4 = animatedLightGrid.nextGrid().nextGrid().nextGrid().nextGrid();
        assertEquals(15, animatedLightGrid.count());
        assertEquals(4, animatedLightGridAfterStep4.count());
    }
}
