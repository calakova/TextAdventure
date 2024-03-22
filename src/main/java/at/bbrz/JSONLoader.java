package at.bbrz;

import at.bbrz.items.Item;
import at.bbrz.items.Weapon;
import at.bbrz.items.armor.Armor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONLoader {
    private JsonReader jsonReader;
    private final Output outputHandler;
    private final Gson gson;

    public JSONLoader(Output outputHandler, Gson gson) {
        this.outputHandler = outputHandler;
        this.gson = gson;
    }

    public Item[] loadItems(String filePath) {
        return gson.fromJson(fileToJsonArray(filePath), Item[].class);
    }

    public Armor[] loadArmor(String filePath) {
        return gson.fromJson(fileToJsonArray(filePath), Armor[].class);
    }

    public Weapon[] loadWeapons(String filePath) {
        return gson.fromJson(fileToJsonArray(filePath), Weapon[].class);
    }
    public Room[] loadRooms(String filePath) {
        return gson.fromJson(fileToJsonArray(filePath), Room[].class);
    }

    private JsonArray fileToJsonArray(String filePath) {
        File file = new File(filePath);

        try {
            jsonReader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            outputHandler.printLine("Invalid file!", "red");
        }

        return JsonParser.parseReader(jsonReader).getAsJsonArray();
    }
}
