package com.jupiter.rogue.Model;

/**
 * Created by Johan on 16/04/15.
 */
public class Enemy extends Creature{
    public Enemy() {
        this.position = new Position();
        this.healthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
    }

    public Enemy(Position position, int healthPoints, int attackPoints, int movementSpeed) {
        this.position = position;
        this.movementSpeed = movementSpeed;
        this.attackPoints = attackPoints;
        this.healthPoints = healthPoints;
    }
}
