package de.tilmanschweitzer.adventofcode.aoc2020;

import com.google.common.base.Preconditions;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day02 extends MultiLineAdventOfCodeDay<Day02.PasswordPolicyAndPassword, Long> {

    public Day02() {
        super(2020, 2);
    }

    @Override
    protected Long getResultOfFirstPuzzle(List<PasswordPolicyAndPassword> passwordsAndPolicies) {
        return passwordsAndPolicies.stream().filter(passwordAndPolicy -> passwordAndPolicy.policy.validateMethodA(passwordAndPolicy.password)).count();
    }

    @Override
    protected Long getResultOfSecondPuzzle(List<PasswordPolicyAndPassword> passwordsAndPolicies) {
        return passwordsAndPolicies.stream().filter(passwordAndPolicy -> passwordAndPolicy.policy.validateMethodB(passwordAndPolicy.password)).count();
    }

    @Override
    protected PasswordPolicyAndPassword parseLine(String line) {
        final Pattern pattern = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");
        final Matcher matcher = pattern.matcher(line);

        Preconditions.checkArgument(matcher.find());

        final String password = matcher.group(4);
        final char policyChar = matcher.group(3).charAt(0);
        final int policyMin = Integer.parseInt(matcher.group(1));
        final int policyMax = Integer.parseInt(matcher.group(2));
        return new PasswordPolicyAndPassword(password, new PasswordPolicy(policyChar, policyMin, policyMax));
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("day02-input.txt");
    }

    private static final String LINE_SEPARATOR = "\n";

    @EqualsAndHashCode
    @ToString
    public static class PasswordPolicyAndPassword {
        final String password;
        final PasswordPolicy policy;

        public PasswordPolicyAndPassword(String password, PasswordPolicy policy) {
            this.password = password;
            this.policy = policy;
        }
    }

    @EqualsAndHashCode
    @ToString
    public static class PasswordPolicy {
        final char c;
        final int valueA;
        final int valueB;

        public PasswordPolicy(char c, int valueA, int valueB) {
            this.c = c;
            this.valueA = valueA;
            this.valueB = valueB;
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
