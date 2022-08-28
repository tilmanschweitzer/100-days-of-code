package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.day.SingleLineAdventOfCodeDay;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day11SantasPasswordPolicy extends SingleLineAdventOfCodeDay<String, String> {

    public Day11SantasPasswordPolicy() {
        super(2015, 11);
    }

    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String findNextPassword(String lastPassword) {

        String passwordCandidate = incrementPassword(lastPassword);

        while (!isValidPassword(passwordCandidate) && passwordCandidate.length() == 8) {
            passwordCandidate = incrementPassword(passwordCandidate);
        }

        return passwordCandidate;
    }

    public static String incrementPassword(String lastPassword) {
        final List<Integer> passwordAsNums = Arrays.stream(lastPassword.split("")).map(s -> alphabet.indexOf(s)).collect(Collectors.toList());

        final int size = passwordAsNums.size();
        int i = size - 1;

        passwordAsNums.set(i, passwordAsNums.get(i) + 1);

        while (i >= 0 && passwordAsNums.get(i) >= alphabet.length()) {
            passwordAsNums.set(i, 0);
            i--;
            if (i >= 0) {
                passwordAsNums.set(i, passwordAsNums.get(i) + 1);
            } else {
                passwordAsNums.add(0, 0);
            }
        }
        return passwordAsNums.stream().map(num -> alphabet.charAt(num) + "").collect(Collectors.joining());
    }

    public static boolean isValidPassword(String password) {
        return exactlyEightLowercaseLetters(password) &&
                includesOneStraightOfThreeIncreasingLetters(password) &&
                notContainConfusingLetters(password) &&
                containTwoLetterPairs(password);
    }

    public static boolean containTwoLetterPairs(String password) {
        return IntStream.range(0, password.length() - 1).boxed().filter(index -> {
            char firstChar = password.charAt(index);
            char secondChar = password.charAt(index + 1);
            if (firstChar != secondChar) {
                return false;
            }
            if (index < password.length() - 2 && password.charAt(index + 2) == secondChar) {
                return false;
            }
            if (index > 0 && password.charAt(index - 1) == firstChar) {
                return false;
            }
            return true;
        }).count() >= 2;
    }

    public static boolean notContainConfusingLetters(String password) {
        return !(password.contains("i") || password.contains("o") || password.contains("l"));
    }

    public static boolean exactlyEightLowercaseLetters(String password) {
        return password.length() == 8 && password.toLowerCase().equals(password);
    }

    public static boolean includesOneStraightOfThreeIncreasingLetters(String password) {
        return IntStream.range(0, password.length() - 2).boxed().anyMatch(index -> {
            int firstNum = numForChar(password.charAt(index));
            int secondNum = numForChar(password.charAt(index + 1));
            int thirdNum = numForChar(password.charAt(index + 2));
            return firstNum + 1 == secondNum && secondNum + 1 == thirdNum;
        });
    }

    public static int numForChar(char c) {
        return alphabet.indexOf(c);
    }


    @Override
    public String getResultOfFirstPuzzle(final String input) {
        return findNextPassword(input);
    }

    @Override
    public String getResultOfSecondPuzzle(final String input) {
        return findNextPassword(findNextPassword(input));
    }

    @Override
    public String parseLine(String line) {
        return line;
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day11-input.txt");
    }
}
