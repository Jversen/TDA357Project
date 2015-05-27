package com.jupiter.rogue.Utils;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-16.
 */

public interface EnemyFactory {

    EnemyController createEnemy(float xPos, float yPos, int level, boolean elite);
}
