package dev.swanndolia.idlemmorpg.items.rarity;

import dev.swanndolia.idlemmorpg.items.Item;

public interface Common {
    default Item Common(Item item){
        item.setRarity("Common");
        return item;
    }
}
