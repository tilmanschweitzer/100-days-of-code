package de.tilmanschweitzer.adventofcode.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertersTest {

    @Test
    void stringToCharList() {
        assertEquals(List.of('U', 'L', 'L'), Converters.stringToCharList("ULL"));
    }

}
