package io.smanicome.lawn_mowing.printer;

import io.smanicome.lawn_mowing.core.LawnMower;
import io.smanicome.lawn_mowing.formatter.ListMowersFormatter;

import java.io.PrintStream;
import java.util.List;

public class PrintStreamMowedLawnPrinter implements MowedLawnPrinter {
    private final PrintStream printStream;

    private final ListMowersFormatter formatter;

    public PrintStreamMowedLawnPrinter(PrintStream printStream, ListMowersFormatter formatter) {
        this.printStream = printStream;
        this.formatter = formatter;
    }

    @Override
    public void print(List<LawnMower> lawnMowers) {
        final var lines = formatter.format(lawnMowers);
        lines.forEach(printStream::println);
    }
}
