package de.tilmanschweitzer.adventofcode.aoc2016.day08.command;

import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;

public interface ModifyScreenCommand {

    void apply(Screen screen);
}
