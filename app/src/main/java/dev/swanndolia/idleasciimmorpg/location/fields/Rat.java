package dev.swanndolia.idleasciimmorpg.location.fields;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idleasciimmorpg.characters.Character;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Egg;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Feather;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.RawChicken;

public class Rat extends Character {
    public Rat Rat(Player player) {
        Rat enemy = new Rat();
        enemy.setName("Chicken");
        enemy.setDesc("It dont look so scary");
        enemy.setLevel((int) (new Random().nextInt((int) ((player.getLevel() * 1.4 - player.getLevel() * 0.6) + 1)) + (player.getLevel() * 0.6) + 1));
        enemy.setEvade((int) (enemy.getLevel() * 1.5));
        enemy.setLuck((int) (enemy.getLevel() * 1.5));
        enemy.setEvade((int) (3 + enemy.getLevel() * 2.5));
        enemy.setDamage((int) (enemy.getLevel() * 3.2));
        enemy.setInventory(generateInventory());
        return enemy;
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather().Feather(), 300);
        inventory.put(new Egg().Egg(), 100);
        inventory.put(new RawChicken().RawChicken(), 700);
        return inventory;
    } //do fight part and leveling system
}
