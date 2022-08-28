package de.tilmanschweitzer.adventofcode.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RingTest {

    @Test
    public void ring_equalsAndHashCode() {
        assertEquals(Ring.of(2, 3, 4, 1), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(3, 4, 1, 2), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(4, 1, 2, 3), Ring.of(1, 2, 3, 4));
        assertEquals(Ring.of(1, 2, 3, 4), Ring.of(1, 2, 3, 4));

        assertEquals(Ring.of(4, 3, 2, 1), Ring.of(1, 2, 3, 4));

        assertNotEquals(Ring.of(1, 3, 2, 4), Ring.of(1, 2, 3, 4));


        assertEquals(Ring.of(2, 3, 4, 1).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(3, 4, 1, 2).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(4, 1, 2, 3).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertEquals(Ring.of(1, 2, 3, 4).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
        assertNotEquals(Ring.of(1, 3, 2, 4).hashCode(), Ring.of(1, 2, 3, 4).hashCode());
    }

    @Test
    public void ring_elementAccess() {
        final Ring<Integer> ring = Ring.of(7, 6, 5);
        assertEquals(7, ring.get(0));
        assertEquals(6, ring.get(1));
        assertEquals(5, ring.get(2));
        assertEquals(7, ring.get(3));
        assertEquals(7, ring.get(99));

        assertEquals(5, ring.get(-1));
        assertEquals(6, ring.get(-2));
        assertEquals(7, ring.get(-3));
        assertEquals(7, ring.get(-99));
    }
}
