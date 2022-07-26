package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Unique;

public class UniqueRarityLoot extends Drops implements Serializable, Unique {
    public UniqueRarityLoot() {
        this.setName("UniqueRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Unique(this);
    }
}
