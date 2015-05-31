package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Chests.Chest;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Model.Items.*;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Utils.WorldConstants;

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
    private int experienceRoof; //A variable to hold the amount of xp needed to lvl up.
    private int strength;
    private int agility;
    private int intellect;
    private int statPoints;

    private boolean touchingChest;
    private Chest usableChest;

    private Hero () {
        position = new Position();
        this.creatureGrounded = false;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 4;
        this.meleeCurrentWeapon = true;

        //Starting weapons
        this.meleeWeapon = new BasicSword();
        this.rangedWeapon = new Boomstick();

        //Stats and experience
        level = 1;
        experiencePoints = 0;
        experienceRoof = 100;
        strength = 1;
        agility = 1;
        intellect = 1;
        statPoints = 0;

        this.creatureDead = false;
        this.invulnerable = false;

        this.setPosition(WorldConstants.HERO_START_XPOS,
        WorldConstants.HERO_START_YPOS);
        this.movementState = MovementState.STANDING;
        this.direction = Direction.RIGHT;
    }

    /**
     *
     * @return returns the singleton instance of hero
     */
    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero();
        }
        return instance;
    }

    @Override
    /**
     * does nothing but decrease durability on ranged weapons
     */
    public void attack() {
        if (isMeleeCurrentWeapon()) {

        } else {
            ((RangedWeapon)getCurrentWeapon()).decreaseDurability();
        }
    }

    /**
     * method to increase experience points and if the experience roof is reached, level the hero up
     * @param experienceGain the amount of experience the hero gains
     */
    public void increaseExperience(int experienceGain) {
        this.experiencePoints = this.experiencePoints + experienceGain;
        if (experiencePoints >= experienceRoof) {
            levelUp();
        }
    }

    /**
     * increases the heros level and gives the hero a stat point. increases the experience roof
     */
    private void levelUp() {
        level++;
        statPoints++;
        experiencePoints = experiencePoints - experienceRoof;
        experienceRoof = experienceRoof + 50;
    }

    /**
     * increases one of the heros attributes
     * @param statToIncrease which stat to increase
     */
    public void increaseStats(String statToIncrease) {
        if (statPoints >= 1) {
            if (statToIncrease.equals("strength")) {
                strength++;
                statPoints--;
            } else if (statToIncrease.equals("intellect")) {
                intellect++;
                statPoints--;
            } else if (statToIncrease.equals("agility")) {
                agility++;
                statPoints--;
            } else {
                System.out.println("The method increaseStats did not recieve the correct string of 'strength', 'intellect' or 'agility'.");
            }
        }
    }

    @Override
    /**
     * sets the heros health points
     */
    public void setHealthPoints(int HP) {
        super.setHealthPoints(HP);
    }

    /**
     * returns the current weapon
     * @return the current weapon
     */
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

    /**
     * swaps between melee and range hero
     */
    public void swapWeapon() {
        if (rangedWeapon != null) {
            meleeCurrentWeapon ^= true;    //Swap the value of meleeCurrentWeapon
        }
    }

    /**
     * picks up and equips items from chests
     * @param item the item that is in the chest
     */
    public void pickUpItem(Item item) {
        if (item instanceof MeleeWeapon) {
            this.meleeWeapon = (MeleeWeapon)item;

        }
        else if (item instanceof RangedWeapon) {
            this.rangedWeapon = (RangedWeapon)item;

        }
        else if (item instanceof Ring) {
            if (ringRight == null) {
                ringRight = (Ring)item;

            } else if (ringLeft == null) {
                ringLeft = (Ring)item;

            }
        }
    }

}