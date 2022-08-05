package de.tilmanschweitzer.adventofcode.common;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Hashes {
    public static String md5(String input) {
        return Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
    }
}
