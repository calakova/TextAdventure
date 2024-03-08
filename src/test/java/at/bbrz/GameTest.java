package at.bbrz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GameTest {
    Game game;
    @Mock
    CommandHandler commandHandlerMock;
    @Mock
    Output outputMock;
    @Mock
    Input inputMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;


    @BeforeEach
    void setup() {
        game = new Game(outputMock, inputMock, commandHandlerMock);
    }

    @Test
    void initImmediateExit() {
        when(inputMock.getNextLine()).thenReturn("\n", "name", "exit");

        game.init();

        InOrder inOrder = inOrder(outputMock, inputMock, commandHandlerMock);
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(inputMock).getNextLine();

        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(inputMock).getNextLine();
        inOrder.verify(outputMock).emptyLine();

        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture());
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(inputMock).getNextLine();
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Press Enter to start the game!", capturedStrings.get(0));
        assertEquals("green", capturedStrings.get(1));
        assertEquals("What's your name?", capturedStrings.get(2));
        assertEquals("yellow", capturedStrings.get(3));
        assertEquals("Home", capturedStrings.get(4));
        assertEquals("A cozy hut in the middle of the woods.", capturedStrings.get(5));
        assertEquals("What do you want to do?", capturedStrings.get(6));
        assertEquals("yellow", capturedStrings.get(7));
    }
}
