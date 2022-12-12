package io.smanicome.lawn_mowing.core;

public record Position(int x, int y) {
    public static final Position Zero = new Position(0, 0);
}
