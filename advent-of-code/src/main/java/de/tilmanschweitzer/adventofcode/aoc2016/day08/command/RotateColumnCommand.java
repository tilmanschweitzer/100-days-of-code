package de.tilmanschweitzer.adventofcode.aoc2016.day08.command;

import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.aoc2016.day08.screen.Screen;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode
@ToString
public class RotateColumnCommand implements ModifyScreenCommand {

    final int x;
    final int by;

    public RotateColumnCommand(int x, int by) {
        this.x = x;
        this.by = by;
    }

    @Override
    public void apply(Screen screen) {
        final List<Boolean> column = screen.getColumn(x);
        final int pivotIndex = column.size() - (by % column.size());
        final List<Boolean> rotatedColumn = Streams.concat(column.subList(pivotIndex, column.size()).stream(), column.subList(0, pivotIndex).stream())
                .collect(Collectors.toUnmodifiableList());

        IntStream.range(0, column.size()).forEach(y -> {
            screen.setOn(x, y, rotatedColumn.get(y));
        });

    }
}
