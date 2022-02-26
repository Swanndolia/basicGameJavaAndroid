package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Rare{
    default Item Rare(Item item){
        item.setRarity("Rare");
        return item;
    }
}