package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Broken;

public class BrokenRarityLoot extends Drops implements Serializable, Broken {
    public BrokenRarityLoot() {
        this.setName("BrokenRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Broken(this);
    }
}
