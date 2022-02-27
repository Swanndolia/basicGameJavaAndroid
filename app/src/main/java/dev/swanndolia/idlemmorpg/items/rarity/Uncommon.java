package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Uncommon {
    default Item Uncommon(Item item){
        item.setRarity("Uncommon");
        return item;
    }
}