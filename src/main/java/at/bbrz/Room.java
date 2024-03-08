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
    @Getter
    private final List<Item> items = new ArrayList<>();

    public Room(String name, String description, Game game) {
        this.game = game;
        this.name = name;
        this.description = description;
    }

    public void addExit(String name, Room exit) {
        exits.put(name, exit);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {

    }

    public String getExitDirections() {
        return String.join(", ", game.getCurrentRoom().exits.keySet());
    }

    public Room getExitFor(String direction) {
        return exits.get(direction);
    }
}
