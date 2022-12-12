package io.smanicome.lawn_mowing.core;

public enum Orientation {
    NORTH, EAST, WEST, SOUTH;

    public Orientation rotateLeft() {
        return switch (this) {
            case NORTH -> Orientation.WEST;
            case EAST -> Orientation.NORTH;
            case WEST -> Orientation.SOUTH;
            case SOUTH -> Orientation.EAST;
        };
    }

    public Orientation rotateRight() {
        return switch (this) {
            case NORTH -> Orientation.EAST;
            case EAST -> Orientation.SOUTH;
            case WEST -> Orientation.NORTH;
            case SOUTH -> Orientation.WEST;
        };
    }

    public String shortName() {
        return switch (this) {

            case NORTH -> "N";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH -> "S";
        };
    }
}
