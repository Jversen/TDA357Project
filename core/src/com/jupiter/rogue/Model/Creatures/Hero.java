package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.MeleeWeapon;
import com.jupiter.rogue.Model.Items.RangedWeapon;
import com.jupiter.rogue.Model.Map.Position;


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

    private Animation runningAnimationRight;  //Possibly move to creature.
    private Animation runningAnimationLeft;
    private Animation idleAnimationRight;
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
        initAnimation();

        //TODO finish rest of stats
    }

    private void initAnimation() {

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroRunningRight//HeroRunningRight.png"));
        atlas = new TextureAtlas("Data//HeroRunningRight//HeroRunningRight.atlas");
        runningAnimationRight = new Animation(1/10f, atlas.getRegions());
        stateTime = 0f;

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroRunningLeft//HeroRunningLeft.png"));
        atlas = new TextureAtlas("Data//HeroRunningLeft//HeroRunningLeft.atlas");
        runningAnimationLeft = new Animation(1/10f, atlas.getRegions());

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroIdleRight//HeroIdleRight.png"));
        atlas = new TextureAtlas("Data//HeroIdleRight//HeroIdleRight.atlas");
        idleAnimationRight = new Animation(1, atlas.getRegions());

        spriteSheet = new Texture(Gdx.files.internal("Data//HeroIdleLeft//HeroIdleLeft.png"));
        atlas = new TextureAtlas("Data//HeroIdleLeft//HeroIdleLeft.atlas");
        idleAnimationLeft = new Animation(1, atlas.getRegions());
    }

    public void updateAnimation(float deltaTime){

        animation = getCurrentAnimation();
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(body.getPosition().x*PPM, body.getPosition().y*PPM);
        body.setUserData(sprite);


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
                PPM, PPM,
                getBody().getAngle() * MathUtils.radiansToDegrees);

        spriteBatch.end();

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

    public void walk(Direction direction) {
        setMovementState(MovementState.WALKING);
        setDirection(direction);

        if(walkingIsPossible(direction, getPosition())) {

            if(direction == Direction.RIGHT) {
                setCurrentAnimation(runningAnimationRight);  //Sets the animation

                getBody().applyLinearImpulse(new Vector2(2f,0f), body.getPosition(), true);
                if(getBody().getLinearVelocity().x > 2f) {
                    getBody().setLinearVelocity(2, getBody().getLinearVelocity().y);
                }
            } else {
                setCurrentAnimation(runningAnimationLeft); //Sets the animation

                getBody().applyLinearImpulse(new Vector2(-2f,0f), body.getPosition(), true);
                if(getBody().getLinearVelocity().x < -2f) {
                    getBody().setLinearVelocity(-2, getBody().getLinearVelocity().y);
                }
            }
        }
    }

    public boolean walkingIsPossible(Direction direction, Position heroPosition) {
        return true;
    }

    public void jump() {
        if(heroIsGrounded()) {
            setMovementState(MovementState.JUMPING);
            getBody().setLinearVelocity(getBody().getLinearVelocity().x, 6);
        }
    }

    public void relax() {
        if (direction == Direction.RIGHT) {
            setCurrentAnimation(idleAnimationRight);
        } else if (direction == Direction.LEFT) {
            setCurrentAnimation(idleAnimationLeft);
        }
        setMovementState(MovementState.STANDING);
    }

    public void attack() {
        System.out.println("attack!");
    }


}
