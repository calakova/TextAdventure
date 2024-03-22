package at.bbrz;

import at.bbrz.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        commandHandler.addCommand("move", moveMock);
        commandHandler.addCommand("exit", exitMock);
        commandHandler.addCommand("suicide", suicideMock);
        commandHandler.addCommand("stats", statsMock);
        commandHandler.addCommand("look", lookMock);
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

        assertEquals("Null input!", capturedStrings.get(0));
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

    @ParameterizedTest
    @ValueSource(strings = {"move e e"})
    public void runCommandTooManyParameters(String command) {
        commandHandler.runCommand(command);
        verify(outputMock).printLine(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Too many parameters!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        verify(outputMock).emptyLine();
    }

    @Test
    void runCommandValidInputMove() {
        commandHandler.runCommand("move e");
        verify(moveMock).run(stringArgumentCaptor.capture());
    }

    @Test
    void runCommandValidInputExit() {
        commandHandler.runCommand("exit");
        verify(exitMock).run(stringArgumentCaptor.capture());
    }

    @Test
    void runCommandValidInputSuicide() {
        commandHandler.runCommand("suicide");
        verify(suicideMock).run(stringArgumentCaptor.capture());
    }

    @Test
    void runCommandValidInputStats() {
        commandHandler.runCommand("stats");
        verify(statsMock).run(stringArgumentCaptor.capture());
    }

    @Test
    void runCommandValidInputLook() {
        commandHandler.runCommand("look");
        verify(lookMock).run(stringArgumentCaptor.capture());
    }

    // TODO: Write more tests as more commands are made
}
