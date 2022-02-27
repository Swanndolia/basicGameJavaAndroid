package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Epic {
    default public Item Epic(Item item){
        item.setRarity("Epic");
        return item;
    }
}