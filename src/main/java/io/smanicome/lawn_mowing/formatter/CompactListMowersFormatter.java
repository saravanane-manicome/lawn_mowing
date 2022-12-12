package io.smanicome.lawn_mowing.formatter;

import io.smanicome.lawn_mowing.core.LawnMower;

import java.util.List;

public class CompactListMowersFormatter implements ListMowersFormatter {
    @Override
    public List<String> format(List<LawnMower> lawnMowers) {
        return lawnMowers.stream()
                .map(CompactListMowersFormatter::describeLawnMower)
                .toList();
    }

    private static String describeLawnMower(LawnMower lawnMower) {
        return lawnMower.position().x() + " " + lawnMower.position().y() + " " + lawnMower.orientation().shortName();
    }
}
