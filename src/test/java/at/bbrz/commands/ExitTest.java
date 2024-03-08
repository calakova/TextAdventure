package at.bbrz.commands;

import at.bbrz.Game;
import at.bbrz.commands.Exit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExitTest {
    @InjectMocks
    Exit exit;
    @Mock
    Game game;

    @Test
    void runDisablesGameLoop() {
        exit.run();
        verify(game, times(1)).setGameRunning(false);
    }
}
