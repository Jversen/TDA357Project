package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;


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

    protected int attackPoints;

    protected int attackHitBoxWidth;
    protected int attackHitBoxHeight;
    protected int attackHitBoxX;
    protected int attackHitBoxY;

    protected int xpValue;

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
        this.invulnerable = false;
        this.movementState = MovementState.STANDING;
        this.direction = Direction.RIGHT;
        this.creatureGrounded = true;
    }

    public void setEnemyDirection(){
        if (this.getX() > Hero.getInstance().getX()){
            this.setDirection(Direction.LEFT);
        } else {
            this.setDirection(Direction.RIGHT);
        }
    }


    @Override
    public void walk(Direction direction) {
        if (creatureGrounded) { //To prevent the hero from walking mid air.
            setMovementState(MovementState.WALKING);
        }
    }
}