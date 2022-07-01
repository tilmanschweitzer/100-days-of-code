package de.tilmanschweitzer.adventofcode.aoc2020;

import de.tilmanschweitzer.adventofcode.app.AdventOfCodeDay;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.stream.Collectors.toUnmodifiableList;

/*
byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)
 */
public class Day04 extends AbstractDay2020<List<Day04.PassportField>> {

    private static final char TREE = '#';


    public static List<Passport> passportsFromFieldsList(final List<List<PassportField>> lines) {
        final List<Passport> passports = new ArrayList<>();
        Passport currentPassport = new Passport();

        for (List<PassportField> line : lines) {
            if (line.size() == 0) {
                passports.add(currentPassport);
                currentPassport = new Passport();
            } else {
                currentPassport.addFields(line);
            }
        }

        if (!passports.contains(currentPassport) && currentPassport.fields.size() > 0) {
            passports.add(currentPassport);
        }

        return passports;
    }

    @Override
    protected long getResultOfFirstPuzzle(final List<List<PassportField>> lines) {
        final List<Passport> passports = passportsFromFieldsList(lines);
        return passports.stream().filter(Passport::hasNecessaryFields).count();
    }

    @Override
    protected long getResultOfSecondPuzzle(List<List<PassportField>> lines) {
        final List<Passport> passports = passportsFromFieldsList(lines);
        return passports.stream().filter(Passport::isValid).count();
    }

    @Override
    protected List<PassportField> parseLine(String line) {
        final String[] splittedLine = line.split(" ");
        if (splittedLine.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(splittedLine)
                .filter(Predicate.not(String::isBlank))
                .map(fieldString -> {
                    final String[] splittedField = fieldString.split(":");
                    if (splittedField.length != 2) {
                        throw new RuntimeException("Unexpected size of splittedField: " + splittedField.length + " for field: '" + fieldString + "'");
                    }
                    return new PassportField(splittedField[0], splittedField[1]);
                }).collect(Collectors.toList());
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("day04-input.txt");
    }



    public static class PassportField {
        final String fieldName;
        final String fieldValue;

        public PassportField(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public boolean isValid() {
            if (fieldName.equals("byr")) {
                final int birthYear = Integer.parseInt(fieldValue);
                return birthYear >= 1920 &&  birthYear <= 2002;
            }
            if (fieldName.equals("iyr")) {
                final int issueYear = Integer.parseInt(fieldValue);
                return issueYear >= 2010 && issueYear <= 2020;
            }
            if (fieldName.equals("eyr")) {
                final int expirationYear = Integer.parseInt(fieldValue);
                return expirationYear >= 2020 && expirationYear <= 2030;
            }
            if (fieldName.equals("hgt")) {
                final String height = fieldValue;
                if (height.endsWith("cm")) {
                    int heightInCm = Integer.parseInt(height.replace("cm", ""));
                    return heightInCm >= 150 && heightInCm <= 193;
                } else if (height.endsWith("in")) {
                    int heightInInch = Integer.parseInt(height.replace("in", ""));
                    return heightInInch >= 59 && heightInInch <= 76;
                } else {
                    return false;
                }
            }
            if (fieldName.equals("hcl")) {
                final String hairColor = fieldValue;
                return hairColor.matches("^#[\\da-f]{6}$");
            }
            if (fieldName.equals("ecl")) {
                final List<String> validEyeColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                final String eyeColor = fieldValue;
                return validEyeColors.contains(eyeColor);
            }
            if (fieldName.equals("pid")) {
                final String passportId = fieldValue;
                return passportId.length() == 9;
            }
            if (fieldName.equals("cid")) {
                return true;
            }
            throw new RuntimeException("Unknown field name: " + fieldName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PassportField that = (PassportField) o;
            return Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldValue, that.fieldValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fieldName, fieldValue);
        }

        @Override
        public String toString() {
            return "PassportField{" +
                    "" + fieldName + '\'' +
                    ":" + fieldValue + '\'' +
                    '}';
        }
    }

    public static class Passport {
        final Map<String, PassportField> fields = new HashMap<>();

        public static Passport from(List<PassportField> fields) {
            final Passport passport = new Passport();
            passport.addFields(fields);
            return passport;
        }

        public List<PassportField> getFields() {
            return fields.values().stream().collect(toUnmodifiableList());
        }

        void addFields(List<PassportField> fields) {
            for (PassportField field : fields) {
                this.fields.put(field.fieldName, field);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Passport passport = (Passport) o;
            return Objects.equals(fields, passport.fields);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fields);
        }

        @Override
        public String toString() {
            return "Passport{" +
                    "fields=" + fields +
                    '}';
        }

        public List<String> getAvailableFieldNames() {
            return getFields().stream().map(PassportField::getFieldName).collect(toUnmodifiableList());
        }

        public boolean hasNecessaryFields() {
            final List<String> necessaryFieldNames = List.of("byr",
                    "iyr",
                    "eyr",
                    "hgt",
                    "hcl",
                    "ecl",
                    "pid"
            );

            for (String necessaryFieldName : necessaryFieldNames) {
                if (!fields.containsKey(necessaryFieldName)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValid() {
            if (!hasNecessaryFields()) {
                return false;
            }

            return fields.values().stream().map(PassportField::isValid).reduce(Boolean::logicalAnd).orElse(false);
        }
    }


}
