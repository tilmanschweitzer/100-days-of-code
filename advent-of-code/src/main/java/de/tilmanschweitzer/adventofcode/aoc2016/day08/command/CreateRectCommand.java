package de.tilmanschweitzer.adventofcode.aoc2016.day08.command;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import de.tilmanschweitzer.adventofcode.common.Coordinates;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CreateRectCommand implements ModifyScreenCommand {

    final int width;
    final int height;

    public CreateRectCommand(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void apply(Screen screen) {
        Coordinates.coordinateStreamWithinGrid(width, height).forEach(coordinate -> {
            screen.turnOn(coordinate.getX(), coordinate.getY());
        });
    }
}
