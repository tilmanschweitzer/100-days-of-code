package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day09ShortestDeliveryDistance.Route;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.tilmanschweitzer.adventofcode.aoc2015.Day09ShortestDeliveryDistance.*;
import static org.junit.jupiter.api.Assertions.*;

class Day09ShortestDeliveryDistanceTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(251, new Day09ShortestDeliveryDistance().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(898, new Day09ShortestDeliveryDistance().getResultOfSecondPuzzle());
    }

    @Test
    public void parseLine() {
        assertEquals(new Route("London", "Dublin", 464), Route.parse("London to Dublin = 464"));
    }

    @Test
    public void getShortestTripForTwoCities() {
        final List<Route> allRoutes = List.of(
                new Route("Dublin", "London", 463)
        );
        final Trip trip = getShortestTrip(allRoutes);

        assertEquals(463, trip.getDistance());
    }

    @Test
    public void getShortestTripForThreeCities() {
        final List<Route> allRoutes = List.of(
            new Route("London", "Dublin", 464),
            new Route("London", "Belfast", 518),
            new Route("Dublin", "Belfast", 141)
        );
        final Trip trip = getShortestTrip(allRoutes);

        final Trip expectedTrip = new Trip(
            new Route("London", "Dublin", 464),
            new Route("Dublin", "Belfast", 141)
        );

        assertEquals(expectedTrip, trip);
        assertEquals(605, trip.getDistance());
    }

    @Test
    public void getShortestTripForFourCities() {
        final List<Route> allRoutes = List.of(
                new Route("Barcelona", "Paris", 1035),
                new Route("Barcelona", "Brussels", 1338),
                new Route("Barcelona", "Amsterdam", 1535),

                new Route("Paris", "Brussels", 313),
                new Route("Paris", "Amsterdam", 516),

                new Route("Amsterdam", "Brussels", 210)
        );
        final Trip trip = getShortestTrip(allRoutes);

        final Trip expectedTrip = new Trip(
                new Route("Barcelona", "Paris", 1035),
                new Route("Paris", "Brussels", 313),
                new Route("Brussels", "Amsterdam", 210)
        );

        assertEquals(expectedTrip, trip);
        assertEquals(1558, trip.getDistance());
    }

    @Test
    public void getLongestTripForThreeCities() {
        final List<Route> allRoutes = List.of(
                new Route("London", "Dublin", 464),
                new Route("London", "Belfast", 518),
                new Route("Dublin", "Belfast", 141)
        );
        final Trip trip = getLongestTrip(allRoutes);

        final Trip expectedTrip = new Trip(
                new Route("Dublin", "London", 464),
                new Route("London", "Belfast", 518)
        );

        assertEquals(expectedTrip, trip);
        assertEquals(982, trip.getDistance());
    }
}
