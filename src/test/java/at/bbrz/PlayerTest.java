package at.bbrz;

import at.bbrz.armor.ArmorSet;
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
public class PlayerTest {
    Player player;
    @Mock
    ArmorSet armorSetMock;
    @Mock
    Weapon weaponMock;
    @Mock
    Output outputMock;
    @Mock
    Game gameMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @BeforeEach
    void setup() {
        player = new Player("Test", armorSetMock, weaponMock, outputMock, gameMock);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 99})
    void takeDamageNoDefence(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(0);

        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + rawDamage + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals(100 - rawDamage + "/100 HP", capturedStrings.get(2));
        assertEquals("purple", capturedStrings.get(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 20, 109})
    void takeDamage10Defence(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(10);

        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + (rawDamage - armorSetMock.getDefence())
                + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals((100 - (rawDamage - armorSetMock.getDefence()))
                + "/100 HP", capturedStrings.get(2));
        assertEquals("purple", capturedStrings.get(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 20, 100})
    void takeNoDamage100Defence(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(100);

        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took 0 damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals("100/100 HP", capturedStrings.get(2));
        assertEquals("purple", capturedStrings.get(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, Integer.MIN_VALUE})
    void takeNoDamageFromNegativeDamage(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(0);

        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took 0 damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals("100/100 HP", capturedStrings.get(2));
        assertEquals("purple", capturedStrings.get(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 1000, Integer.MAX_VALUE})
    void dieFromDamageNoDefence(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(0);

        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock, gameMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(gameMock).setGameRunning(booleanArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + rawDamage + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals("You died!", capturedStrings.get(2));
        assertEquals("red", capturedStrings.get(3));
        assertEquals(false, booleanArgumentCaptor.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {181, 1000, Integer.MAX_VALUE})
    void dieFromDamage100Defence80HP(int rawDamage) {
        when(armorSetMock.getDefence()).thenReturn(0);

        player.setCurrentHP(80);
        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock, gameMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(gameMock).setGameRunning(booleanArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + (rawDamage - armorSetMock.getDefence())
                + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals("You died!", capturedStrings.get(2));
        assertEquals("red", capturedStrings.get(3));
        assertEquals(false, booleanArgumentCaptor.getValue());
    }

    @Test
    void die() {
        player.die();

        InOrder inOrder = inOrder(outputMock, gameMock);
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(gameMock).setGameRunning(booleanArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("You died!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
        assertEquals(false, booleanArgumentCaptor.getValue());
    }
}
