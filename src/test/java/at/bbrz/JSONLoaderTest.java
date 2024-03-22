package at.bbrz;

import at.bbrz.items.Item;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JSONLoaderTest {
    JSONLoader JSONLoader;
    @Mock
    Output outputMock;
    @Mock
    Gson gsonMock;
    @Mock
    Item[] itemsMock;

    @BeforeEach
    void setup() {
        JSONLoader = new JSONLoader(outputMock, gsonMock);
    }

    @Test
    void loadItems() {
        JSONLoader.loadItems("");
    }
}
