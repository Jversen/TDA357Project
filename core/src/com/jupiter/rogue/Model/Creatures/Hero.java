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

    private Hero (float xPos, float yPos) {

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
        spriteSheet = new Texture(Gdx.files.internal("Data//pixHeroAtlas.png"));
        atlas = new TextureAtlas("Data//pixHeroAtlas.atlas");
        animation = new Animation(1/15f, atlas.getRegions());
        stateTime = 0f;
    }

    public void updateAnimation(float deltaTime){
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);

        spriteBatch.begin();

        /* Draws the current frame of the hero animation, at position x,y of it's body
        scaled to the PPM, its origin offset (for scaling and rotating) at half the body
        (A bit unsure about that, got it right by experimenting),
        full size of the textureregion, and finally scaled to PPM and rotated to match
        the rotation of the body.
         */
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
                //
                getBody().applyLinearImpulse(new Vector2(2f,0f), body.getPosition(), true);
                if(getBody().getLinearVelocity().x > 2f) {
                    getBody().setLinearVelocity(2, getBody().getLinearVelocity().y);
                }
            } else {
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
        setMovementState(MovementState.STANDING);
    }

    public void attack() {
        System.out.println("attack!");
    }


}
