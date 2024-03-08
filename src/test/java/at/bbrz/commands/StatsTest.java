package at.bbrz.commands;

import at.bbrz.armor.ArmorSet;
import at.bbrz.Output;
import at.bbrz.Player;
import at.bbrz.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class StatsTest {
    Stats stats;
    @Mock
    Player playerMock;
    @Mock
    Output outputMock;
    @Mock
    ArmorSet armorSetMock;
    @Mock
    Weapon weaponMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setup() {
        stats = new Stats(playerMock, armorSetMock, weaponMock, outputMock);
    }

    @Test
    void run() {
        when(playerMock.getName()).thenReturn("TestName");
        when(playerMock.getCurrentHP()).thenReturn(90);
        when(playerMock.getMaxHP()).thenReturn(100);
        when(armorSetMock.getDefence()).thenReturn(5);
        when(weaponMock.getAttack()).thenReturn(3);

        stats.run();

        InOrder inOrder = inOrder(outputMock);
        inOrder.verify(outputMock, times(4))
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("Name: TestName", capturedStrings.get(0));
        assertEquals("green", capturedStrings.get(1));
        assertEquals("HP: 90/100", capturedStrings.get(2));
        assertEquals("green", capturedStrings.get(3));
        assertEquals("Defence: 5", capturedStrings.get(4));
        assertEquals("green", capturedStrings.get(5));
        assertEquals("Attack: 3", capturedStrings.get(6));
        assertEquals("green", capturedStrings.get(7));
    }
}
