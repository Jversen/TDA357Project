package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.EnemyMovement;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 16/04/15.
 */
public class Enemy extends Creature {

    private boolean flying;
    private Position heroPos;
    protected float bodyHeight;
    protected float bodyWidth;

    public Enemy() {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
        this.flying = false;
        this.position = new Position(200, 50);
    }

    public Enemy(int maxHP, int currentHP, int attackPoints, int movementSpeed, boolean flying, int posX, int posY) {

        this.movementSpeed = movementSpeed;
        this.attackPoints = attackPoints;
        this.currentHealthPoints = currentHP;
        this.maxHealthPoints = maxHP;
        this.flying = flying;
        this.position = new Position(posX, posY);
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

    public float getBodyHeight(){
        return bodyHeight;
    }

    public float getBodyWidth(){
        return bodyWidth;
    }


    public void attack(EnemyMovement enemyMovement){
        if(Math.abs((this.getX() + 5/PPM) - (Hero.getInstance().getX() + (this.getBodyWidth()/2)/PPM)) <= 25/PPM){
            enemyMovement.attack(this.getDirection());
        }
    }

    public void update(){
        if(this.getX() - (Hero.getInstance().getX()) > 0){
            this.setDirection(Direction.LEFT);
        }
        else{
            this.setDirection(Direction.RIGHT);
        }

        if((Math.abs((this.getX() + 5/PPM) - (Hero.getInstance().getX() + (this.getBodyWidth()/2)/PPM)) > 25/PPM) ||
                (Math.abs((this.getY() + (this.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) > 38/PPM)){
            //this.walk(this.getMovementSpeed(), new EnemyMovement());
        }
        else {
           // this.attack(new EnemyMovement(this.getBody()));
        }
    }

    public float getMovementSpeed(){
        return this.movementSpeed;
    }

}


























