package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04MiningAdventCoinsTest {

    @Test
    void getResultOfFirstPuzzle() {
        assertEquals(254575, new Day04MiningAdventCoins().getResultOfFirstPuzzle());
    }

    @Test
    void getResultOfSecondPuzzle() {
        assertEquals(1038736, new Day04MiningAdventCoins().getResultOfSecondPuzzle());
    }

    @Test
    void findHashStaringWith() {
        assertEquals(609043, Day04MiningAdventCoins.findAdventCoinForKey("abcdef"));
        assertEquals(1048970, Day04MiningAdventCoins.findAdventCoinForKey("pqrstuv"));
    }
}
