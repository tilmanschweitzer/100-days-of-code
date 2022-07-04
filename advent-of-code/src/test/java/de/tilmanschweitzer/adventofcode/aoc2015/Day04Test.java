package de.tilmanschweitzer.adventofcode.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day04Test {

    @Test
    public void md5() {
        assertEquals( "000001dbbfa3a5c83a2d506429c7b00e", Day04.md5("abcdef609043"));
        assertEquals( "000006136ef2ff3b291c85725f17325c", Day04.md5("pqrstuv1048970"));
    }

    @Test
    public void findHashStaringWith() {
        assertEquals( 609043, Day04.findAdventCoinForKey("abcdef"));
        assertEquals( 1048970, Day04.findAdventCoinForKey("pqrstuv"));
    }
}
