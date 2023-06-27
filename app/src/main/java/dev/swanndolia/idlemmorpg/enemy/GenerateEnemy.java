package dev.swanndolia.idlemmorpg.enemy;

import java.util.List;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultEncounter;


public class GenerateEnemy {
    public DefaultEncounter getRandomEnemy() {
        return randomEnemy;
    }

    DefaultEncounter randomEnemy;
    public GenerateEnemy(List<DefaultEncounter> enemyList) {
        randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
    }
}
