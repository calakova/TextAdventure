package at.bbrz.commands;

import at.bbrz.Game;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExitTest {
    @InjectMocks
    Exit exit;
    @Mock
    Game game;

    @ParameterizedTest
    @ValueSource(strings = {"", "!\"§²³@€$%&/()=?{[]}ß\\üäö<>|`´#'+*~,;.:-_^°",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void runWithRandomParameters(String parameter) {
        exit.run(parameter);
        verify(game).setGameRunning(false);
    }
}
