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
        this.itemName = "Black Dagger";

        this.strengthRequirement = 0;
        this.agilityRequirement = 0;
        this.intellectRequirement = 0;

        this.itemName = "Black Dagger";
        this.description = ("There once was an thought of dark and light souls. This item is all that remains.");

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
