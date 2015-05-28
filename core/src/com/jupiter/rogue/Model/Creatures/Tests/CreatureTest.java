package com.jupiter.rogue.Model.Creatures.Tests;

import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Utils.Position;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-26.
 */
public class CreatureTest {

    Enemy enemy = new Enemy(100, 100, 25, 100, 1, 6, false, 50, 50, 1, false);
    Enemy enemy2 = new Enemy(100, 100, 25, 100, 1, 6, false, 50, 50, 1, false);

    @Test
    public void testSetPosition() throws Exception {
        Position pos = new Position(50, 50);
        enemy.setPosition(50, 50);
        enemy2.setPosition(60, 60);
        assertEquals(enemy.getPosition(), pos);
        assertNotEquals(enemy2.getPosition(), pos);
    }

    @Test
    public void testTakeDamage() throws Exception {
        enemy.setInvulnerable(false);
        enemy.setHealthPoints(100);
        int newHP = 80;
        enemy.takeDamage(20);
        assertEquals(enemy.getCurrentHealthPoints(), newHP);

        enemy2.setInvulnerable(true);
        enemy2.setHealthPoints(100);
        enemy.takeDamage(20);
        assertEquals(enemy.getCurrentHealthPoints(), 100);
    }

    @Test
    public void testIsCreatureDying() throws Exception {

        boolean dead = true;
        boolean alive = false;
        enemy.setHealthPoints(0);
        enemy2.setHealthPoints(2);
        assertEquals(enemy.isCreatureDying(), dead);
        assertEquals(enemy2.isCreatureDying(), alive);
    }

    @Test
    public void testSetHealthPoints() throws Exception {
        enemy.setHealthPoints(56);
        assertEquals(enemy.getCurrentHealthPoints(), 56);

        enemy.setHealthPoints(150);
        assertEquals(enemy.getCurrentHealthPoints(), 100);

        enemy.setHealthPoints(-20);
        assertEquals(enemy.getCurrentHealthPoints(), 100);
    }

    @Test
    public void testWalk() throws Exception {
        enemy.setMovementState(MovementState.STANDING);
        enemy.setCreatureGrounded(true);
        enemy.walk(Direction.LEFT);
        assertEquals(enemy.getMovementState(), MovementState.WALKING);

        enemy2.setMovementState(MovementState.STANDING);
        enemy2.setCreatureFalling(true);
        enemy2.walk(Direction.LEFT);
        assertEquals(enemy2.getMovementState(), MovementState.FALLING);
    }

    @Test
    public void testJump() throws Exception {
        enemy.setMovementState(MovementState.STANDING);
        enemy.jump();
        assertEquals(enemy.getMovementState(), MovementState.JUMPING);
    }

    @Test
    public void testRelax() throws Exception {
        enemy.setMovementState(MovementState.WALKING);
        enemy.setCreatureGrounded(true);
        enemy.relax();
        assertEquals(enemy.getMovementState(), MovementState.STANDING);

        enemy2.setMovementState(MovementState.WALKING);
        enemy2.setCreatureFalling(true);
        enemy2.relax();
        assertEquals(enemy2.getMovementState(), MovementState.FALLING);
    }
}


