package io.smanicome.lawn_mowing.core;

import java.util.List;

public record LawnToMow(Position topRightCorner, List<PlannedMowing> plannedMowings) {

    public List<LawnMower> mow() {
        return plannedMowings.stream().map(this::moveLawnMower).toList();
    }

    LawnMower moveLawnMower(PlannedMowing plannedMowing) {
        var position = plannedMowing.lawnMower().position();
        var orientation = plannedMowing.lawnMower().orientation();

        final var mowerPositions = plannedMowings.stream()
                .map(PlannedMowing::lawnMower)
                .filter(mower -> !mower.equals(plannedMowing.lawnMower()))
                .map(LawnMower::position)
                .toList();

        for (final var action : plannedMowing.actions()) {
            switch (action) {
                case ROTATE_LEFT -> orientation = orientation.rotateLeft();
                case ROTATE_RIGHT -> orientation = orientation.rotateRight();
                case FORWARD -> {
                    final var newPosition = forwardWithinRectangle(position, orientation);
                    if(!mowerPositions.contains(newPosition)) {
                        position = newPosition;
                    }
                }
            }
        }

        return new LawnMower(position, orientation);
    }

    Position forwardWithinRectangle(Position position, Orientation orientation) {
        final var x = position.x();
        final var y = position.y();

        return switch (orientation) {
            case NORTH -> new Position(x, Math.min(y+1, topRightCorner.y()));
            case EAST  -> new Position(Math.min(x+1, topRightCorner.x()), y);
            case WEST  -> new Position(Math.max(x-1, Position.Zero.x()), y);
            case SOUTH -> new Position(x, Math.max(y-1, Position.Zero.y()));
        };
    }
}
