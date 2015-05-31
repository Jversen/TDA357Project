package com.jupiter.rogue.Model.Factories;

import com.jupiter.rogue.Model.Creatures.Boss;
import com.jupiter.rogue.Model.Creatures.Enemy;

/**
 * Created by hilden on 2015-05-31.
 */
public class BossFactory implements EnemyFactory {

    String enemyType = "widow";

    @Override
    public Enemy createEnemy(float xPos, float yPos, int level, boolean elite){
        Enemy enemy = new Boss(xPos, yPos, level, elite);
        return enemy;
    }

    public String getEnemyType(){
        return enemyType;
    }
}
