package at.bbrz;

import at.bbrz.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommandHandlerTest {
    CommandHandler commandHandler;
    @Mock
    Output outputMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @Mock
    Move moveMock;
    @Mock
    Exit exitMock;
    @Mock
    Suicide suicideMock;
    @Mock
    Stats statsMock;
    @Mock
    Look lookMock;


    @BeforeEach
    void setup() {
        commandHandler = new CommandHandler(outputMock);
    }

    @Test
    public void runCommandInvalidInput() {
        commandHandler.runCommand("test");
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Invalid command!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    public void runCommandEmptyStringInput() {
        commandHandler.runCommand("");
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Invalid command!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    public void runCommandNullInput() {
        commandHandler.runCommand(null);
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Invalid command!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    public void runCommandLongStringInput() {
        commandHandler.runCommand("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Invalid command!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    public void runCommandSpecialCharactersInput() {
        commandHandler.runCommand("!\"§²³@€$%&/()=?{[]}ß\\üäö<>|`´#'+*~,;.:-_^°");
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Invalid command!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    void runCommandValidInputMove() {
        commandHandler.addCommand("move", moveMock);
        commandHandler.runCommand("move");
        verify(moveMock).run();
    }

    @Test
    void runCommandValidInputExit() {
        commandHandler.addCommand("exit", exitMock);
        commandHandler.runCommand("exit");
        verify(exitMock).run();
    }

    @Test
    void runCommandValidInputSuicide() {
        commandHandler.addCommand("suicide", suicideMock);
        commandHandler.runCommand("suicide");
        verify(suicideMock).run();
    }

    @Test
    void runCommandValidInputStats() {
        commandHandler.addCommand("stats", statsMock);
        commandHandler.runCommand("stats");
        verify(statsMock).run();
    }

    @Test
    void runCommandValidInputLook() {
        commandHandler.addCommand("look", lookMock);
        commandHandler.runCommand("look");
        verify(lookMock).run();
    }

    // TODO: Write more tests as more commands are made
}
