package dev.swanndolia.idleasciimmorpg.tools.player;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class ForceSaveInventoryList extends Item implements Serializable {
    public ForceSaveInventoryList ForceSaveInventoryList(){
        this.setSlot("KeepMe");
        this.setName("InvisibleItem");
        this.setDesc("Item used to force save empty inventory list but completely invisible");
        this.setSellValue(-1);
        return this;
    }
}
