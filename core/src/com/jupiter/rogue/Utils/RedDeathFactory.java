package com.jupiter.rogue.Utils;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Controller.RedDeathController;


/**
 * Created by Johan on 2015-05-17.
 */
public class RedDeathFactory implements EnemyFactory {
    EnemyController enemyController;
    String enemyType = "redDeath";
    int level;
    boolean elite;

    @Override
    public EnemyController createEnemy(float xPos, float yPos, int level, boolean elite) {
        this.enemyController = new RedDeathController(xPos, yPos, level, elite);
        return enemyController;
    }

    @Override
    public String getEnemyType() {
        return enemyType;
    }
}
