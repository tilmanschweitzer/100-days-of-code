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
public class RotateRowCommand implements ModifyScreenCommand {

    final int y;
    final int by;

    public RotateRowCommand(int y, int by) {
        this.y = y;
        this.by = by;
    }

    @Override
    public void apply(Screen screen) {
        final List<Boolean> row = screen.getRow(y);
        final int pivotIndex = row.size() - (by % row.size());
        final List<Boolean> rotatedRow = Streams.concat(row.subList(pivotIndex, row.size()).stream(), row.subList(0, pivotIndex).stream())
                .collect(Collectors.toUnmodifiableList());

        IntStream.range(0, row.size()).forEach(x -> {
            screen.setOn(x, y, rotatedRow.get(x));
        });

    }
}
