package dev.swanndolia.idlemmorpg.tools.market;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.Item;

public class ItemHolder implements Serializable {
    Item item;
    Integer price;
    Integer amount;
    String ownerName;

    public ItemHolder(Item item, Integer amountToSell, Integer sellPrice, String ownerName) {
        this.item = item;
        this.price = sellPrice;
        this.amount = amountToSell;
        this.ownerName = ownerName;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOwnerName() {
        return this.ownerName;
    }
}
