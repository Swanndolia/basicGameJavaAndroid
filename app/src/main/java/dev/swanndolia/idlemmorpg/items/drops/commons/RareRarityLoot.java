package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Rare;

public class RareRarityLoot extends Drops implements Serializable, Rare {
    public RareRarityLoot() {
        this.setName("RareRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Rare(this);
    }
}
