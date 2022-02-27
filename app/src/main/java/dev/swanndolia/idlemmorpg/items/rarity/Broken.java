package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Broken {
    default Item Broken(Item item){
        item.setRarity("Broken");
        return item;
    }
}
