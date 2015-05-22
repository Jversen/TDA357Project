package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.*;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.Utils.HeroMovement;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class Hero extends Creature {

    // Singleton-instance of hero
    private static Hero instance = null;

    // Inventory-spots
    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;
    private boolean meleeCurrentWeapon;
    private Ring ringRight;
    private Ring ringLeft;

    // Experience and stats
    private int experiencePoints;
    private int strength;
    private int agility;
    private int intellect;
    private int statIncreasePoints;

    private Hero (float xPos, float yPos) {
        this.creatureGrounded = false;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 100;
        this.meleeCurrentWeapon = true;

        //Starting weapons
        this.meleeWeapon = new StartingWeapon();
        this.rangedWeapon = new DoubleBarreled();

        //Stats and experience
        experiencePoints = 0;
        strength = 1;
        agility = 1;
        intellect = 1;
        statIncreasePoints = 0;

        this.position = WorldConstants.HERO_START_POSITION;
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero(100,100);
        }
        return instance;
    }

    //returns the weapon currently in use
    public Weapon getCurrentWeapon() {
        if (meleeCurrentWeapon) {
            return meleeWeapon;
        } else {
            if (rangedWeapon != null) {
                return rangedWeapon;
            }
            return meleeWeapon;
        }
    }

    //swaps the value of meleeCurrentWeapon
    public void swapWeapon() {
        if (rangedWeapon != null) {
            meleeCurrentWeapon ^= true;    //Swap the value of meleeCurrentWeapon
        }
    }

    //a method for the hero to be able to pick up weapons.
    public void pickUpItem(Item item) {
        if (item instanceof MeleeWeapon) {
            this.meleeWeapon = (MeleeWeapon)item;
        } else if (item instanceof RangedWeapon) {
            this.rangedWeapon = (RangedWeapon)item;
        } else if (item instanceof Ring) {
            if (ringRight == null) {
                ringRight = (Ring)item;
            } else if (ringLeft == null) {
                ringLeft = (Ring)item;
            }
        }
    }

    public void walk(Direction direction, HeroMovement heroMovement) {

        if (creatureGrounded) { //To prevent the hero from walking mid air.
            setMovementState(MovementState.WALKING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }

        setDirection(direction);
        heroMovement.walk(direction);
        setPosition(heroMovement.getPosition());
    }

    public void jump(HeroMovement heroMovement) {

        //if(creatureGrounded) {
            setMovementState(MovementState.JUMPING);
            heroMovement.jump();
            setPosition(heroMovement.getPosition());
        //}
    }

    public void relax(HeroMovement heroMovement) {

        if (creatureGrounded) { //To prevent the hero standing mid air.
            setMovementState(MovementState.STANDING);
        } else if (creatureFalling) {  //To check if falling
            setMovementState(MovementState.FALLING);
        }
    }

    public void attack(HeroMovement heroMovement) {
    }
}