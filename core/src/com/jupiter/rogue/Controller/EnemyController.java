package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.View.EnemyView;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public abstract class EnemyController {

    EnemyView enemyView;
    Enemy enemy;

    public void update(){}
}
