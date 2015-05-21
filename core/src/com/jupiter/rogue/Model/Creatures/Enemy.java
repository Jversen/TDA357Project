package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Items.Weapon;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors.AttackBehavior;
import com.jupiter.rogue.Utils.AIBehaviors.JumpBehaviors.JumpBehavior;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.MoveBehavior;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 16/04/15.
 */
public class Enemy extends Creature {

    private boolean flying;
    private float attackRange;
    private int jumpHeight;
    private Position heroPos;
    protected float bodyHeight;
    protected float bodyWidth;
    private boolean elite;
    protected AttackBehavior attackBehavior;
    protected JumpBehavior jumpBehavior;
    protected MoveBehavior moveBehavior;

    public Enemy() {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
        this.flying = false;
        this.attackRange = 25/PPM;
        this.jumpHeight = 6;
        this.position = new Position(200, 50);
    }

    public Enemy(int maxHP, int currentHP, int attackPoints, float attackRange, int movementSpeed, int jumpHeight, boolean flying,
                 float posX, float posY, int level, boolean elite) {
        this.movementSpeed = movementSpeed;
        this.jumpHeight = jumpHeight;
        this.attackPoints = attackPoints;
        this.attackRange = attackRange;
        this.currentHealthPoints = currentHP;
        this.maxHealthPoints = maxHP;
        this.flying = flying;
        this.position = new Position(posX, posY);
        this.level = level; // Enemies level, scales up all stats by some factor.
        this.elite = elite; // Whether enemy has 'elite' status or not. Elite enemies are stronger.
    }

    public void setMoveBehavior(MoveBehavior moveBehavior){
        this.moveBehavior = moveBehavior;
    }

    public void setAttackBehavior(AttackBehavior attackBehavior){
        this.attackBehavior = attackBehavior;
    }

    public void setJumpBehavior(JumpBehavior jumpBehavior){
        this.jumpBehavior = jumpBehavior;
    }

    public void setEnemyDirection(){
        if(this.getPosition().getXPos() > Hero.getInstance().getX()){
            this.setDirection(Direction.LEFT);
        }
        else{
            this.setDirection(Direction.RIGHT);
        }
    }

    public boolean heroInRange(){
        return((Math.abs((this.getX() + (this.getBodyWidth()/2)/PPM) - (Hero.getInstance().getX() + 5/PPM)) <= this.attackRange/PPM) &&
                (Math.abs((this.getY() + (this.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) <= 38/PPM));
    }

    public void performMove(){
        moveBehavior.move(this.getDirection(), this.movementSpeed);
    }

    public void performJump(){
        jumpBehavior.jump(this.jumpHeight);
    }

    public void performAttack(){
        attackBehavior.attack(this.getDirection());
    }

    //for testing purposes, prints.
    @Override
    public void takeDamage(int incomingDamage) {
        super.takeDamage(incomingDamage);
        //enemyMovement.takeDamage();
        System.out.println("Enemy " + this.toString() + " took: " + incomingDamage + " damage and is now at: " + this.currentHealthPoints + " hp");
    }

    public float getBodyHeight(){
        return bodyHeight;
    }

    public float getBodyWidth(){
        return bodyWidth;
    }

    public float getMovementSpeed(){
        return this.movementSpeed;
    }
}