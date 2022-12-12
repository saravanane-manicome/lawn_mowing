package io.smanicome.lawn_mowing.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LawnToMowShould {
    @Test
    @DisplayName("return mower with correct final positions and orientations")
    void mowAndReturnMowedLawn() {
        final var firstMower = new LawnMower(new Position(1, 2), Orientation.NORTH);
        final var firstMowerActions = List.of(
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.FORWARD
        );
        final var secondMower = new LawnMower(new Position(3, 3), Orientation.EAST);
        final var secondMowerActions = List.of(
                Action.FORWARD,
                Action.FORWARD,
                Action.ROTATE_RIGHT,
                Action.FORWARD,
                Action.FORWARD,
                Action.ROTATE_RIGHT,
                Action.FORWARD,
                Action.ROTATE_RIGHT,
                Action.ROTATE_RIGHT,
                Action.FORWARD
        );

        final var plannedMowings = List.of(
                new PlannedMowing(firstMower, firstMowerActions),
                new PlannedMowing(secondMower, secondMowerActions)
        );
        final var lawnToMaw = new LawnToMow(new Position(5, 5), plannedMowings);

        final var result = lawnToMaw.mow();

        final var expected = List.of(
                new LawnMower(new Position(1, 3), Orientation.NORTH),
                new LawnMower(new Position(5, 1), Orientation.EAST)
        );

        assertEquals(expected, result);
    }

    static Stream<Arguments> getPositionAndOrientation() {
        return Stream.of(
                Arguments.of(new Position(5, 5), Orientation.NORTH),
                Arguments.of(new Position(5, 5), Orientation.EAST),
                Arguments.of(new Position(0, 0), Orientation.WEST),
                Arguments.of(new Position(0, 0), Orientation.SOUTH)
        );
    }

    @DisplayName("return current position when trying to move outside of boundaries")
    @ParameterizedTest
    @MethodSource("getPositionAndOrientation")
    void returnCurrentPositionWhenTryingToMoveOutsideOfBoundaries(Position position, Orientation orientation) {
        final var lawnToMow = new LawnToMow(new Position(5, 5), List.of());
        final var result = lawnToMow.forwardWithinRectangle(position, orientation);
        assertEquals(position, result);
    }

    @Test
    @DisplayName("should return correct final position of mower")
    void returnFinalPositionOfMower() {
        final var mower = new LawnMower(new Position(1, 2), Orientation.NORTH);
        final var mowerActions = List.of(
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.ROTATE_LEFT,
                Action.FORWARD,
                Action.FORWARD,
                Action.ROTATE_RIGHT
        );
        final var plannedMowing = new PlannedMowing(mower, mowerActions);

        final var lawnToMow = new LawnToMow(new Position(5, 5), List.of(plannedMowing));

        final var result = lawnToMow.moveLawnMower(plannedMowing);

        final var expected = new LawnMower(new Position(1, 3), Orientation.EAST);
        assertEquals(expected, result);
    }
}