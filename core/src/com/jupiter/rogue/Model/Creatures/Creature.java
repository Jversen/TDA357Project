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

    protected boolean incapacitated;
    protected boolean invulnerable;
    protected Timer timer = new Timer();

    /**
     * sets the creatures position in the room
     * @param x the creature's position in the x-axis
     * @param y the creature's position in the y axis
     */
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    /**
     * reduces the creatures health points with the amount of incoming damage
     * @param incomingDamage the amount of damage from the damage source
     */
    public void takeDamage(int incomingDamage) {
        if (!invulnerable) {
            currentHealthPoints = currentHealthPoints - incomingDamage;
            invulnerable = true;
            incapacitated = true;
            timer.schedule(new RemoveInvulnerabilityTask(), 500);
        }
    }

    /**
     * checks if the creature is dying
     * @return return whether the creature's health is at 0 or below
     */
    public boolean isCreatureDying() {
        if (currentHealthPoints < 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * makes the creature grounded
     * @param creatureGrounded whether the creature is standing on the ground or not
     */
    public void setCreatureGrounded(boolean creatureGrounded) {
        this.creatureGrounded = creatureGrounded;
    }

    /**
     * makes the creature falling
     * @param creatureFalling whether the creature is falling in the air or not
     */
    public void setCreatureFalling(boolean creatureFalling) {
        this.creatureFalling = creatureFalling;
    }

    /**
     * sets the creatures x position
     * @param x the desired position in the x axis
     */
    public void setX(float x) {
        this.position.setXPos(x);
    }

    /**
     * gets the creatures x position
     * @return the creatures position in x axis
     */
    public float getX() {
        return this.position.getXPos();
    }

    /**
     * sets the creatures y position
     * @param y the desired position in the y axis
     */
    public void setY(float y) {
        this.position.setYPos(y);
    }

    /**
     * gets the creatures y position
     * @return the creatures position in y axis
     */
    public float getY() {
        return this.position.getYPos();
    }

    /**
     * sets the creatures facing direction
     * @param direction the desired direction of the creature
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * gets the creatures movement state
     * @return the creatures movement state
     */
    public MovementState getMovementState() {
        return movementState;
    }

    /**
     * sets the creatures movement state
     * @param movementState the desired movement state of the creature
     */
    public void setMovementState(MovementState movementState) {
        this.movementState = movementState;
    }

    /**
     *  sets the value of the creatures health points
     * @param HP the desired value of health points of the creature
     */
    public void setHealthPoints(int HP) {
        if (this.maxHealthPoints >= HP && HP >= 0) {
            this.currentHealthPoints = HP;
        }
    }

    /**
     * a method for a creature to walk
     * @param direction the direction the creature should walk in
     */
    public void walk(Direction direction) {

        if (creatureGrounded) { //To prevent the hero from walking mid air.
            setMovementState(MovementState.WALKING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }
        setDirection(direction);
    }

    /**
     * makes the creature jump
     */
    public void jump() {
        setMovementState(MovementState.JUMPING);
    }

    /**
     * makes the creature idle
     */
    public void relax() {
        if (creatureGrounded) { //To prevent the hero standing mid air.
            setMovementState(MovementState.STANDING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }
    }

    /**
     * does nothing
     */
    public void attack () {
    }

    /**
     * sets the creatures movement state to dying
     */
    public void die () {
        setMovementState(MovementState.DYING);
    }

    /**
     * makes the creature vulnerable again after a time of invulnerability
     */
    class RemoveInvulnerabilityTask extends TimerTask {
        public void run() {
            invulnerable = false;
            incapacitated = false;
        }
    }
}