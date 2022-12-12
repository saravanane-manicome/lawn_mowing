package io.smanicome.lawn_mowing.printer;

import io.smanicome.lawn_mowing.core.LawnMower;

import java.util.List;

public interface MowedLawnPrinter {
    void print(List<LawnMower> lawnMowers);
}
