package de.tilmanschweitzer.adventofcode.aoc2020.app;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day02 {

    private static final String LINE_SEPARATOR = "\n";

    public static void run() {
        final InputStream systemResourceAsStream = getSystemResourceAsStream("day02-input.txt");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(systemResourceAsStream)))) {
            final List<PasswordPolicyAndPassword> passwordsAndPolicies = reader.lines().map(Day02::parseSinglePasswordAndPolicy).collect(toUnmodifiableList());

            final long validPasswordMethodA = passwordsAndPolicies.stream().filter(passwordAndPolicy -> passwordAndPolicy.policy.validateMethodA(passwordAndPolicy.password)).count();
            System.out.println("validPasswords (Method A): " + validPasswordMethodA);

            final long validPasswordMethodB = passwordsAndPolicies.stream().filter(passwordAndPolicy -> passwordAndPolicy.policy.validateMethodB(passwordAndPolicy.password)).count();
            System.out.println("validPasswords (Method B): " + validPasswordMethodB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PasswordPolicyAndPassword> parsePasswordAndPolicy(String testInput) {
        return Arrays.stream(testInput.split(LINE_SEPARATOR)).map(Day02::parseSinglePasswordAndPolicy).collect(toUnmodifiableList());
    }

    public static PasswordPolicyAndPassword parseSinglePasswordAndPolicy(String line) {
        final Pattern pattern = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");
        final Matcher matcher = pattern.matcher(line);

        Preconditions.checkArgument(matcher.find());

        final String password = matcher.group(4);
        final char policyChar = matcher.group(3).charAt(0);
        final int policyMin = Integer.parseInt(matcher.group(1));
        final int policyMax = Integer.parseInt(matcher.group(2));
        return new PasswordPolicyAndPassword(password, new PasswordPolicy(policyChar, policyMin, policyMax));
    }

    public static class PasswordPolicyAndPassword {
        final String password;
        final PasswordPolicy policy;

        public PasswordPolicyAndPassword(String password, PasswordPolicy policy) {
            this.password = password;
            this.policy = policy;
        }

        @Override
        public String toString() {
            return "PasswordPolicyAndPassword{" +
                    "password='" + password + '\'' +
                    ", policy=" + policy +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PasswordPolicyAndPassword that = (PasswordPolicyAndPassword) o;
            return password.equals(that.password) && policy.equals(that.policy);
        }

        @Override
        public int hashCode() {
            return Objects.hash(password, policy);
        }
    }

    public static class PasswordPolicy {
        final char c;
        final int valueA;
        final int valueB;

        public PasswordPolicy(char c, int valueA, int valueB) {
            this.c = c;
            this.valueA = valueA;
            this.valueB = valueB;
        }

        @Override
        public String toString() {
            return "PasswordPolicy{" +
                    "c=" + c +
                    ", valueA=" + valueA +
                    ", valueB=" + valueB +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PasswordPolicy that = (PasswordPolicy) o;
            return c == that.c && valueA == that.valueA && valueB == that.valueB;
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, valueA, valueB);
        }

        public boolean validateMethodA(String password) {
            if (password.isEmpty()) {
                return false;
            }
            final long countPolicyChar = (int) Arrays.stream(password.split("")).filter(passwordChar -> passwordChar.charAt(0) == c).count();
            return valueA <= countPolicyChar && countPolicyChar <= valueB;
        }

        public boolean validateMethodB(String password) {
            if (password.isEmpty()) {
                return false;
            }
            final char valueAChar = password.charAt(valueA - 1);
            final char valueBChar = password.charAt(valueB - 1);
            return valueAChar == c ^ valueBChar == c;
        }
    }

}
