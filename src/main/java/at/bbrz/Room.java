package at.bbrz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
    }

    public void addExit(String name, Room exit) {
        exits.put(name, exit);
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
