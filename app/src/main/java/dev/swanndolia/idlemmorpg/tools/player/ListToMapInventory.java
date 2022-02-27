package dev.swanndolia.idlemmorpg.tools.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.swanndolia.idlemmorpg.items.Item;

public class ListToMapInventory {
    public Map<Item, Integer> ListToMapInventory(List<Item> inventory) {
        Map<Item, Integer> inventoryMap = new HashMap<Item, Integer>();
        if (inventory != null) {
            for (Item item : inventory) {
                if (!inventoryMap.containsKey(item))
                    inventoryMap.put(item, 0);
                inventoryMap.put(item, inventoryMap.get(item) + 1);
            }
        }
        return inventoryMap;
    }
}
