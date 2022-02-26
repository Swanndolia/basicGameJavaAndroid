package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Broken {
    default Item Broken(Item item){
        item.setRarity("Broken");
        item.setColor(Color.rgb(180, 180, 180));
        return item;
    }
}
