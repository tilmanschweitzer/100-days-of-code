package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightCoordinate;
import de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightGrid;
import de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightInstruction;
import org.junit.jupiter.api.Test;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightInstruction.Instruction.*;
import static de.tilmanschweitzer.adventofcode.aoc2015.Day06.LightInstruction.parseLightInstruction;
import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

    @Test
    public void parseLine() {
        assertEquals(new LightInstruction(new LightCoordinate(0,0), new LightCoordinate(999,0), TURN_ON), parseLightInstruction("turn on 0,0 through 999,0"));
        assertEquals(new LightInstruction(new LightCoordinate(123,17), new LightCoordinate(234,23), TOGGLE), parseLightInstruction("toggle 123,17 through 234,23"));
        assertEquals(new LightInstruction(new LightCoordinate(17,123), new LightCoordinate(23, 234), TURN_OFF), parseLightInstruction("turn off 17,123 through 23,234"));
    }

    @Test
    public void emptyLightGrid_toString() {
        final LightGrid lightGrid = new LightGrid(3);
        final String expectedOutput = "   \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    public void emptyLightGrid_applyInstruction() {
        final LightGrid lightGrid = new LightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,0 through 0,1"));

        final String expectedOutput = "** \n" +
                "   \n" +
                "   ";
        assertEquals(expectedOutput, lightGrid.toString());

        assertTrue(lightGrid.lightGrid.containsKey(new LightCoordinate(0,0)));
    }

    @Test
    public void emptyLightGrid_applyCrossInstructions() {
        final LightGrid lightGrid = new LightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));


        final String expectedOutput = " * \n" +
                "***\n" +
                " * ";
        assertEquals(expectedOutput, lightGrid.toString());
    }

    @Test
    public void emptyLightGrid_applyInvertedCrossInstructions() {
        final LightGrid lightGrid = new LightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));
        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));


        final String expectedOutput = "* *\n" +
                "   \n" +
                "* *";
        assertEquals(expectedOutput, lightGrid.toString());
    }


    @Test
    public void lightGrid_countLights() {
        final LightGrid lightGrid = new LightGrid(3);

        lightGrid.applyLightInstruction(parseLightInstruction("turn on 0,1 through 2,1"));
        lightGrid.applyLightInstruction(parseLightInstruction("turn on 1,0 through 1,2"));

        assertEquals(5L, lightGrid.countLights());

        lightGrid.applyLightInstruction(parseLightInstruction("toggle 0,0 through 2,2"));
        assertEquals(4L, lightGrid.countLights());
    }

}
