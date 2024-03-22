package at.bbrz.commands;

import at.bbrz.Game;
import at.bbrz.Input;
import at.bbrz.Output;
import at.bbrz.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;



    @BeforeEach
    void setup() {
        move = new Move(gameMock, roomMock, outputMock);
        ArrayList<String> directionList = new ArrayList<>();
        directionList.add("E");
        when(roomMock.getExitDirectionsArrayList()).thenReturn(directionList);
    }

    @Test
    void runWithValidInput() {
        when(roomMock.getExitFor("E")).thenReturn(nextRoomMock);
        move.run("E");
        verify(gameMock).setCurrentRoom(nextRoomMock);
    }

    @Test
    void runWithNullInput() {
        move.run(null);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock).printLine(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    @ParameterizedTest
    @ValueSource(strings = {"",
    "!\"§²³@€$%&/()=?{[]}ß\\üäö<>|`´#'+*~,;.:-_^°",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "N"
    })
    void runWithInvalidDirections(String parameter) {
        move.run(parameter);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock).printLine(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertStringListValuesAreExpected(capturedStrings);
    }

    void assertStringListValuesAreExpected(List<String> stringList) {
        Assertions.assertAll(
            "Grouped Assertions of console outputs",
                () -> assertEquals("Invalid direction!", stringList.get(0)),
                () -> assertEquals("red", stringList.get(1))
        );
    }
}
