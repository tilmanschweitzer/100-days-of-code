package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

public class Day09 extends MultiLineAdventOfCodeDay<Day09.Route> {


    @Override
    public long getResultOfFirstPuzzle(final List<Route> input) {
        return getShortestTrip(input).getDistance();
    }

    @Override
    public long getResultOfSecondPuzzle(final List<Route> input) {
        return 0;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day09-input.txt");
    }

    @Override
    protected Route parseLine(String line) {
        return Route.parse(line);
    }

    public static Trip getShortestTrip(final List<Route> routes) {
        final List<Route> reverseRoutes = routes.stream().map(Route::reverse).collect(toUnmodifiableList());
        final List<Route> allRoutes = Stream.of(routes, reverseRoutes).flatMap(Collection::stream).collect(toUnmodifiableList());

        final List<String> allCities = Sets.union(getAllStarts(allRoutes), getAllEnds(allRoutes)).stream().sorted().collect(toUnmodifiableList());

        final int tripLength = allCities.size() - 1;
        final TripSearchHelper tripSearchHelper = new TripSearchHelper(tripLength);

        final Map<String, List<Route>> routesByStartCity = allRoutes.stream().collect(groupingBy(Route::getStart));

        for (String startCity : allCities) {
            final List<Route> startRoutes = routesByStartCity.get(startCity);

            for (Route startRoute : startRoutes) {
                final Stack<Route> currentTrip = new Stack<>();
                currentTrip.push(startRoute);
                searchForCandidates(tripSearchHelper, currentTrip, routesByStartCity);
            }
        }

        return tripSearchHelper.currentShortestTrip;
    }

    private static void searchForCandidates(TripSearchHelper tripSearchHelper, Stack<Route> currentTrip, Map<String, List<Route>> routesByStartCity) {
        if (currentTrip.size() == tripSearchHelper.tripLength) {
            tripSearchHelper.tryToUpdateShortestTrip(currentTrip);
            return;
        }
        final String nextStart = currentTrip.peek().getEnd();
        final List<Route> nextAvailableRoutes = routesByStartCity.getOrDefault(nextStart, emptyList());
        final Set<String> alreadyVisitedCities = getAllStarts(currentTrip);
        final List<Route> nextRoutesWithourAlreadyVisitedCities = nextAvailableRoutes.stream()
                .filter(route -> !alreadyVisitedCities.contains(route.getEnd()))
                .collect(toUnmodifiableList());
        for (Route nextRoute : nextRoutesWithourAlreadyVisitedCities) {
            currentTrip.push(nextRoute);
            searchForCandidates(tripSearchHelper, currentTrip, routesByStartCity);
            currentTrip.pop();
        }
    }

    public static class TripSearchHelper {
        final int tripLength;
        int currentShortestTripDistance = Integer.MAX_VALUE;
        Trip currentShortestTrip = Trip.emptyTrip();

        public TripSearchHelper(int tripLength) {
            this.tripLength = tripLength;
        }

        public boolean tryToUpdateShortestTrip(List<Route> candidateTrip) {
            if (candidateTrip.size() != tripLength) {
                throw new RuntimeException("Unexpected trip length " + candidateTrip.size());
            }
            final Trip trip = new Trip(candidateTrip);
            if (trip.getDistance() >= currentShortestTripDistance) {
                return false;
            }
            currentShortestTripDistance = trip.getDistance();
            currentShortestTrip = trip;
            return true;

        }
    }


    public static Set<String> getAllStarts(List<Route> trip) {
        return trip.stream().map(Route::getStart).collect(toUnmodifiableSet());
    }

    public static Set<String> getAllEnds(List<Route> trip) {
        return trip.stream().map(Route::getEnd).collect(toUnmodifiableSet());
    }

    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Route {
        final String start;
        final String end;
        final int distance;

        public Route(String start, String end, int distance) {
            this.start = start;
            this.end = end;
            this.distance = distance;
        }

        public static Route parse(String line) {
            final Pattern routePattern = Pattern.compile("^(\\w+)\\s+to\\s+(\\w+)\\s+=\\s+(\\d+)$");
            final Matcher routeMatcher = routePattern.matcher(line);
            if (!routeMatcher.find()) {
                throw new RuntimeException("Could not parse line: " + line);
            }
            return new Route(routeMatcher.group(1), routeMatcher.group(2), Integer.parseInt(routeMatcher.group(3)));
        }

        public static Route reverse(Route route) {
            return new Route(route.end, route.start, route.distance);
        }
    }

    public static class Trip {
        final List<Route> routes;

        public Trip(List<Route> routes) {
            checkRoutes(routes);
            this.routes = routes.stream().collect(toUnmodifiableList());
        }

        public Trip(Route... routes) {
            this(Arrays.stream(routes).collect(toUnmodifiableList()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trip trip = (Trip) o;
            return Objects.equals(routes, trip.routes) || Objects.equals(routes, reverse(trip.routes));
        }

        @Override
        public int hashCode() {
            return Objects.hash(routes);
        }

        public static List<Route> reverse(List<Route> routes) {
            return Lists.reverse(routes).stream().map(Route::reverse).collect(toUnmodifiableList());
        }

        public int getDistance() {
            return routes.stream().map(Route::getDistance).reduce(Integer::sum).orElse(0);
        }

        public static void checkRoutes(final List<Route> trip) {
            if (!allEndsMatchNextStart(trip)) {
                throw new RuntimeException("Starts and end don't match.");
            }
            if (!allStartsAreUnique(trip)) {
                throw new RuntimeException("Not all starts are unique");
            }
            if (!allEndsAreUnique(trip)) {
                throw new RuntimeException("Not all ends are unique");
            }
        }
        public static Trip emptyTrip() {
            return new Trip(emptyList());
        }

        public static boolean allEndsMatchNextStart(final List<Route> trip) {
            return IntStream.range(0, trip.size() - 1).boxed()
                    .filter(index -> !Objects.equals(trip.get(index).end, trip.get(index + 1).start))
                    .findAny().isEmpty();
        }

        public static boolean allStartsAreUnique(final List<Route> trip) {
            return trip.size() == getAllStarts(trip).size();
        }

        public static boolean allEndsAreUnique(final List<Route> trip) {
            return trip.size() == getAllEnds(trip).size();
        }
    }
}
