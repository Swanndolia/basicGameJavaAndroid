package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Unique  {
    default Item Unique(Item item){
        item.setRarity("Unique");
        return item;
    }
}
