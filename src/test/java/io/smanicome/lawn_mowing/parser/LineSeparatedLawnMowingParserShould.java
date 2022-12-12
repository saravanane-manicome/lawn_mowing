package io.smanicome.lawn_mowing.parser;

import io.smanicome.lawn_mowing.core.Action;
import io.smanicome.lawn_mowing.core.LawnMower;
import io.smanicome.lawn_mowing.core.Orientation;
import io.smanicome.lawn_mowing.core.Position;
import io.smanicome.lawn_mowing.exceptions.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineSeparatedLawnMowingParserShould {
    private final LineSeparatedLawnMowingParser parser = new LineSeparatedLawnMowingParser();

    @Test
    @DisplayName("parse string into lawn mower")
    void parseLawnMower() throws InvalidInputException {
        final var result = parser.parseLawnMower("1 1 N");
        assertEquals(new LawnMower(new Position(1, 1), Orientation.NORTH), result);
    }

    @DisplayName("throw when parsing lawn mower from")
    @ParameterizedTest(name = "invalid input being \"{0}\"")
    @ValueSource(strings = {"", " ", "AZERTY", "-1 1 N", "1 1 P"})
    void throwIfLawnMowerHasInvalidInput(String input) {
        final var exception = assertThrows(InvalidInputException.class, () -> parser.parseLawnMower(input));
        assertEquals("unrecognized mower position and|or orientation", exception.getMessage());
    }

    @Test
    void parseStringIntoLawTopRightCorner() throws InvalidInputException {
        final var result = parser.parseLawnTopRightCorner("1 1");
        assertEquals(new Position(1, 1), result);
    }

    @DisplayName("throw when parsing top right corner")
    @ParameterizedTest(name = "with input string being \"{0}\"")
    @ValueSource(strings = {"", " ", "A 1", "1 1 "})
    void throwWhenParsingTopRightCornerWithInvalidValue(String input) {
        final var exception = assertThrows(InvalidInputException.class, () -> parser.parseLawnTopRightCorner(input));
        assertEquals("unrecognized lawn top right corner", exception.getMessage());
    }

    @Test
    @DisplayName("parse string into list of actions")
    void parseActions() throws InvalidInputException {
        final var result = parser.parseActions("AGD");
        final var expected = List.of(Action.FORWARD, Action.ROTATE_LEFT, Action.ROTATE_RIGHT);
        assertEquals(expected, result);
    }

    @DisplayName("throw when parsing actions")
    @ParameterizedTest(name = "with input string being \"{0}\"")
    @ValueSource(strings = {" ", "A 1", "1 1 "})
    void throwWhenParsingActionWithInvalidValue(String input) {
        final var exception = assertThrows(InvalidInputException.class, () -> parser.parseActions(input));
        assertEquals("unrecognized action", exception.getMessage());
    }
}