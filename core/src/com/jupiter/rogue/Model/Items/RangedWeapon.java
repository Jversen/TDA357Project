package com.jupiter.rogue.Model.Items;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class RangedWeapon extends Weapon {
    protected int durability;
    protected Float projectileSpeed;

    /**
     * returns the movement speed of the projectile fired by the weapons
     * @return projectile speed
     */
    public Float getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * decreases the weapons durability
     */
    public void decreaseDurability() {
        durability--;
    }
}
