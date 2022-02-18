package dev.swanndolia.idleasciimmorpg.location.fields;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.swanndolia.idleasciimmorpg.characters.Character;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class GenerateEnemy {
    public Character GenerateEnemy(String location, Player player) {
        List<Character> enemyList = new ArrayList<Character>();
        enemyList.add(new Chicken().Chicken(player));
        enemyList.add(new Fox().Fox(player));
        enemyList.add(new Rat().Rat(player));
        Character randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
        return randomEnemy;
    }
}
