package dev.swanndolia.idlemmorpg.location.fields.enemylist;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.drops.fields.chickendrops.Egg;
import dev.swanndolia.idlemmorpg.items.drops.fields.chickendrops.Feather;
import dev.swanndolia.idlemmorpg.items.drops.fields.chickendrops.RawChicken;

public class Fox extends DefaultCharacter {
    public Fox Fox(Player player) {
        Fox enemy = new Fox();
        enemy.setName("Fox");
        enemy.setDesc("Can you catch it");
        enemy.setLevel((int) (new Random().nextInt((int) ((player.getLevel() * 1.4 - player.getLevel() * 0.6) + 1)) + (player.getLevel() * 0.6) + 1));
        enemy.setEvade((int) Math.round(1 + enemy.getLevel() * 3.5));
        enemy.setLuck((int) Math.round(1 + enemy.getLevel() * 2.5));
        enemy.setHp((int) (Math.round(5 + enemy.getLevel() * 1.8)));
        enemy.setExpReward((int) Math.round(1 + enemy.getLevel() * 1.2));
        enemy.setDamage((int) Math.round(1 + enemy.getLevel() * 2.8));
        enemy.setAccuracy(85);
        enemy.setCritChance(8);
        enemy.setCritMultiplier(1.3);
        enemy.setInventory(generateInventory());
        return enemy;
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather(), 300);
        inventory.put(new Egg(), 100);
        inventory.put(new RawChicken(), 700);
        return inventory;
    }
}
