package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by hilden on 2015-05-07.
 */
public abstract class CreatureView {

    protected Creature creature;

    protected Sprite sprite;
    protected Texture spriteSheet;
    protected TextureAtlas atlas;
    protected TextureRegion currentFrame;
    protected Animation animation;
    protected SpriteBatch spriteBatch;

    //Variables to set animationsoptions.
    protected String spritesheetPathRun;
    protected String atlasFilePathRun;
    protected Float animationSpeedRun;

    protected String spritesheetPathIdle;
    protected String atlasFilePathIdle;
    protected Float animationSpeedIdle;

    protected float stateTime;

    protected Animation runningAnimation;
    protected Animation idleAnimation;

    protected void initCreatureAnimations() {

        //Running animation
        spriteSheet = new Texture(Gdx.files.internal(spritesheetPathRun));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathRun));
        runningAnimation = new Animation(animationSpeedRun, atlas.getRegions());

        //Idle animation
        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathIdle)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathIdle));
        idleAnimation = new Animation(animationSpeedIdle, atlas.getRegions());
    }

  /*  public TextureRegion getCurrentFrame() {
        return currentFrame;
    } */

    private Animation getCurrentAnimation() {

        if (creature.getMovementState() == MovementState.WALKING) {
            return runningAnimation;
        } else {
            return idleAnimation;
        }
    }

    public void updateAnimation(float deltaTime, Matrix4 projectionMatrix) {

        animation = getCurrentAnimation();
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(creature.getX() * PPM, creature.getY() * PPM);

        /* Draws the current frame of the hero animation, at position x,y of it's body
        scaled to the PPM, its origin offset (for scaling and rotating) at half the body
        (A bit unsure about that, got it right by experimenting),
        full size of the textureregion, and finally scaled to PPM and rotated to match
        the rotation of the body.
         */


        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(projectionMatrix);
        spriteBatch.draw(currentFrame,
                creature.getX() * PPM,
                creature.getY() * PPM,
                0.5f,
                0.5f,
                1f, 1.5f,
                getDirValue() * PPM, PPM, 0);

        spriteBatch.end();
    }

    //Returns an int value of the enum Direction. -1 for left, 1 for right.
    //Added to help set the spritescale to negative if walking left.
    //This is also where you set the SCALE of the sprites.
    private int getDirValue() {
        if (creature.getDirection() == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    }
}
