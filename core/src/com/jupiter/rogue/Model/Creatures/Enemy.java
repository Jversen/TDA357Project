package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Controller.AIBehaviors.AttackBehaviors.AttackBehavior;
import com.jupiter.rogue.Controller.AIBehaviors.JumpBehaviors.JumpBehavior;
import com.jupiter.rogue.Controller.AIBehaviors.MoveBehaviors.MoveBehavior;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class Enemy extends Creature {

    private boolean flying;
    private float attackRange;
    private int jumpHeight;
    private Position heroPos;
    private boolean elite;

    protected float bodyHeight;
    protected float bodyWidth;
    protected float bodyY;

    protected AttackBehavior attackBehavior;
    protected JumpBehavior jumpBehavior;
    protected MoveBehavior moveBehavior;

    protected int attackHitBoxWidth;
    protected int attackHitBoxHeight;
    protected int attackHitBoxX;
    protected int attackHitBoxY;

    protected int xpValue;

    public Enemy() {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = 100;
        this.attackPoints = 100;
        this.movementSpeed = 100;
        this.flying = false;
        this.attackRange = 25/PPM;
        this.jumpHeight = 6;
        this.position = new Position(200, 50);
        this.creatureDead = false;
        this.attackInProgress = false;
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
        this.creatureDead = false;
        this.attackInProgress = false;
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

    public void performMove(){
        this.setMovementState(MovementState.WALKING);
        moveBehavior.move(this.getDirection(), this.movementSpeed);
    }

    public void performJump(){
        jumpBehavior.jump(this.jumpHeight);
    }

    public void performAttack() {
        this.setMovementState(MovementState.ATTACKING);
        attackBehavior.attack(this.getDirection());
    }

    public void performIdle() {
        this.setMovementState(MovementState.STANDING);
    }

    //for testing purposes, prints.
    @Override
    public void takeDamage(int incomingDamage) {
        super.takeDamage(incomingDamage);
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