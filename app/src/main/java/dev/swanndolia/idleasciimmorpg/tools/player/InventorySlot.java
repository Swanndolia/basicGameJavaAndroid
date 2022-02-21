package dev.swanndolia.idleasciimmorpg.tools.player;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class InventorySlot {
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer amount;
    public Item item;
}
