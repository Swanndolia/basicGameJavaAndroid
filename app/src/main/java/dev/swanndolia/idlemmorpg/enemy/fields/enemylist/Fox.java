package dev.swanndolia.idlemmorpg.enemy.fields.enemylist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.drops.fields.Egg;
import dev.swanndolia.idlemmorpg.items.drops.fields.Feather;
import dev.swanndolia.idlemmorpg.items.drops.fields.RawChicken;

public class Fox extends DefaultCharacter implements Serializable {
    public Fox(int playerLevel) {
        this.setName("Fox");
        this.setDesc("Can you catch it");
        this.setLevel((int) (new Random().nextInt((int) ((playerLevel * 1.4 - playerLevel * 0.6) + 1)) + (playerLevel * 0.6) + 1));
        this.setEvade((int) Math.round(1 + this.getLevel() * 3.5));
        this.setLuck((int) Math.round(1 + this.getLevel() * 2.5));
        this.setHp((int) (Math.round(5 + this.getLevel() * 1.8)));
        this.setExpReward((int) Math.round(1 + this.getLevel() * 1.2));
        this.setDamage((int) Math.round(1 + this.getLevel() * 2.8));
        this.setAccuracy(85);
        this.setCritChance(8);
        this.setCritMultiplier(1.3);
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
