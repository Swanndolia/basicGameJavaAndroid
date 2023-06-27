package dev.swanndolia.idlemmorpg.enemy.fields.enemylist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultEncounter;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.drops.fields.Egg;
import dev.swanndolia.idlemmorpg.items.drops.fields.Feather;
import dev.swanndolia.idlemmorpg.items.drops.fields.RawChicken;

public class Chicken extends DefaultEncounter implements Serializable {
    public Chicken(int playerLevel) {
        this.setName("Chicken");
        this.setDesc("It don't look so scary");
        this.setLevel((int) (new Random().nextInt((int) ((playerLevel * 1.4 - playerLevel * 0.6) + 1)) + (playerLevel * 0.6) + 1));
        this.setEvade((int) Math.round(1 + this.getLevel() * 0.2));
        this.setLuck((int) Math.round(1 + this.getLevel() * 0.2));
        this.setHp((int) Math.round(8 + this.getLevel() * 4.2));
        this.setExpReward((int) Math.round(1 + this.getLevel() * 1.2));
        this.setDamage((int) Math.round(1 + this.getLevel() * 2.2));
        this.setAccuracy(90);
        this.setCritChance(2);
        this.setCritMultiplier(2.0);
        this.setInventory(generateInventory());
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather(), 300);
        inventory.put(new Egg(), 100);
        inventory.put(new RawChicken(), 700);
        return inventory;
    }
}
