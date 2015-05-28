package com.jupiter.rogue.Model.Creatures.Tests;

import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.Enums.Direction;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-27.
 */
public class EnemyTest {

    Enemy enemy = new Enemy(100, 100, 25, 100, 1, 6, false, 50, 50, 1, false);

    @Test
    public void testSetEnemyDirection() throws Exception {
        enemy.setX(50);
        Hero.getInstance().setX(70);
        enemy.setEnemyDirection();
        assertEquals(Direction.RIGHT, enemy.getDirection());
        Hero.getInstance().setX(30);
        enemy.setEnemyDirection();
        assertEquals(Direction.LEFT, enemy.getDirection());
    }
}