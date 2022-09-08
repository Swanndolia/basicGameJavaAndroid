package dev.swanndolia.idlemmorpg.enemy.fields;

import java.util.ArrayList;
import java.util.List;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemy.fields.enemylist.Chicken;
import dev.swanndolia.idlemmorpg.enemy.fields.enemylist.Fox;
import dev.swanndolia.idlemmorpg.enemy.fields.enemylist.Rat;

public class EnemyListFields {
    public List<DefaultCharacter> EnemyList(Player player) {
        List<DefaultCharacter> enemyList = new ArrayList<DefaultCharacter>();
        enemyList.add(new Chicken(player.getLevel()));
        enemyList.add(new Fox(player.getLevel()));
        enemyList.add(new Rat(player.getLevel()));
        return (List<DefaultCharacter>) enemyList;
    }
     }
