package at.bbrz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommandHandlerTest {
    @InjectMocks
    CommandHandler commandHandlerMock;
    @Mock
    Runnable runnableMock;
    @Mock
    Output outputMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    // TODO: Fix this test!
    @Test
    public void runCommandInvalidInput() {
        commandHandlerMock.setCommand("test");

        commandHandlerMock.runCommand();
        verify(outputMock, times(1))
                .printLine(stringArgumentCaptor.capture());
        assertEquals("Invalid command!", stringArgumentCaptor.getValue());
        verify(outputMock, times(1)).emptyLine();
    }
}
