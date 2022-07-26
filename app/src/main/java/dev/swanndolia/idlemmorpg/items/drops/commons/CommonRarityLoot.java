package dev.swanndolia.idlemmorpg.items.drops.commons;

import java.io.Serializable;
import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Common;

public class CommonRarityLoot extends Drops implements Serializable, Common {
    public CommonRarityLoot() {
        this.setName("CommonRarityLoot");
        this.setDesc("Just for testing purpose");
        this.setSellValue(0);
        this.Common(this);
    }
}
