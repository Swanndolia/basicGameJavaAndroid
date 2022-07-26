package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Legendary;

public class LegendaryRarityLoot extends Drops implements Serializable, Legendary {
    public LegendaryRarityLoot() {
        this.setName("LegendaryRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Legendary(this);
    }
}
