package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Utils.Position;


/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class Enemy extends Creature {

    protected String enemyType;
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

    /**
     *
     * @param maxHP the enemys maximum health points
     * @param currentHP the enemys current health points
     * @param attackPoints the enemys attack points
     * @param attackRange the enemys attack range
     * @param movementSpeed the enemys movement speed
     * @param jumpHeight the enemys jump height
     * @param flying whether the enemy can fly or not
     * @param posX the enemys x pos
     * @param posY the enemys y pos
     * @param level the enemys level
     * @param elite whether if the enemy is elite or not
     */
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

    /**
     * sets the enemies direction depending on where the hero is. if the hero is left of the enemy, the enemy faces left
     * and vice versa
     */
    public void setEnemyDirection(){
        if (this.getX() > Hero.getInstance().getX()){
            this.setDirection(Direction.LEFT);
        } else {
            this.setDirection(Direction.RIGHT);
        }
    }
}