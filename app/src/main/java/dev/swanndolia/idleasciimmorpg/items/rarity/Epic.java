package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Epic {
    default public Item Epic(Item item){
        item.setRarity("Epic");
        return item;
    }
}