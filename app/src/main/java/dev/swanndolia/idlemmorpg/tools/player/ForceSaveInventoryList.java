package dev.swanndolia.idlemmorpg.tools.player;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.Item;

public class ForceSaveInventoryList extends Item implements Serializable {
    public ForceSaveInventoryList(){
        this.setSlot("KeepMe");
        this.setName("InvisibleItem");
        this.setDesc("Item used to force save empty inventory list but completely invisible");
        this.setSellValue(-1);
    }
}
