package io.smanicome.lawn_mowing.printer;

import io.smanicome.lawn_mowing.core.LawnMower;
import io.smanicome.lawn_mowing.core.Orientation;
import io.smanicome.lawn_mowing.core.Position;
import io.smanicome.lawn_mowing.formatter.ListMowersFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrintStreamLawnPrinterShould {
    @Mock
    private PrintStream printStream;

    @Mock
    private ListMowersFormatter formatter;

    @InjectMocks
    private PrintStreamMowedLawnPrinter printer;

    @Test
    void formatThenPrintSingleLine() {
        final var mower = new LawnMower(Position.Zero, Orientation.NORTH);

        when(formatter.format(any())).thenReturn(List.of("0 0 1"));

        printer.print(List.of(mower));

        final var orderVerifier = inOrder(printStream, formatter);
        orderVerifier.verify(formatter).format(List.of(mower));
        orderVerifier.verify(printStream).println("0 0 1");
        orderVerifier.verifyNoMoreInteractions();
    }

    @Test
    void formatThenPrintMultipleLines() {
        final var firstMower = new LawnMower(Position.Zero, Orientation.NORTH);
        final var secondMower = new LawnMower(new Position(1, 0), Orientation.NORTH);
        final var linesToPrint = List.of("0 0 N", "1 0 N");

        when(formatter.format(any())).thenReturn(linesToPrint);

        printer.print(List.of(firstMower, secondMower));

        final var orderVerifier = inOrder(printStream, formatter);
        orderVerifier.verify(formatter).format(List.of(firstMower, secondMower));
        for (String s : linesToPrint) {
            orderVerifier.verify(printStream).println(s);
        }
        orderVerifier.verifyNoMoreInteractions();
    }
}