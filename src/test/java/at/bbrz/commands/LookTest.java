package at.bbrz.commands;

import at.bbrz.Output;
import at.bbrz.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LookTest {
    Look look;
    @Mock
    Room roomMock;
    @Mock
    Output outputMock;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setup() {
        look = new Look(roomMock, outputMock);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\\?ß²³§$%&/()=}{[]", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void runWithValidParametersAndItems(String parameter) {
        ArrayList<String> items = new ArrayList<>();
        items.add("TestItem1");
        items.add("TestItem2");
        when(roomMock.getListOfItems()).thenReturn(items);

        look.run(parameter);

        InOrder inOrder = inOrder(roomMock, outputMock);
        inOrder.verify(roomMock).getListOfItems();
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("TestItem1, TestItem2", capturedStrings.get(0));
        assertEquals("cyan", capturedStrings.get(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\\?ß²³§$%&/()=}{[]", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void runWithValidParametersNoItems(String parameter) {
        ArrayList<String> items = new ArrayList<>();
        when(roomMock.getListOfItems()).thenReturn(items);

        look.run(parameter);

        InOrder inOrder = inOrder(roomMock, outputMock);
        inOrder.verify(roomMock).getListOfItems();
        inOrder.verify(outputMock)
                .printLine(stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture());
        inOrder.verify(outputMock).emptyLine();

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        assertEquals("No items found in this room!", capturedStrings.get(0));
        assertEquals("red", capturedStrings.get(1));
    }
}
