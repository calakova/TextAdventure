package at.bbrz;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private final Game game;
    @Getter
    private final String name;
    @Getter
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();

    public Room(String name, String description, Game game) {
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

    public void addExit(String name, Room exit) {
        exits.put(name, exit);
    }

    public String getExitDirections() {
        return String.join(", ", game.getCurrentRoom().exits.keySet());
    }

    public Room getExitFor(String direction) {
        return exits.get(direction);
    }
}
