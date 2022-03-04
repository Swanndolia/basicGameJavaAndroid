package dev.swanndolia.idlemmorpg.enemyzones.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemyzones.fields.enemylist.Chicken;
import dev.swanndolia.idlemmorpg.enemyzones.fields.enemylist.Fox;
import dev.swanndolia.idlemmorpg.enemyzones.fields.enemylist.Rat;

public class GenerateEnemy {
    public DefaultCharacter GenerateEnemy(String location, Player player) {
        List<DefaultCharacter> enemyList = new ArrayList<DefaultCharacter>();
        enemyList.add(new Chicken(player.getLevel()));
        enemyList.add(new Fox(player.getLevel()));
        enemyList.add(new Rat(player.getLevel()));
        DefaultCharacter randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
        return randomEnemy;
    }
}
