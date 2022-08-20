package de.tilmanschweitzer.adventofcode.common;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converters {

    public static Stream<Character> stringToCharStream(String str) {
        return Arrays.stream(str.split("")).map(s -> s.charAt(0));
    }

    public static List<Character> stringToCharList(String str) {
        return stringToCharStream(str).collect(Collectors.toUnmodifiableList());
    }

    public static Set<Character> stringToCharSet(String str) {
        return stringToCharStream(str).collect(Collectors.toUnmodifiableSet());
    }

    public static String charsToString(Character... chars) {
        return charsToString(Arrays.stream(chars));
    }

    public static String charsToString(Stream<Character> chars) {
        return chars.map(Object::toString).collect(Collectors.joining());
    }
}
