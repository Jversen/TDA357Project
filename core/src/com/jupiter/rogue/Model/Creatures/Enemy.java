package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.libGDX.EnemyMovement;

/**
 * Created by Johan on 16/04/15.
 */
public class Enemy extends Creature {

    private boolean flying;
    String name;
    private Position heropos;

    public Enemy() {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
        this.flying = false;
    }

    public Enemy(int maxHP, int currentHP, int attackPoints, int movementSpeed, boolean flying) {

        this.movementSpeed = movementSpeed;
        this.attackPoints = attackPoints;
        this.currentHealthPoints = currentHP;
        this.maxHealthPoints = maxHP;
        this.flying = flying;
    }

    public void fly(Direction direction, float angle, float force, EnemyMovement enemyMovement) {
        if (this.flying) {
            setDirection(direction);
            enemyMovement.fly(direction, angle, force);
            setPosition(enemyMovement.getPosition());

        } else {
            System.out.println("this enemy can't fly");
        }
    }

    public void walk(float movementSpeed, EnemyMovement enemyMovement){
        if(this.getPosition().getXPos() > Hero.getInstance().getX()){
            this.setDirection(Direction.LEFT);
        }
        else{
            this.setDirection(Direction.RIGHT);
        }

        enemyMovement.walk(this.getDirection(), movementSpeed);
        setPosition(enemyMovement.getPosition());
    }

    public String getName(){
        return this.name;
    }

    public void attack(){
        if(Math.abs(this.getX() - (Hero.getInstance().getX())) < 1){
            System.out.println(this.getName() + " attacks");
        }
    }

    public void update(){
        if(this.getX() - (Hero.getInstance().getX()) > 0){
            this.setDirection(Direction.LEFT);
        }
        else{
            this.setDirection(Direction.RIGHT);
        }


        if(Math.abs(this.getX() - (Hero.getInstance().getX() - 20)) > 0){
            this.walk(this.getMovementSpeed(), new EnemyMovement(this.getBody()));
        }
        else {
            this.attack();
        }

    }

    public float getMovementSpeed(){
        return this.movementSpeed;
    }

}
