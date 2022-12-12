package io.smanicome.lawn_mowing.runner;

import io.smanicome.lawn_mowing.exceptions.InvalidInputException;
import io.smanicome.lawn_mowing.formatter.CompactListMowersFormatter;
import io.smanicome.lawn_mowing.parser.LawnMowingParser;
import io.smanicome.lawn_mowing.parser.LineSeparatedLawnMowingParser;
import io.smanicome.lawn_mowing.printer.MowedLawnPrinter;
import io.smanicome.lawn_mowing.printer.PrintStreamMowedLawnPrinter;

public class LawnMowingRunner {
    private final LawnMowingParser lawnMowingParser;
    private final MowedLawnPrinter mowedLawnPrinter;

    public LawnMowingRunner(LawnMowingParser lawnMowingParser, MowedLawnPrinter mowedLawnPrinter) {
        this.lawnMowingParser = lawnMowingParser;
        this.mowedLawnPrinter = mowedLawnPrinter;
    }

    public void run(String filepath) throws InvalidInputException {
        final var lawn = lawnMowingParser.parse(filepath);
        final var lawnMowers = lawn.mow();
        mowedLawnPrinter.print(lawnMowers);
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            throw new IllegalArgumentException("USAGE: THERE SHOULD BE EXACTLY ONE ARGUMENT, WHICH WOULD BE THE PATH OF THE FILE TO PARSE");
        }

        final var parser = new LineSeparatedLawnMowingParser();
        final var formatter = new CompactListMowersFormatter();
        final var printer = new PrintStreamMowedLawnPrinter(System.out, formatter);

        final var lawnMowingRunner = new LawnMowingRunner(parser, printer);

        try {
            lawnMowingRunner.run(args[0]);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }
}
