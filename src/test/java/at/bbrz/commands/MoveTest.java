package at.bbrz.commands;

import at.bbrz.Game;
import at.bbrz.Input;
import at.bbrz.Output;
import at.bbrz.Room;
import at.bbrz.commands.Move;
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
    Output outputMock;
    @Mock
    Input inputMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;



    @BeforeEach
    void setup() {
        move = new Move(gameMock, roomMock, outputMock, inputMock);
        when(roomMock.getExitDirections()).thenReturn("E");
    }

    @Test
    void runWithValidInput() {
        when(inputMock.getNextLine()).thenReturn("E");
        when(roomMock.getExitFor("E")).thenReturn(nextRoomMock);
        move.run();
        verify(gameMock).setCurrentRoom(nextRoomMock);
    }

    @Test
    void runWithInvalidInput() {
        when(inputMock.getNextLine()).thenReturn("N");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithEmptyStringInput() {
        when(inputMock.getNextLine()).thenReturn("");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithNullInput() {
        when(inputMock.getNextLine()).thenReturn(null);
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @Test
    void runWithLongStringInput() {
        when(inputMock.getNextLine()).thenReturn("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
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
        when(inputMock.getNextLine()).thenReturn("!\"§²³@€$%&/()=?{[]}ß\\üäö<>|`´#'+*~,;.:-_^°");
        move.run();

        inOrderVerifyOutputHandlerMock();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    void inOrderVerifyOutputHandlerMock() {
        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();
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
