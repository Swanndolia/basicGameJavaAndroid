package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Uncommon {
    default Item Uncommon(Item item){
        item.setRarity("Uncommon");
        return item;
    }
}