package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day14ReindeerOlympics.Reindeer;
import de.tilmanschweitzer.adventofcode.aoc2015.Day14ReindeerOlympics.ReindeerRace;
import de.tilmanschweitzer.adventofcode.aoc2015.Day14ReindeerOlympics.ReindeerScore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day14ReindeerOlympicsTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(2696, new Day14ReindeerOlympics().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(1084, new Day14ReindeerOlympics().getResultOfSecondPuzzle());
    }

    @Test
    public void parseReindeer() {
        assertEquals(new Reindeer("Rudolph", 22, 8, 165), Reindeer.parse("Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds."));
    }

    @Test
    public void distanceAfterSeconds() {
        final Reindeer comet = Reindeer.parse("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.");
        final Reindeer dancer = Reindeer.parse("Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.");

        assertEquals(14, comet.getDistanceAfterSeconds(1));
        assertEquals(16, dancer.getDistanceAfterSeconds(1));

        assertEquals(140, comet.getDistanceAfterSeconds(10));
        assertEquals(160, dancer.getDistanceAfterSeconds(10));

        assertEquals(140, comet.getDistanceAfterSeconds(11));
        assertEquals(176, dancer.getDistanceAfterSeconds(11));

        assertEquals(1120, comet.getDistanceAfterSeconds(1000));
        assertEquals(1056, dancer.getDistanceAfterSeconds(1000));
    }

    @Test
    public void pointsAfterSecondsInRace() {
        final Reindeer comet = Reindeer.parse("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.");
        final Reindeer dancer = Reindeer.parse("Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.");

        final List<ReindeerScore> actualScores = ReindeerRace.scoresAfterRaceDuration(List.of(comet, dancer), 1000);

        final List<ReindeerScore> expectedScores = List.of(
            new ReindeerScore(dancer, 689),
            new ReindeerScore(comet, 312)
        );

        assertEquals(expectedScores, actualScores);
    }
}
