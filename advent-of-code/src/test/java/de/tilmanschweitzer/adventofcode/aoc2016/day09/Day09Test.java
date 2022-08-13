package de.tilmanschweitzer.adventofcode.aoc2016.day09;

import de.tilmanschweitzer.adventofcode.common.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day09Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(107035, new Day09().getResultOfFirstPuzzle());
    }

    @Test
    public void decompress() {
        assertEquals("ADVENT", Day09.decompress("ADVENT"));
        assertEquals("ABBBBBC", Day09.decompress("A(1x5)BC"));
        assertEquals("BBBBBBBBBBBBBBB", Day09.decompress("(1x5)B(1x5)B(1x5)B"));

        assertEquals("XYZXYZXYZ", Day09.decompress("(3x3)XYZ"));
        assertEquals("ABCBCDEFEFG", Day09.decompress("A(2x2)BCD(2x2)EFG"));
        assertEquals("(1x3)A", Day09.decompress("(6x1)(1x3)A"));
        assertEquals("X(3x3)ABC(3x3)ABCY", Day09.decompress("X(8x2)(3x3)ABCY"));
        assertEquals(Day09.repeat(8, "(22x7)(4x15)XOPG(7x9)JDPAKGM(8x8)ALGCJRZQ(38x1)(4x10)VNSW(12x10)BZPAZABYKIDJ(3x14)IHF(40x15)(34x8)UGTIHCTVONZPPIWUAEGHGFJUNTIMIELOLW(6x1)XLMMKD"), Day09.decompress("(143x8)(22x7)(4x15)XOPG(7x9)JDPAKGM(8x8)ALGCJRZQ(38x1)(4x10)VNSW(12x10)BZPAZABYKIDJ(3x14)IHF(40x15)(34x8)UGTIHCTVONZPPIWUAEGHGFJUNTIMIELOLW(6x1)XLMMKD"));
    }

    @Test
    public void decompressLength() {
        assertEquals(6, Day09.decompressLength("ADVENT"));
        assertEquals("ABBBBBC".length(), Day09.decompressLength("A(1x5)BC"));
        assertEquals("XYZXYZXYZ".length(), Day09.decompressLength("(3x3)XYZ"));
        assertEquals("ABCBCDEFEFG".length(), Day09.decompressLength("A(2x2)BCD(2x2)EFG"));
        assertEquals("(1x3)A".length(), Day09.decompressLength("(6x1)(1x3)A"));
        assertEquals("X(3x3)ABC(3x3)ABCY".length(), Day09.decompressLength("X(8x2)(3x3)ABCY"));
        assertEquals(Day09.repeat(8, "(22x7)(4x15)XOPG(7x9)JDPAKGM(8x8)ALGCJRZQ(38x1)(4x10)VNSW(12x10)BZPAZABYKIDJ(3x14)IHF(40x15)(34x8)UGTIHCTVONZPPIWUAEGHGFJUNTIMIELOLW(6x1)XLMMKD").length(), Day09.decompressLength("(143x8)(22x7)(4x15)XOPG(7x9)JDPAKGM(8x8)ALGCJRZQ(38x1)(4x10)VNSW(12x10)BZPAZABYKIDJ(3x14)IHF(40x15)(34x8)UGTIHCTVONZPPIWUAEGHGFJUNTIMIELOLW(6x1)XLMMKD"));
    }

    @Test
    public void extractMarker() {
        assertEquals("(1x5)", Day09.extractNextMarker("A(1x5)BC"));
    }

    @Test
    public void parseMarker() {
        assertEquals(Pair.of(3, 17), Day09.parseMarker("(3x17)"));
        assertEquals(Pair.of(13, 7), Day09.parseMarker("(13x7)"));
    }
}
