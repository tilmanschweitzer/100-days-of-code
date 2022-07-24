package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day03DeliveryOfPresentsToHouses.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day03DeliveryOfPresentsToHousesTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(2565, new Day03DeliveryOfPresentsToHouses().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(2639, new Day03DeliveryOfPresentsToHouses().getResultOfSecondPuzzle());
    }

    @Test
    public void testMoveFromInput() {
        assertEquals(Move.NORTH, Move.fromInput('^'));
        assertEquals(Move.SOUTH, Move.fromInput('v'));
        assertEquals(Move.EAST, Move.fromInput('>'));
        assertEquals(Move.WEST, Move.fromInput('<'));
    }


    @Test
    public void testNumberOfHousesFromMoves() {
        assertEquals(2, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMoves(Move.fromInput(">")));
        assertEquals(4, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMoves(Move.fromInput("^>v<")));
        assertEquals(2, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMoves(Move.fromInput("^v^v^v^v^v")));
    }

    @Test
    public void testNumberOfHousesFromMovesFromSantaAndRoboSanta() {
        assertEquals(3, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMovesFromSantaAndRoboSanta(Move.fromInput("^v")));
        assertEquals(3, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMovesFromSantaAndRoboSanta(Move.fromInput("^>v<")));
        assertEquals(11, Day03DeliveryOfPresentsToHouses.numberOfHousesFromMovesFromSantaAndRoboSanta(Move.fromInput("^v^v^v^v^v")));
    }
}
