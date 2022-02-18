package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class RawChicken extends Drops implements Serializable {
    public RawChicken RawChicken(){
        RawChicken item = new RawChicken();
        item.setName("Raw Chicken");
        item.setDesc("Don't look so good for now, i should cook it first");
        item.setSellValue(1);
        return item;
    }
}
