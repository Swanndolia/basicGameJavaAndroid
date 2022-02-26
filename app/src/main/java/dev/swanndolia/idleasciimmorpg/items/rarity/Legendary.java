package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Legendary{
    default Item Legendary(Item item){
        item.setRarity("Legendary");
        item.setColor(Color.rgb(255, 215, 0));
        return item;
    }
}