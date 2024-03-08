package at.bbrz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomTest {
    Room room;
    @Mock
    Game gameMock;
    @Mock
    Room roomMock;
    @Mock
    Map<String, Room> mapMock;

    @BeforeEach
    void setup() {
        room = new Room("Example", "Example", gameMock);
    }

    @Test
    void getExitDirectionsOneExit() {
        when(gameMock.getCurrentRoom()).thenReturn(room);
        room.addExit("E", roomMock);
        String result = room.getExitDirections();
        assertEquals("E", result);
    }

    @Test
    void getExitDirectionsFourExits() {
        when(gameMock.getCurrentRoom()).thenReturn(room);
        room.addExit("S", roomMock);
        room.addExit("E", roomMock);
        room.addExit("W", roomMock);
        room.addExit("N", roomMock);
        String result = room.getExitDirections();
        assertEquals("S, E, W, N", result);
    }

    @Test
    void getExitFor() {
        room.addExit("E", roomMock);
        Room exit = room.getExitFor("E");
        assertEquals(exit, roomMock);
    }
}
