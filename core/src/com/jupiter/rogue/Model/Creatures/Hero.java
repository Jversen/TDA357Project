package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.MeleeWeapon;
import com.jupiter.rogue.Model.Items.RangedWeapon;

/**
 * Created by Johan on 16/04/15.
 */
public class Hero extends Creature {

    private static Hero instance = null;

    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;
    private MovementState movementState = MovementState.JUMPING;

    private Hero () {
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 10;
        //TODO finish rest of stats
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero();
        }
        return instance;
    }
}
