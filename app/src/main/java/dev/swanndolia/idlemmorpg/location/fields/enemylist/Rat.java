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

public class Rat extends DefaultCharacter {
    public Rat Rat(Player player) {
        Rat enemy = new Rat();
        enemy.setName("Rat");
        enemy.setDesc("I should avoid getting bitten");
        enemy.setLevel((int) (new Random().nextInt((int) ((player.getLevel() * 1.4 - player.getLevel() * 0.6) + 1)) + (player.getLevel() * 0.6) + 1));
        enemy.setEvade((int) Math.round(1 + enemy.getLevel() * 1.5));
        enemy.setLuck((int) Math.round(1 + enemy.getLevel() * 1.5));
        enemy.setHp((int) Math.round(3 + enemy.getLevel() * 2.5));
        enemy.setDamage((int) Math.round(1 + enemy.getLevel() * 3.2));
        enemy.setExpReward((int) Math.round(1 + enemy.getLevel() * 1.2));
        enemy.setAccuracy(80);
        enemy.setCritChance(5);
        enemy.setCritMultiplier(1.5);
        enemy.setInventory(generateInventory());
        return enemy;
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather(), 300);
        inventory.put(new Egg(), 100);
        inventory.put(new RawChicken(), 700);
        return inventory;
    } //do fight part and leveling system
}
