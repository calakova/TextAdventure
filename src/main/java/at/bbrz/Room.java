package at.bbrz;

import at.bbrz.items.Item;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Room {
    private final int id;
    @Setter
    private Game game;
    @Getter
    private final String name;
    @Getter
    private final String description;
    private final Map<String, Integer> exits = new CaseInsensitiveMap<>();
    private final List<Item> items = new ArrayList<>();

    public Room(int id, String name, String description, Game game) {
        this.id = id;
        this.game = game;
        this.name = name;
        this.description = description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<String> getListOfItems() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : items) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    public String getExitDirectionsString() {
        return String.join(", ", game.getCurrentRoom().exits.keySet());
    }

    public ArrayList<String> getExitDirectionsArrayList() {
        return new ArrayList<>(game.getCurrentRoom().exits.keySet());
    }

    public Room getExitFor(String direction) {
        int roomId = exits.get(direction);
        // TODO: Find a way to access a room by its id
    }
}
