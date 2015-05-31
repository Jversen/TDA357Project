package com.jupiter.rogue.Model.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by hilden on 2015-05-16.
 */
public class BasicSword extends MeleeWeapon {

    public BasicSword() {
        this.damage = 50;
        this.attackSpeed = 350;
        this.animationSpeed = 350;
        this.itemName = "Basic Sword";

        //hitBoxValues
        this.hitBoxLength = 17;
        this.hitBoxHeight = 2;
        this.hitBoxX = 15;
        this.hitBoxY = 17;
        this.hitBoxTilt = 12f;

        super.initWeapon();
    }
}
