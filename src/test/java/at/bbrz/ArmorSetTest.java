package at.bbrz;

import at.bbrz.items.armor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ArmorSetTest {
    ArmorSet armorSet;
    @Mock
    HeadArmor headArmorMock;
    @Mock
    TorsoArmor torsoArmorMock;
    @Mock
    LegArmor legArmorMock;
    @Mock
    FootArmor footArmorMock;

    @BeforeEach
    void setup() {
        armorSet = new ArmorSet(headArmorMock, torsoArmorMock, legArmorMock, footArmorMock);
    }

    @DisplayName("Get ArmorSet defence")
    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
        0 | 0  | 0  | 0  | 0
        1 | 1  | 1  | 1  | 4
        0 | 34 | 20 | 16 | 70
    """)
    void getArmorSetDefence(int headDefence, int torsoDefence, int legDefence, int footDefence, int expectedDefence) {
        when(headArmorMock.getDefence()).thenReturn(headDefence);
        when(torsoArmorMock.getDefence()).thenReturn(torsoDefence);
        when(legArmorMock.getDefence()).thenReturn(legDefence);
        when(footArmorMock.getDefence()).thenReturn(footDefence);

        assertEquals(expectedDefence, armorSet.getDefence());
    }
}
