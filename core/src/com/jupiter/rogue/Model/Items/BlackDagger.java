package com.jupiter.rogue.Model.Items;

/**
 * Created by hilden on 2015-05-16.
 */
public class BlackDagger extends MeleeWeapon {

    public BlackDagger() {
        this.damage = 200;
        this.attackSpeed = 100;
        this.animationSpeed = 100;

        //hitBoxValues
        this.hitBoxLength = 5;
        this.hitBoxHeight = 2;
        this.hitBoxX = 15;
        this.hitBoxY = 17;
        this.hitBoxTilt = 10f;

        super.initWeapon();
    }
}
