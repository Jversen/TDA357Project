package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.View.Hud;


/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public abstract class Creature {

    protected int currentHealthPoints;
    protected int maxHealthPoints;
    protected int attackPoints;
    protected float movementSpeed;
    protected float verticalSpeed;
    protected Position position;
    protected MovementState movementState = MovementState.STANDING;
    protected Direction direction = Direction.RIGHT;
    protected int level;
    protected boolean creatureGrounded;
    protected boolean creatureFalling;
    protected Hud hud = Hud.getInstance();

    protected boolean creatureDead;

    protected boolean attackInProgress;

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void takeDamage(int incomingDamage) {
        currentHealthPoints = currentHealthPoints - incomingDamage;
    }

    public boolean isCreatureDying() {
        if (currentHealthPoints < 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setCreatureGrounded(boolean creatureGrounded) {
        this.creatureGrounded = creatureGrounded;
    }

    public void setCreatureFalling(boolean creatureFalling) {
        this.creatureFalling = creatureFalling;
    }

    public void setX(float x) {
        this.position.setXPos(x);
    }

    public float getX() {
        return this.position.getXPos();
    }

    public void setY(float y) {
        this.position.setYPos(y);
    }

    public float getY() {
        return this.position.getYPos();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MovementState getMovementState() {
        return movementState;
    }

    public void setMovementState(MovementState movementState) {
        this.movementState = movementState;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setHealthPoints(int HP) {
        if (this.maxHealthPoints >= HP || HP >= 0) {
            this.currentHealthPoints = HP;
        }
    }

    public void decreaseHealthPoints(int HP) {
        if (HP > 0) {
            this.currentHealthPoints -= HP;
        }
        if (currentHealthPoints < 0) {
            currentHealthPoints = 0;
        }
    }
}