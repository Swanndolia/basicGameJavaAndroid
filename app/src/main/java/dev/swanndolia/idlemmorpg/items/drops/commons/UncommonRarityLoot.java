package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Uncommon;

public class UncommonRarityLoot extends Drops implements Serializable, Uncommon {
    public UncommonRarityLoot() {
        this.setName("UncommonRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Uncommon(this);
    }
}
