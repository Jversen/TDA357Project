package com.jupiter.rogue.Model.Items;

/**
 * Created by hilden on 2015-05-16.
 */
public class DoubleBarreled extends RangedWeapon {

    public DoubleBarreled() {
        this.damage = 150;
        this.durability = 2;
        this.projectileSpeed = 3000f;

        //hitBoxValues for the projectile fired by the weapon
        this.hitBoxLength = 1;
        this.hitBoxHeight = 1;
        this.hitBoxX = 0;
        this.hitBoxY = 20;
        this.hitBoxTilt = 0f;

        super.initWeapon();
    }
}