package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Epic;

public class EpicRarityLoot extends Drops implements Serializable, Epic {
    public EpicRarityLoot() {
        this.setName("EpicRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Epic(this);
    }
}
