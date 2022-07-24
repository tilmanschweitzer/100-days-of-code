package de.tilmanschweitzer.adventofcode.common;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

public class CollectionFunctions {

    public static int sum(Collection<Integer> nums) {
        return sum(nums.stream());
    }

    public static int sum(Stream<Integer> nums) {
        return nums.reduce(Integer::sum).orElse(0);
    }
}
