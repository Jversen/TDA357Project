package com.jupiter.rogue.Model.Items;

/**
 * Created by hilden on 2015-05-16.
 */
@lombok.Data
public class Boomstick extends RangedWeapon {

    public Boomstick() {
        this.damage = 150;
        this.attackSpeed = 1200;
        this.animationSpeed = 1500;
        this.durability = 2;
        this.projectileSpeed = 2700f;
        this.weaponName = "Boomstick";

        this.strengthRequirement = 0;
        this.agilityRequirement = 0;
        this.intellectRequirement = 0;

        this.itemName = "Boom Stick";
        this.description = ("This delicate contraption was forged in a land beyond time, at the Grand Rapids. " +
                "It's got a walnut stock, cobalt blue steel, and a hair trigger.");

        //hitBoxValues for the projectile fired by the weapon
        this.hitBoxLength = 1;
        this.hitBoxHeight = 1;
        this.hitBoxX = 0;
        this.hitBoxY = 20;
        this.hitBoxTilt = 0f;

        super.initWeapon();
    }
}