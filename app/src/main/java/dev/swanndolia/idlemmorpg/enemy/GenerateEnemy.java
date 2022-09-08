package dev.swanndolia.idlemmorpg.enemy;

import java.util.List;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;


public class GenerateEnemy {
    public DefaultCharacter getRandomEnemy() {
        return randomEnemy;
    }

    DefaultCharacter randomEnemy;
    public GenerateEnemy(List<DefaultCharacter> enemyList) {
        randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
    }
}
