package de.tilmanschweitzer.adventofcode.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    public static List<Character> stringToCharList(String str) {
        return Arrays.stream(str.split("")).map(s -> s.charAt(0)).collect(Collectors.toUnmodifiableList());
    }
}
