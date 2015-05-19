package com.jupiter.rogue.Model.Items;

/**
 * Created by hilden on 2015-05-16.
 */
public class DoubleBarreled extends RangedWeapon {

    public DoubleBarreled() {
        this.damage = 150;
        this.durability = 2;

        //hitBoxValues
        this.hitBoxLength = 75;
        this.hitBoxHeight = 2;
        this.hitBoxX = 75;
        this.hitBoxY = 17;
        this.hitBoxTilt = 0f;

        super.initWeapon();
    }
}