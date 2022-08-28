package de.tilmanschweitzer.adventofcode.aoc2016.day08.screen;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.command.ModifyScreenCommand;
import de.tilmanschweitzer.adventofcode.common.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Screen {
    final int width;
    final int height;
    final List<List<ScreenPixel>> pixels;

    private Screen(int width, int height, List<List<ScreenPixel>> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public static Screen of(int width, int height) {
        final List<List<ScreenPixel>> pixels = new ArrayList<>();
        Coordinates.coordinateStreamWithinGrid(width, height).forEach(coordinate -> {
            if (coordinate.getY() >= pixels.size()) {
                pixels.add(new ArrayList<>());
            }
            pixels.get(coordinate.getY()).add(ScreenPixel.of(coordinate));
        });
        return new Screen(width, height, pixels);
    }

    public void applyCommand(ModifyScreenCommand command) {
        command.apply(this);
    }

    private ScreenPixel getPixel(int x, int y) {
        return pixels.get(y).get(x);
    }

    public List<Boolean> getRow(int y) {
        return pixels.get(y).stream().map(ScreenPixel::isOn).collect(Collectors.toUnmodifiableList());
    }

    public List<Boolean> getColumn(int x) {
        return IntStream.range(0, height).boxed().map(y -> getPixel(x, y).isOn()).collect(Collectors.toUnmodifiableList());
    }

    public boolean isOn(int x, int y) {
        return getPixel(x, y).isOn();
    }

    public void turnOn(int x, int y) {
        getPixel(x, y).turnOn();
    }

    public void setOn(int x, int y, boolean on) {
        getPixel(x, y).setOn(on);
    }

    @Override
    public String toString() {
        return pixels.stream().map(pixelRow -> pixelRow.stream().map(Screen::displayChar).map(Objects::toString).collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }

    private static char displayChar(ScreenPixel screenPixel) {
        return screenPixel.isOn() ? '#' : '.';
    }


    public Long countLights() {
        return pixels.stream().map(row -> row.stream().filter(ScreenPixel::isOn).count()).reduce(Long::sum).orElse(0L);
    }
}
