package de.tilmanschweitzer.adventofcode.day;

import java.io.InputStream;

public abstract class AdventOfCodeDay<I, A> {

    public void runFirstPuzzle() {
        System.out.println("Result of " + this.getClass().getSimpleName() + " and first puzzle:");
        System.out.println(getResultOfFirstPuzzle(getParsedInput()));
    }

    public void runSecondPuzzle() {
        System.out.println("Result of " + this.getClass().getSimpleName() + " and second puzzle:");
        System.out.println(getResultOfSecondPuzzle(getParsedInput()));
    }

    protected abstract A getResultOfFirstPuzzle(I input);

    protected abstract A getResultOfSecondPuzzle(I input);

    protected abstract InputStream getInputAsStream();

    protected abstract I getParsedInput();

}
