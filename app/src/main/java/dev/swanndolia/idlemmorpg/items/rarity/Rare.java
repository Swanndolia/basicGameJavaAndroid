package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Rare{
    default Item Rare(Item item){
        item.setRarity("Rare");
        return item;
    }
}