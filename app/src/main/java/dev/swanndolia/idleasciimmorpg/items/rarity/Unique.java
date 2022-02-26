package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Unique  {
    default Item Unique(Item item){
        item.setRarity("Unique");
        return item;
    }
}
