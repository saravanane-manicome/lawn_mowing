package io.smanicome.lawn_mowing.parser;

import io.smanicome.lawn_mowing.core.*;
import io.smanicome.lawn_mowing.exceptions.InvalidInputException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LineSeparatedLawnMowingParser implements LawnMowingParser {
    private static final Pattern lawnSizeRegex = Pattern.compile("^[\\d+] [\\d+]$");
    private static final Pattern actionRegex = Pattern.compile("^[AGD]*$");

    @Override
    public LawnToMow parse(String filepath) throws InvalidInputException {
        try {
            final var lines = Files.readAllLines(Path.of(filepath));

            final var topRightCorner = parseLawnTopRightCorner(lines.get(0));
            final var plannedMowings = parsePlannedMowings(lines.subList(1, lines.size()));

            return new LawnToMow(topRightCorner, plannedMowings);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    List<PlannedMowing> parsePlannedMowings(List<String> lines) throws InvalidInputException {
        final var plannedMowings = new ArrayList<PlannedMowing>();
        final var linesIterator = lines.iterator();

        while(linesIterator.hasNext()) {
            final var positionAndOrientation = linesIterator.next();
            if (!linesIterator.hasNext()) throw new InvalidInputException("missing actions for mower");
            final var actionSequence = linesIterator.next();

            final var lawnMower = parseLawnMower(positionAndOrientation);
            final var actions = parseActions(actionSequence);

            final var plannedMowing = new PlannedMowing(lawnMower, actions);
            plannedMowings.add(plannedMowing);
        }
        return plannedMowings;
    }

    Position parseLawnTopRightCorner(String line) throws InvalidInputException {
        if(!lawnSizeRegex.matcher(line).matches())
            throw new InvalidInputException("unrecognized lawn top right corner");

        final var coordinates = line.split(" ");
        final var x = Integer.parseInt(coordinates[0]);
        final var y = Integer.parseInt(coordinates[1]);
        return new Position(x, y);
    }

    LawnMower parseLawnMower(String positionAndOrientation) throws InvalidInputException {
        if(!positionAndOrientation.matches("^\\d+ \\d+ [NEWS]$"))
            throw new InvalidInputException("unrecognized mower position and|or orientation");

        final var coordinates = positionAndOrientation.split(" ");

        final var x = Integer.parseInt(coordinates[0]);
        final var y = Integer.parseInt(coordinates[1]);
        final var position = new Position(x, y);

        final var orientation = switch (coordinates[2]) {
            case "N" -> Orientation.NORTH;
            case "E" -> Orientation.EAST;
            case "W" -> Orientation.WEST;
            case "S" -> Orientation.SOUTH;
            default -> throw new UnsupportedOperationException();
        };

        return new LawnMower(position, orientation);
    }

    List<Action> parseActions(String actionsToParse) throws InvalidInputException {
        if(!actionRegex.matcher(actionsToParse).matches()) throw new InvalidInputException("unrecognized action");

        return Arrays.stream(actionsToParse.split(""))
                .map(letter -> switch (letter) {
                    case "A" -> Action.FORWARD;
                    case "G" -> Action.ROTATE_LEFT;
                    case "D" -> Action.ROTATE_RIGHT;
                    default -> throw new UnsupportedOperationException();
                })
                .toList();
    }
}
