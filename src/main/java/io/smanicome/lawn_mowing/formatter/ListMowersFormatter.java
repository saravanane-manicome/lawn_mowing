package io.smanicome.lawn_mowing.formatter;

import io.smanicome.lawn_mowing.core.LawnMower;

import java.util.List;

public interface ListMowersFormatter {
    List<String> format(List<LawnMower> lawnMowers);
}
