package io.smanicome.lawn_mowing.formatter;

import io.smanicome.lawn_mowing.core.LawnMower;
import io.smanicome.lawn_mowing.core.Orientation;
import io.smanicome.lawn_mowing.core.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompactListMowersFormatterShould {
    private final CompactListMowersFormatter formatter = new CompactListMowersFormatter();

    @Test
    @DisplayName("format mower into a string")
    void formatSingleMower() {
        final var mower = new LawnMower(Position.Zero, Orientation.NORTH);

        final var result = formatter.format(List.of(mower));

        assertEquals(List.of("0 0 N"), result);
    }

    @Test
    @DisplayName("format two mowers into separate strings")
    void formatMultipleMowers() {
        final var firstMower = new LawnMower(Position.Zero, Orientation.NORTH);
        final var secondMower = new LawnMower(new Position(1, 0), Orientation.NORTH);

        final var result = formatter.format(List.of(firstMower, secondMower));

        assertEquals(List.of("0 0 N", "1 0 N"), result);
    }
}