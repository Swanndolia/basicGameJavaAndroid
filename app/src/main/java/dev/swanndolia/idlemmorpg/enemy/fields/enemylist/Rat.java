package dev.swanndolia.idlemmorpg.enemyzones.fields.enemylist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.drops.fields.Egg;
import dev.swanndolia.idlemmorpg.items.drops.fields.Feather;
import dev.swanndolia.idlemmorpg.items.drops.fields.RawChicken;

public class Rat extends DefaultCharacter implements Serializable {
    public Rat(int playerLevel) {
        this.setName("Rat");
        this.setDesc("I should avoid getting bitten");
        this.setLevel((int) (new Random().nextInt((int) ((playerLevel * 1.4 - playerLevel * 0.6) + 1)) + (playerLevel * 0.6) + 1));
        this.setEvade((int) Math.round(1 + this.getLevel() * 1.5));
        this.setLuck((int) Math.round(1 + this.getLevel() * 1.5));
        this.setHp((int) Math.round(3 + this.getLevel() * 2.5));
        this.setDamage((int) Math.round(1 + this.getLevel() * 3.2));
        this.setExpReward((int) Math.round(1 + this.getLevel() * 1.2));
        this.setAccuracy(80);
        this.setCritChance(5);
        this.setCritMultiplier(1.5);
        this.setInventory(generateInventory());
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather(), 300);
        inventory.put(new Egg(), 100);
        inventory.put(new RawChicken(), 700);
        return inventory;
    } //do fight part and leveling system
}
