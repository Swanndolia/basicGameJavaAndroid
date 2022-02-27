package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Legendary{
    default Item Legendary(Item item){
        item.setRarity("Legendary");
        return item;
    }
}