package at.bbrz;

import at.bbrz.items.armor.ArmorSet;
import at.bbrz.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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


    @DisplayName("Player takes damage")
    @ParameterizedTest(name = "rawDam: {0}, def: {1}, originalHP: {2}, expectedDam: {3}, expectedHP: {4}")
    @CsvSource(delimiter = '|', textBlock = """
        1    | 0   | 100 | 1  | 99
        100  | 1   | 100 | 99 | 1
        1000 | 999 | 2   | 1  | 1
    """)
    void takeDamageWhenDamageHigherThanDefence(int rawDamage,
                                               int defence,
                                               int originalHP,
                                               int expectedDamage,
                                               int expectedHP) {
        when(armorSetMock.getDefence()).thenReturn(defence);

        player.setCurrentHP(originalHP);
        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + expectedDamage + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals(expectedHP + "/100 HP", capturedStrings.get(2));
        assertEquals("purple", capturedStrings.get(3));
    }

    @DisplayName("Player takes no damage")
    @ParameterizedTest(name = "rawDam: {0}, def: {1}, originalHP: {2}")
    @CsvSource(delimiter = '|', textBlock = """
        1       | 1         | 100
        0       | 0         | 100
        1000    | 1000      | 2
        1000000 | 100000000 | 100
    """)
    void takeNoDamageWhenDamageLowerThanOrEqualToDefence(int rawDamage, int defence, int originalHP) {
        when(armorSetMock.getDefence()).thenReturn(defence);

        player.setCurrentHP(originalHP);
        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took 0 damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals(originalHP + "/100 HP", capturedStrings.get(2));
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

    @DisplayName("Player dies from damage")
    @ParameterizedTest(name = "rawDam: {0}, def: {1}, originalHP: {2}, expectedDam: {3}")
    @CsvSource(delimiter = '|', textBlock = """
        101       | 1      | 100 | 100
        1         | 0      | 1   | 1
        1000      | 10     | 20  | 990
        1_000_000 | 10_000 | 100 | 990_000
    """)
    void dieFromDamage(int rawDamage, int defence, int originalHP, int expectedDamage) {
        when(armorSetMock.getDefence()).thenReturn(defence);

        player.setCurrentHP(originalHP);
        player.takeDamage(rawDamage);

        InOrder inOrder = inOrder(outputMock, gameMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(gameMock).setGameRunning(booleanArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + expectedDamage + " damage!", capturedStrings.get(0));
        assertEquals("purple", capturedStrings.get(1));
        assertEquals("You died!", capturedStrings.get(2));
        assertEquals("red", capturedStrings.get(3));
        assertEquals(false, booleanArgumentCaptor.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100, 100_000})
    void dieFromMaxDamage(int defence) {
        when(armorSetMock.getDefence()).thenReturn(defence);

        player.takeDamage(Integer.MAX_VALUE);

        InOrder inOrder = inOrder(outputMock, gameMock);
        inOrder.verify(outputMock, times(2))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(gameMock).setGameRunning(booleanArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Test took " + (Integer.MAX_VALUE - defence) + " damage!", capturedStrings.get(0));
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
