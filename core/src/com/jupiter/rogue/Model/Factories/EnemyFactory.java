package com.jupiter.rogue.Model.Factories;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-16.
 */

public interface EnemyFactory {
    /**
     * creates an enemy
     * @param xPos position in x axis
     * @param yPos position in y axis
     * @param level level of enemy
     * @param elite elite status
     * @return the enemy created
     */
    Enemy createEnemy(float xPos, float yPos, int level, boolean elite);
}
