package dev.swanndolia.idlemmorpg.items;


import java.io.Serializable;

public class Item implements Serializable {

    public String name;
    public String desc;
    public String slot;
    public int dodgeChance;
    public int protection;
    public int weight;
    public int sellValue;
    public int damage;
    public int critChance;
    public int critDamage;
    public int accuracy;
    public Double critMultiplier;
    public String rarity;
    public int icon;

    public Item() {
        this.slot = "None";
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public int getProtection() {
        return protection;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Double getCritMultiplier() {
        return critMultiplier;
    }

    public void setCritMultiplier(Double critMultiplier) {
        this.critMultiplier = critMultiplier;
    }

    public void calculateCritDamage() {
        this.critDamage = (int) (this.damage * this.critMultiplier);
    }

    public Integer getCritDamage() {
        return critDamage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Item))
            return false;
        Item item = (Item) obj;
        return item.getName().equals(this.getName())
                && item.getDesc().equals(this.getDesc())
                && item.getSellValue() == this.getSellValue();
    }
}
