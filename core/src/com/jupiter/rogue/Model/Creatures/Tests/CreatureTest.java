package com.jupiter.rogue.Model.Creatures.Tests;

import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Map.Position;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-26.
 */
public class CreatureTest extends Creature {

    Enemy enemy = new Enemy();
    Enemy enemy2 = new Enemy();

    @Test
    public void testSetPosition() throws Exception {
        System.out.println("Tests if the method setPosition changes the position properly");
        Position pos = new Position(50, 50);
        enemy.setPosition(50, 50);
        enemy2.setPosition(60, 60);
        assertEquals(enemy.getPosition(), pos);
        assertNotEquals(enemy2.getPosition(), pos);
    }

    @Test
    public void testTakeDamage() throws Exception {
        enemy.setCurrentHealthPoints(100);
        int newHP = 80;
        enemy.takeDamage(20);
        assertEquals(enemy.getCurrentHealthPoints(), newHP);
    }

    @Test
    public void testIsCreatureDying() throws Exception {

        boolean dead = true;
        boolean alive = false;
        enemy.setCurrentHealthPoints(0);
        enemy2.setCurrentHealthPoints(2);
        assertEquals(enemy.isCreatureDying(), dead);
        assertEquals(enemy2.isCreatureDying(), alive);
    }

    @Test
    public void testSetHealthPoints() throws Exception {
        //??

    }

    @Test
    public void testDecreaseHealthPoints() throws Exception {
        enemy.setCurrentHealthPoints(50);
        enemy.decreaseHealthPoints(20);
        assertEquals(enemy.getCurrentHealthPoints(), 50-20);
        enemy2.setCurrentHealthPoints(50);
        enemy2.decreaseHealthPoints(999);
        assertEquals(enemy2.getCurrentHealthPoints(), 0);
    }


}