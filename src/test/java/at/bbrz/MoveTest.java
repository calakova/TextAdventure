package at.bbrz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MoveTest {
    Move move;
    @Mock
    Game gameMock;
    @Mock
    Room roomMock;
    @Mock
    Room nextRoomMock;
    @Mock
    OutputHandler outputHandlerMock;
    @Mock
    InputHandler inputHandlerMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;



    @BeforeEach
    void setup() {
        move = new Move(gameMock, roomMock, outputHandlerMock, inputHandlerMock);
        when(roomMock.getExitDirections()).thenReturn("E");
    }

    @Test
    void runWithValidInput() {
        when(inputHandlerMock.getNextLine()).thenReturn("E");
        when(roomMock.getExitFor("E")).thenReturn(nextRoomMock);
        move.run();
        verify(gameMock).setCurrentRoom(nextRoomMock);
    }

    @Test
    void runWithInvalidInput() {
        when(inputHandlerMock.getNextLine()).thenReturn("N");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithEmptyStringInput() {
        when(inputHandlerMock.getNextLine()).thenReturn("");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithNullInput() {
        when(inputHandlerMock.getNextLine()).thenReturn(null);
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithLongStringInput() {
        when(inputHandlerMock.getNextLine()).thenReturn("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithSpecialCharactersInput() {
        when(inputHandlerMock.getNextLine()).thenReturn("!\"§²³@€$%&/()=?{[]}ß\\üäö<>|`´#'+*~,;.:-_^°");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    void inOrderVerifyOutputHandlerMock() {
        InOrder inOrder = inOrder(outputHandlerMock);
        inOrder.verify(outputHandlerMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputHandlerMock).emptyLine();
        inOrder.verify(outputHandlerMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputHandlerMock).emptyLine();
    }

    void assertStringListValuesAreExpected(List<String> stringList) {
        Assertions.assertAll(
            "Grouped Assertions of console outputs",
                () -> assertEquals("Which direction do you want to go in?", stringList.get(0)),
                () -> assertEquals("yellow", stringList.get(1)),
                () -> assertEquals("Available directions: E", stringList.get(2)),
                () -> assertEquals("blue", stringList.get(3)),
                () -> assertEquals("Invalid direction!", stringList.get(4)),
                () -> assertEquals("red", stringList.get(5))
        );
    }
}
