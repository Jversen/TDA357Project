package com.jupiter.rogue.Model.Factories;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-16.
 */

public interface EnemyFactory {

    Enemy createEnemy(float xPos, float yPos, int level, boolean elite);
}
