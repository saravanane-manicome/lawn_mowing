package io.smanicome.lawn_mowing.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class OrientationShould {

    @ParameterizedTest
    @EnumSource(Orientation.class)
    void rotateLeft(Orientation orientation) {
        final var expectedOrientation = switch (orientation) {

            case NORTH -> Orientation.WEST;
            case EAST -> Orientation.NORTH;
            case WEST -> Orientation.SOUTH;
            case SOUTH -> Orientation.EAST;
        };

        assertEquals(expectedOrientation, orientation.rotateLeft());
    }

    @ParameterizedTest
    @EnumSource(Orientation.class)
    void rotateRight(Orientation orientation) {
        final var expectedOrientation = switch (orientation) {

            case NORTH -> Orientation.EAST;
            case EAST -> Orientation.SOUTH;
            case WEST -> Orientation.NORTH;
            case SOUTH -> Orientation.WEST;
        };

        assertEquals(expectedOrientation, orientation.rotateRight());
    }

    @ParameterizedTest
    @EnumSource(Orientation.class)
    void shortName(Orientation orientation) {
        final var expectedString = switch (orientation) {

            case NORTH -> "N";
            case EAST  -> "E";
            case WEST  -> "W";
            case SOUTH -> "S";
        };

        assertEquals(expectedString, orientation.shortName());
    }
}