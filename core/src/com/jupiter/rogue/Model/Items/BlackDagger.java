package com.jupiter.rogue.Model.Items;

/**
 * Created by hilden on 2015-05-16.
 */
@lombok.Data
public class BlackDagger extends MeleeWeapon {

    public BlackDagger() {
        this.damage = 200;
        this.attackSpeed = 100;
        this.animationSpeed = 100;
        this.weaponName = "Black Dagger";

        this.strengthRequirement = 1;
        this.agilityRequirement = 2;
        this.intellectRequirement = 1;

        this.description = ("There once was a creature doomed to eternal slavery to a terrible creature far, far below." +
                "He forged a dagger of dark matter to end his own life.");

        String description;

        //hitBoxValues
        this.hitBoxLength = 5;
        this.hitBoxHeight = 2;
        this.hitBoxX = 15;
        this.hitBoxY = 17;
        this.hitBoxTilt = 10f;

        super.initWeapon();
    }
}
