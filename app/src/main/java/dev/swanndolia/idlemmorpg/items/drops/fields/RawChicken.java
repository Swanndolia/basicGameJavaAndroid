package dev.swanndolia.idlemmorpg.items.drops.fields;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Common;

public class RawChicken extends Drops implements Serializable, Common {
    public RawChicken(){
        this.setName("Raw Chicken");
        this.setDesc("Don't look so good for now, i should cook it first");
        this.setSellValue(1);
        this.Common(this);
    }
}
