package dev.swanndolia.idleasciimmorpg.location.fields.enemylist;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Egg;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Feather;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.RawChicken;

public class Chicken extends DefaultCharacter {
    public Chicken Chicken(Player player) {
        Chicken enemy = new Chicken();
        enemy.setName("Chicken");
        enemy.setDesc("It don't look so scary");
        enemy.setLevel((int) (new Random().nextInt((int) ((player.getLevel() * 1.4 - player.getLevel() * 0.6) + 1)) + (player.getLevel() * 0.6) + 1));
        enemy.setEvade((int) Math.round(1 + enemy.getLevel() * 0.2));
        enemy.setLuck((int) Math.round(1 + enemy.getLevel() * 0.2));
        enemy.setHp((int) Math.round(8 + enemy.getLevel() * 4.2));
        enemy.setExpReward((int) Math.round(1 + enemy.getLevel() * 1.2));
        enemy.setDamage((int) Math.round(1 + enemy.getLevel() * 2.2));
        enemy.setAccuracy(90);
        enemy.setCritChance(2);
        enemy.setCritMultiplier(2.0);
        enemy.setDamage(enemy.getLevel() * 2);
        enemy.setInventory(generateInventory());
        return enemy;
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather().Feather(), 300);
        inventory.put(new Egg().Egg(), 100);
        inventory.put(new RawChicken().RawChicken(), 700);
        return inventory;
    }
}
