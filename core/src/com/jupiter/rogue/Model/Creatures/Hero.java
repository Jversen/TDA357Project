package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.MeleeWeapon;
import com.jupiter.rogue.Model.Items.RangedWeapon;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Map.WorldConstants;
import com.jupiter.rogue.Utils.libGDX.HeroMovement;


import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class Hero extends Creature {

    // Singleton-instance of hero
    private static Hero instance = null;

    // Inventory-spots
    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;

    private Hero (float xPos, float yPos) {
        this.nbrOfPlatformsTouched = 0;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 100;

        this.position = WorldConstants.HERO_START_POSITION;

        //TODO finish rest of stats
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero(100,100);
        }
        return instance;
    }

    public void walk(Direction direction, HeroMovement heroMovement) {
        setMovementState(MovementState.WALKING);
        setDirection(direction);
        heroMovement.walk(direction);
        setPosition(heroMovement.getPosition());
    }

    public void jump(HeroMovement heroMovement) {
        if(creatureIsGrounded()) {
            setMovementState(MovementState.JUMPING);
            heroMovement.jump();
            setPosition(heroMovement.getPosition());
        }
    }

    public void relax(HeroMovement heroMovement) {
        setMovementState(MovementState.STANDING);
    }

    public void attack(HeroMovement heroMovement) {
        System.out.println("attack!");
    }
}