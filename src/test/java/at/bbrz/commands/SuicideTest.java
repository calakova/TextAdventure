package at.bbrz.commands;

import at.bbrz.Output;
import at.bbrz.Player;
import at.bbrz.commands.Suicide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SuicideTest {
    Suicide suicide;
    @Mock
    Player playerMock;

    @BeforeEach
    void setup() {
        suicide = new Suicide(playerMock);
    }

    @Test
    void run() {
        suicide.run();
        verify(playerMock).die();
    }
}
