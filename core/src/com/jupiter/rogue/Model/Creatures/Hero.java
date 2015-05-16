package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.physics.box2d.PolygonShape;
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

    private boolean enemyInRangeRight;  //Variable to track if an enemy is in range of the heroes weapon on the right side.
    private boolean enemyInRangeLeft;  //Variable to track if an enemy is in range of the heroes weapon on the left side.


    private Hero (float xPos, float yPos) {
        this.creatureGrounded = false;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 100;
        this.meleeCurrentWeapon = true;
        this.meleeWeapon = new StartingWeapon();

        this.position = WorldConstants.HERO_START_POSITION;
        //TODO finish rest of stats
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
            return rangedWeapon;
        }
    }

    //swaps the value of meleeCurrentWeapon
    public void changeWeapon() {
        meleeCurrentWeapon ^= true;    //Swap the value of meleeCurrentWeapon
    }

    /*
    public void pickUpItem(Item item) {
        if (MeleeWeapon) {

        } else if (RangedWeapon) {

        }
    } */

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
        if (this.isEnemyInRangeRight() && this.direction == Direction.RIGHT) {
            System.out.println("ATTACK RIGHT");
            heroMovement.attack();
        } else if (this.isEnemyInRangeLeft() && this.direction == Direction.LEFT) {
            System.out.println("ATTACK LEFT");
            heroMovement.attack();
        }
    }
}