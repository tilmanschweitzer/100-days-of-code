package de.tilmanschweitzer.adventofcode.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashesTest {
    @Test
    void md5() {
        assertEquals("000001dbbfa3a5c83a2d506429c7b00e", Hashes.md5("abcdef609043"));
        assertEquals("000006136ef2ff3b291c85725f17325c", Hashes.md5("pqrstuv1048970"));
        assertEquals("00000155f8105dff7f56ee10fa9b9abd", Hashes.md5("abc3231929"));
        assertEquals("000008f82c5b3924a1ecbebf60344e00", Hashes.md5("abc5017308"));
    }


}
