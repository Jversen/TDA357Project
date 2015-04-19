package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Texture;
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

    private Hero () {
        super.maxHealthPoints = 100;
        super.currentHealthPoints = maxHealthPoints;
        super.movementSpeed = 10;
        super.texture = new Texture("pixHero_01.png");
        super.setBounds(super.texture.getWidth(),super.texture.getHeight());
        System.out.println(super.getBoundsX() + " "+ super.getBoundsY());
        //TODO finish rest of stats
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero();
        }
        return instance;
    }
}
