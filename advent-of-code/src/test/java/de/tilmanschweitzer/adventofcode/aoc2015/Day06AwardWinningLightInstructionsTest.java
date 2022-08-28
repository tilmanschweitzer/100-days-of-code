package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day06AwardWinningLightInstructions.*;
import org.junit.jupiter.api.Test;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day06AwardWinningLightInstructions.LightInstruction.Instruction.*;
import static de.tilmanschweitzer.adventofcode.aoc2015.Day06AwardWinningLightInstructions.LightInstruction.parseLightInstruction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day06AwardWinningLightInstructionsTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(569999, new Day06AwardWinningLightInstructions().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(17836115, new Day06AwardWinningLightInstructions().getResultOfSecondPuzzle());
    }

    @Test
    void parseLine() {
        assertEquals(new LightInstruction(new LightCoordinate(0, 0), new LightCoordinate(999, 0), TURN_ON), parseLightInstruction("turn on 0,0 through 999,0"));
        assertEquals(new LightInstruction(new LightCoordinate(123, 17), new LightCoordinate(234, 23), TOGGLE), parseLightInstruction("toggle 123,17 through 234,23"));
        assertEquals(new LightInstruction(new LightCoordinate(17, 123), new LightCoordinate(23, 234), TURN_OFF), parseLightInstruction("turn off 17,123 through 23,234"));
    }

    @Test
    void testBasicLightGrid_toString() {
        final LightGrid lightGrid = new BasicLightGrid(3);
        final String expectedOutput = "   \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    void testBasicLightGrid_applyInstruction() {
        final LightGrid lightGrid = new BasicLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,0 through 0,1"));

        final String expectedOutput = "** \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());

        assertTrue(lightGrid.lightGrid.containsKey(new LightCoordinate(0, 0)));
    }

    @Test
    void testBasicLightGrid_applyCrossInstructions() {
        final LightGrid lightGrid = new BasicLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));


        final String expectedOutput = " * \n" +
                "***\n" +
                " * ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    void testBasicLightGrid_applyInvertedCrossInstructions() {
        final LightGrid lightGrid = new BasicLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));
        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));


        final String expectedOutput = "* *\n" +
                "   \n" +
                "* *";
        assertEquals(expectedOutput, lightGrid.toString());
    }


    @Test
    void testBasicLightGrid_count() {
        final LightGrid lightGrid = new BasicLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));

        assertEquals(5L, lightGrid.count());

        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));
        assertEquals(4L, lightGrid.count());
    }


    @Test
    void testBrightnessLightGrid_toString() {
        final LightGrid lightGrid = new BrightnessLightGrid(3);
        final String expectedOutput = "   \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    void testBrightnessLightGrid_applyInstruction() {
        final LightGrid lightGrid = new BrightnessLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,0 through 0,1"));

        final String expectedOutput = "11 \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());

        assertTrue(lightGrid.lightGrid.containsKey(new LightCoordinate(0, 0)));
    }

    @Test
    void testBrightnessLightGrid_applyCrossInstructions() {
        final LightGrid lightGrid = new BrightnessLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));


        final String expectedOutput = " 1 \n" +
                "121\n" +
                " 1 ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    void testBrightnessLightGrid_applyInvertedCrossInstructions() {
        final LightGrid lightGrid = new BrightnessLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));
        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));


        final String expectedOutput = "232\n" +
                "343\n" +
                "232";
        assertEquals(expectedOutput, lightGrid.toString());
    }


    @Test
    void testBrightnessLightGrid_count() {
        final LightGrid lightGrid = new BrightnessLightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));

        assertEquals(6L, lightGrid.count());

        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));
        assertEquals(24L, lightGrid.count());
    }

}
