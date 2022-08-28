package de.tilmanschweitzer.adventofcode.day;

import lombok.EqualsAndHashCode;

import java.io.InputStream;

public abstract class AdventOfCodeDay<I, A> {

    final YearAndDay yearAndDay;

    @EqualsAndHashCode
    public static class YearAndDay {
        final int year;
        final int day;

        public YearAndDay(int year, int day) {
            this.year = year;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public int getDay() {
            return day;
        }
    }

    protected AdventOfCodeDay(YearAndDay yearAndDay) {
        this.yearAndDay = yearAndDay;
    }

    protected AdventOfCodeDay(int year, int day) {
        this(new YearAndDay(year, day));
    }

    public int getYear() {
        return yearAndDay.getYear();
    }

    public int getDay() {
        return yearAndDay.getDay();
    }

    public YearAndDay getYearAndDay() {
        return yearAndDay;
    }

    public void runFirstPuzzle() {
        System.out.println("Result of " + this.getClass().getSimpleName() + " and first puzzle:");
        System.out.println(getResultOfFirstPuzzle(getParsedInput()));
    }

    public void runSecondPuzzle() {
        System.out.println("Result of " + this.getClass().getSimpleName() + " and second puzzle:");
        System.out.println(getResultOfSecondPuzzle(getParsedInput()));
    }

    public A getResultOfFirstPuzzle() {
        return getResultOfFirstPuzzle(getParsedInput());
    }

    public A getResultOfSecondPuzzle() {
        return getResultOfSecondPuzzle(getParsedInput());
    }

    protected abstract A getResultOfFirstPuzzle(I input);

    protected abstract A getResultOfSecondPuzzle(I input);

    protected abstract InputStream getInputAsStream();

    protected abstract I getParsedInput();

}
