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
    @Mock
    JSONLoader JSONLoaderMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;


    @BeforeEach
    void setup() {
        game = new Game(outputMock, inputMock, commandHandlerMock, JSONLoaderMock);
    }

    @Test
    void initImmediateExit() {
        when(inputMock.getNextLine()).thenReturn("\n", "name", "exit");

        game.init();

        InOrder inOrder = inOrder(outputMock, inputMock, commandHandlerMock, JSONLoaderMock);
        inOrder.verify(JSONLoaderMock).loadItems(stringArgumentCaptor.capture());
        inOrder.verify(JSONLoaderMock).loadArmor(stringArgumentCaptor.capture());
        inOrder.verify(JSONLoaderMock).loadWeapons(stringArgumentCaptor.capture());
        inOrder.verify(JSONLoaderMock).loadRooms(stringArgumentCaptor.capture());

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

        assertEquals("C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                "\\src\\main\\resources\\json\\items.json", capturedStrings.get(0));
        assertEquals("C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                "\\src\\main\\resources\\json\\armor.json", capturedStrings.get(1));
        assertEquals("C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                "\\src\\main\\resources\\json\\weapons.json", capturedStrings.get(2));
        assertEquals("C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                "\\src\\main\\resources\\json\\rooms.json", capturedStrings.get(3));
        assertEquals("Press Enter to start the game!", capturedStrings.get(4));
        assertEquals("green", capturedStrings.get(5));
        assertEquals("What's your name?", capturedStrings.get(6));
        assertEquals("yellow", capturedStrings.get(7));
        assertEquals("Home", capturedStrings.get(8));
        assertEquals("A cozy hut in the middle of the woods.", capturedStrings.get(9));
        assertEquals("What do you want to do?", capturedStrings.get(10));
        assertEquals("yellow", capturedStrings.get(11));
    }
}
