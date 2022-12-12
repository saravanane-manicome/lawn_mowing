package io.smanicome.lawn_mowing.runner;

import io.smanicome.lawn_mowing.core.*;
import io.smanicome.lawn_mowing.exceptions.InvalidInputException;
import io.smanicome.lawn_mowing.parser.LawnMowingParser;
import io.smanicome.lawn_mowing.printer.MowedLawnPrinter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LawnMowingRunnerShould {
    @Mock
    private LawnMowingParser lawnMowingParser;

    @Mock
    private MowedLawnPrinter mowedLawnPrinter;

    @InjectMocks
    private LawnMowingRunner lawnMowingRunner;


    @Test
    void mowWithSingleMower() throws InvalidInputException {
        final var filepath = "some_file";
        final var mower = new LawnMower(Position.Zero, Orientation.NORTH);
        final var lawnToMow = new LawnToMow(Position.Zero, List.of(new PlannedMowing(mower, List.of())));

        when(lawnMowingParser.parse(any())).thenReturn(lawnToMow);

        lawnMowingRunner.run(filepath);

        final var orderVerifier = inOrder(lawnMowingParser, mowedLawnPrinter);
        orderVerifier.verify(lawnMowingParser).parse(filepath);
        orderVerifier.verify(mowedLawnPrinter).print(List.of(mower));
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    void mowWithMultipleMowers() throws InvalidInputException {
        final var filepath = "some_file";
        final var firstMower = new LawnMower(Position.Zero, Orientation.NORTH);
        final var secondMower = new LawnMower(new Position(1,0), Orientation.NORTH);
        final var lawnToMow = new LawnToMow(
            Position.Zero,
            List.of(
                new PlannedMowing(firstMower, List.of()),
                new PlannedMowing(secondMower, List.of())
            )
        );

        when(lawnMowingParser.parse(any())).thenReturn(lawnToMow);

        lawnMowingRunner.run(filepath);

        final var orderVerifier = inOrder(lawnMowingParser, mowedLawnPrinter);
        orderVerifier.verify(lawnMowingParser).parse(filepath);
        orderVerifier.verify(mowedLawnPrinter).print(List.of(firstMower, secondMower));
        orderVerifier.verifyNoMoreInteractions();
    }
}