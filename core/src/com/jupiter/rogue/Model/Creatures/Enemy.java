package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Map.Position;

/**
 * Created by Johan on 16/04/15.
 */
public class Enemy extends Creature {

    public Enemy() {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
    }

    public Enemy(Position position, int maxHP, int currentHP, int attackPoints, int movementSpeed) {

        this.movementSpeed = movementSpeed;
        this.attackPoints = attackPoints;
        this.currentHealthPoints = currentHP;
        this.maxHealthPoints = maxHP;
    }

    public void move(){

    }

    public void attack(){
        if(Math.abs(this.getX() - Hero.getInstance().getX()) < 50){
            System.out.println(this + "attacks");
        }
    }

}
