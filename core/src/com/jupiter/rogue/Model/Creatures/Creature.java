package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Utils.Position;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public abstract class Creature {

    protected int currentHealthPoints;
    protected int maxHealthPoints;
    protected float movementSpeed;
    protected float verticalSpeed;
    protected Position position;
    protected MovementState movementState;
    protected Direction direction;
    protected int level;
    protected boolean creatureGrounded;
    protected boolean creatureFalling;

    protected boolean creatureDead;

    protected boolean attackInProgress;

    protected boolean invulnerable;
    protected Timer timer = new Timer();

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void takeDamage(int incomingDamage) {
        if (!invulnerable) {
            currentHealthPoints = currentHealthPoints - incomingDamage;
            invulnerable = true;
            timer.schedule(new RemoveInvulnerabilityTask(), 1000);
        }
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

    public void setHealthPoints(int HP) {
        if (this.maxHealthPoints >= HP && HP >= 0) {
            this.currentHealthPoints = HP;
        }
    }


    public void walk(Direction direction) {

        if (creatureGrounded) { //To prevent the hero from walking mid air.
            setMovementState(MovementState.WALKING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }
        setDirection(direction);
    }

    public void jump() {
        setMovementState(MovementState.JUMPING);
    }

    public void relax() {
        if (creatureGrounded) { //To prevent the hero standing mid air.
            setMovementState(MovementState.STANDING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }
    }

    public void attack () {
    }

    //A nestled class to implement a timertask. Timertask to control time for creatures to stop being invulnerable after an attack.
    class RemoveInvulnerabilityTask extends TimerTask {
        public void run() {
            invulnerable = false;
        }
    }
}