package dev.swanndolia.idleasciimmorpg.items.rarity;

import android.graphics.Color;

import dev.swanndolia.idleasciimmorpg.items.Item;

public interface Common {
    default Item Common(Item item){
        item.setRarity("Common");
        item.setColor(Color.rgb(0, 0, 0));
        return item;
    }
}
