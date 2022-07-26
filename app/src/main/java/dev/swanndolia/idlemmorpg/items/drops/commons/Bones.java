package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Common;

public class Bones extends Drops implements Serializable, Common {
    public Bones(){
        this.setName("Bones");
        this.setDesc("It's sturdy, could be use to craft basic armor");
        this.setSellValue(2);
        this.Common(this);
    }
}
