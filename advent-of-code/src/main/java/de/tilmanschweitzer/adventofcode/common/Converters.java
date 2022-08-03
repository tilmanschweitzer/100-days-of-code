package de.tilmanschweitzer.adventofcode.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converters {

    public static List<Character> stringToCharList(String str) {
        return Arrays.stream(str.split("")).map(s -> s.charAt(0)).collect(Collectors.toUnmodifiableList());
    }

    public static String charsToString(Character... chars) {
        return charsToString(Arrays.stream(chars));
    }

    public static String charsToString(Stream<Character> chars) {
        return chars.map(Object::toString).collect(Collectors.joining());
    }
}
