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

    private Animation runningAnimation;  //Possibly move to creature.
    private Animation runningAnimationLeft;
    private Animation idleAnimation;
    private Animation idleAnimationLeft;
    private Animation currentAnimation;

    private Hero (float xPos, float yPos) {
        sprite = new Sprite();

        this.nbrOfPlatformsTouched = 0;
        scale = 1f;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = scale*100;

        spriteBatch = new SpriteBatch();

        this.position = WorldConstants.HERO_START_POSITION;
        initAnimation();

        //TODO finish rest of stats
    }

    private void initAnimation() {

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroRunning//HeroRunningRight.png"));
        atlas = new TextureAtlas("Data//HeroRunning//HeroRunningRight.atlas");
        runningAnimation = new Animation(1/10f, atlas.getRegions());
        stateTime = 0f;

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroIdle//HeroIdleRight.png"));
        atlas = new TextureAtlas("Data//HeroIdle//HeroIdleRight.atlas");
        idleAnimation = new Animation(1, atlas.getRegions());
    }

    public void updateAnimation(float deltaTime){

        animation = getCurrentAnimation();
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(getX() * PPM, getY() * PPM);

        /* Draws the current frame of the hero animation, at position x,y of it's body
        scaled to the PPM, its origin offset (for scaling and rotating) at half the body
        (A bit unsure about that, got it right by experimenting),
        full size of the textureregion, and finally scaled to PPM and rotated to match
        the rotation of the body.
         */
        spriteBatch.begin();
        spriteBatch.draw(currentFrame,
                getX() * PPM,
                getY() * PPM,
                0.5f,
                0.5f,
                1f, 1f,
                getDirValue() * PPM, PPM, 0);

        spriteBatch.end();
    }

    //Returns an int value of the enum Direction. -1 for left, 1 for right.
    //Added to help set the spritescale to negative if walking left.
    private int getDirValue() {
        if (direction == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    }

    private void setCurrentAnimation(Animation a) {
        this.currentAnimation = a;
    }

    private Animation getCurrentAnimation() {
        return currentAnimation;
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
        setCurrentAnimation(runningAnimation); //Sets the animation
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
        if (direction == Direction.RIGHT) {
            setCurrentAnimation(idleAnimation);
        } else if (direction == Direction.LEFT) {
            setCurrentAnimation(idleAnimation);
        }
        setMovementState(MovementState.STANDING);
    }

    public void attack(HeroMovement heroMovement) {
        System.out.println("attack!");
    }
}