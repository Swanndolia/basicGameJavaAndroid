package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class RawChicken extends Drops implements Serializable {
    public RawChicken RawChicken(){
        this.setName("Raw Chicken");
        this.setDesc("Don't look so good for now, i should cook it first");
        this.setSellValue(1);
        return this;
    }
}
