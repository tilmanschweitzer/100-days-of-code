package de.tilmanschweitzer.adventofcode.aoc2020;

import de.tilmanschweitzer.adventofcode.aoc2020.Day04;
import de.tilmanschweitzer.adventofcode.aoc2020.Day04.Passport;
import de.tilmanschweitzer.adventofcode.aoc2020.Day04.PassportField;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(216, new Day04().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(150, new Day04().getResultOfSecondPuzzle());
    }

    static final String testInput =  "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
            "byr:1937 iyr:2017 cid:147 hgt:183cm\n" +
            "\n" +
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
            "hcl:#cfa07d byr:1929\n" +
            "\n" +
            "hcl:#ae17e1 iyr:2013\n" +
            "eyr:2024\n" +
            "ecl:brn pid:760753108 byr:1931\n" +
            "hgt:179cm\n" +
            "\n" +
            "hcl:#cfa07d eyr:2025 pid:166559648\n" +
            "iyr:2011 ecl:brn hgt:59in";

    static final String validPassports =  "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\n" +
            "hcl:#623a2f\n" +
            "\n" +
            "eyr:2029 ecl:blu cid:129 byr:1989\n" +
            "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\n" +
            "\n" +
            "hcl:#888785\n" +
            "hgt:164cm byr:2001 iyr:2015 cid:88\n" +
            "pid:545766238 ecl:hzl\n" +
            "eyr:2022\n" +
            "\n" +
            "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719";

    static final String invalidPassports = "eyr:1972 cid:100\n" +
            "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\n" +
            "\n" +
            "iyr:2019\n" +
            "hcl:#602927 eyr:1967 hgt:170cm\n" +
            "ecl:grn pid:012533040 byr:1946\n" +
            "\n" +
            "hcl:dab227 iyr:2012\n" +
            "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\n" +
            "\n" +
            "hgt:59cm ecl:zzz\n" +
            "eyr:2038 hcl:74454a iyr:2023\n" +
            "pid:3556412378 byr:2007";

    @Test
    public void parseLine_parsesTestInputAsExpected() {
        final Day04 day04 = new Day04();
        final List<List<PassportField>> passportFieldsByLine = Arrays.stream(testInput.split("\n")).map(day04::parseLine).collect(Collectors.toList());

        final List<PassportField> expectedFirstLine = List.of(
                new PassportField("ecl", "gry"),
                new PassportField("pid", "860033327"),
                new PassportField("eyr", "2020"),
                new PassportField("hcl", "#fffffd")
        );

        assertEquals(13, passportFieldsByLine.size());
        assertEquals(expectedFirstLine, passportFieldsByLine.get(0));
    }


    @Test
    public void passportsFromFieldsList_joinsPassportFieldsSeparatedByEmptyLines() {
        final Day04 day04 = new Day04();
        final List<List<PassportField>> passportFieldsByLine = Arrays.stream(testInput.split("\n")).map(day04::parseLine).collect(Collectors.toList());

        System.out.println(passportFieldsByLine);

        final List<Passport> passports = Day04.passportsFromFieldsList(passportFieldsByLine);

        System.out.println(passports);

        final Passport firstPassport = Passport.from(new ArrayList<>(List.of(
                new PassportField("ecl", "gry"),
                new PassportField("pid", "860033327"),
                new PassportField("eyr", "2020"),
                new PassportField("hcl", "#fffffd"),
                new PassportField("byr", "1937"),
                new PassportField("iyr", "2017"),
                new PassportField("cid", "147"),
                new PassportField("hgt", "183cm")
        )));

        final Passport lastPassport = Passport.from(new ArrayList<>(List.of(
                new PassportField("hcl", "#cfa07d"),
                new PassportField("eyr", "2025"),
                new PassportField("pid", "166559648"),
                new PassportField("iyr", "2011"),
                new PassportField("ecl", "brn"),
                new PassportField("hgt", "59in")
        )));

        assertEquals(4, passports.size());
        assertEquals(firstPassport, passports.get(0));
        assertEquals(lastPassport, passports.get(3));
    }

    @Test
    public void getResultOfFirstPuzzle_returns2forTestInput() {
        final Day04 day04 = new Day04();
        final List<List<PassportField>> passportFieldsByLine = Arrays.stream(testInput.split("\n")).map(day04::parseLine).collect(Collectors.toList());

        final long result = day04.getResultOfFirstPuzzle(passportFieldsByLine);

        assertEquals(2, result);
    }

    @Test
    public void getResultOfFirstPuzzle_returns4forValidPassports() {
        final Day04 day04 = new Day04();
        final List<List<PassportField>> passportFieldsByLine = Arrays.stream(validPassports.split("\n")).map(day04::parseLine).collect(Collectors.toList());

        final long result = day04.getResultOfSecondPuzzle(passportFieldsByLine);

        assertEquals(4, result);
    }

    @Test
    public void getResultOfFirstPuzzle_returns0forInvalidPassports() {
        final Day04 day04 = new Day04();
        final List<List<PassportField>> passportFieldsByLine = Arrays.stream(invalidPassports.split("\n")).map(day04::parseLine).collect(Collectors.toList());

        final long result = day04.getResultOfSecondPuzzle(passportFieldsByLine);

        assertEquals(0, result);
    }
}
