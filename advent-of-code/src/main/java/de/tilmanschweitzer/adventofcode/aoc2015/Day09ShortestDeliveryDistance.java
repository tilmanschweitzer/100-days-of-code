package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.tilmanschweitzer.adventofcode.common.Pair;
import de.tilmanschweitzer.adventofcode.common.combination.OrderedCombinations;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

public class Day09ShortestDeliveryDistance extends MultiLineAdventOfCodeDay<Day09ShortestDeliveryDistance.Route, Integer> {

    public Day09ShortestDeliveryDistance() {
        super(2015, 9);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Route> input) {
        return getShortestTrip(input).getDistance();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Route> input) {
        return getLongestTrip(input).getDistance();
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
        return new ShortestTripSearchHelper(routes).search();
    }

    public static Trip getLongestTrip(final List<Route> routes) {
        return new LongestTripSearchHelper(routes).search();
    }

    public abstract static class TripSearchHelper {
        final List<Route> routes;
        final Map<Pair<String>, Route> routesByStartAndEndCity;


        final List<String> allCities;
        Trip currentShortestTrip = Trip.emptyTrip();

        public TripSearchHelper(List<Route> routes) {
            this.routes = Routes.union(routes, Routes.reverse(routes));
            this.allCities = Routes.allCities(this.routes);
            this.routesByStartAndEndCity = this.routes.stream().collect(toMap(route -> new Pair<>(route.start, route.end), Function.identity()));

        }

        public Trip search() {
            final Set<Trip> allTripPossibilities = OrderedCombinations.allCombinationsAsSet(new HashSet<>(allCities))
                    .stream()
                    .map(citiesCombination -> IntStream.range(1, citiesCombination.size())
                            .boxed()
                            .map(index -> {
                                final Pair<String> startAndEndCityKey = Pair.of(citiesCombination.get(index - 1), citiesCombination.get(index));
                                return routesByStartAndEndCity.get(startAndEndCityKey);
                            }).collect(toUnmodifiableList())
                    ).map(Trip::new)
                    .collect(toSet());


            for (Trip tripCandidate : allTripPossibilities) {
                tryToUpdateShortestTrip(tripCandidate);
            }

            return currentShortestTrip;
        }

        public boolean tryToUpdateShortestTrip(Trip candidateTrip) {
            if (candidateTrip.length() != allCities.size() - 1) {
                throw new RuntimeException("Unexpected trip length " + candidateTrip.length());
            }
            ;
            if (!checkAndUpdateCandidateCondition(candidateTrip)) {
                return false;
            }
            currentShortestTrip = candidateTrip;
            return true;

        }

        public abstract boolean checkAndUpdateCandidateCondition(Trip candidateTrip);
    }

    public static class ShortestTripSearchHelper extends TripSearchHelper {
        int currentShortestTripDistance = Integer.MAX_VALUE;

        public ShortestTripSearchHelper(List<Route> routes) {
            super(routes);
        }

        @Override
        public boolean checkAndUpdateCandidateCondition(Trip candidateTrip) {
            if (candidateTrip.getDistance() >= currentShortestTripDistance) {
                return false;
            }
            currentShortestTripDistance = candidateTrip.getDistance();
            return true;
        }
    }

    public static class LongestTripSearchHelper extends TripSearchHelper {
        int currentLongestTripDistance = 0;

        public LongestTripSearchHelper(List<Route> routes) {
            super(routes);
        }

        @Override
        public boolean checkAndUpdateCandidateCondition(Trip candidateTrip) {
            if (candidateTrip.getDistance() <= currentLongestTripDistance) {
                return false;
            }
            currentLongestTripDistance = candidateTrip.getDistance();
            return true;
        }
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

    public static class Routes {
        public static List<Route> reverse(List<Route> routes) {
            return Lists.reverse(routes).stream().map(Route::reverse).collect(toUnmodifiableList());
        }

        public static List<Route> union(List<Route>... routes) {
            return Stream.of(routes).flatMap(Collection::stream).collect(toUnmodifiableList());
        }

        public static List<String> allCities(List<Route> routes) {
            return Sets.union(getAllStarts(routes), getAllEnds(routes)).stream().sorted().collect(toUnmodifiableList());
        }

        public static Set<String> getAllStarts(List<Route> trip) {
            return trip.stream().map(Route::getStart).collect(toUnmodifiableSet());
        }

        public static Set<String> getAllEnds(List<Route> trip) {
            return trip.stream().map(Route::getEnd).collect(toUnmodifiableSet());
        }
    }

    public static class Trip {
        final List<Route> routes;
        final int distance;

        public Trip(List<Route> routes) {
            checkRoutes(routes);
            this.routes = routes.stream().collect(toUnmodifiableList());
            this.distance = getDistance(routes);
        }

        public Trip(Route... routes) {
            this(Arrays.stream(routes).collect(toUnmodifiableList()));
        }

        public int getDistance() {
            return distance;
        }

        public Trip appendRoute(final Route route) {
            final List<Route> routes = new ArrayList<>(this.routes);
            routes.add(route);
            return new Trip(routes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trip trip = (Trip) o;
            return Objects.equals(routes, trip.routes) || Objects.equals(routes, Routes.reverse(trip.routes));
        }

        @Override
        public int hashCode() {
            return Objects.hash(routes);
        }

        public static int getDistance(List<Route> routes) {
            return sum(routes.stream().map(Route::getDistance));
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
            return trip.size() == Routes.getAllStarts(trip).size();
        }

        public static boolean allEndsAreUnique(final List<Route> trip) {
            return trip.size() == Routes.getAllEnds(trip).size();
        }

        public int length() {
            return routes.size();
        }
    }
}
