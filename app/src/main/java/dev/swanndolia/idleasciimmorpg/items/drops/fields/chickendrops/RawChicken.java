package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;
import dev.swanndolia.idleasciimmorpg.items.rarity.Common;

public class RawChicken extends Drops implements Serializable, Common {
    public RawChicken RawChicken(){
        this.setName("Raw Chicken");
        this.setDesc("Don't look so good for now, i should cook it first");
        this.setSellValue(1);
        this.Common(this);
        return this;
    }
}
