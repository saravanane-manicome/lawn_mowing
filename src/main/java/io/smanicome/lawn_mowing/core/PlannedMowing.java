package io.smanicome.lawn_mowing.core;

import java.util.List;

public record PlannedMowing(LawnMower lawnMower, List<Action> actions) {
}
