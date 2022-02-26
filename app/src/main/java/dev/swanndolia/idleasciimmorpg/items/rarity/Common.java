package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Common {
    default Item Common(Item item){
        item.setRarity("Common");
        return item;
    }
}
