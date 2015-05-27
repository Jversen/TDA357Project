package com.jupiter.rogue.Utils;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Controller.WidowController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-16.
 */
public class WidowFactory implements EnemyFactory {
    EnemyController enemyController;
    String enemyType = "widow";
    int level;
    boolean elite;

    public WidowFactory(){

    }

    public String getEnemyType(){
        return enemyType;
    }

    @Override
    public EnemyController createEnemy(float xPos, float yPos, int level, boolean elite){
        this.enemyController = new WidowController(xPos, yPos, level, elite);
        return enemyController;
    }
}
