package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.badlogic.gdx.math.Matrix4.*;

import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;

/**
 * Created by hilden on 2015-05-04.
 */
public class HeroView {

    private Sprite sprite;
    private Texture spriteSheet;
    private TextureAtlas atlas;
    private TextureRegion currentFrame;
    private Animation animation;
    private SpriteBatch spriteBatch;

    private Hero hero;

    private float stateTime;

    private Animation runningAnimation;
    private Animation idleAnimation;


    public HeroView() {
        hero = Hero.getInstance();
        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initHeroAnimations();
    }

    private void initHeroAnimations() {

        //Running animation
        spriteSheet = new Texture(Gdx.files.internal("Data//HeroRunning//HeroRunningRight.png"));
        atlas = new TextureAtlas("Data//HeroRunning//HeroRunningRight.atlas");
        runningAnimation = new Animation(1/10f, atlas.getRegions());

        //Idle animation
        spriteSheet = new Texture(Gdx.files.internal("Data//HeroIdle//HeroIdleRight.png"));
        atlas = new TextureAtlas("Data//HeroIdle//HeroIdleRight.atlas");
        idleAnimation = new Animation(1, atlas.getRegions());
    }

    //Returns the current animation depending on the hero MovementState.
    private Animation getCurrentAnimation() {

        if (hero.getMovementState() == MovementState.WALKING) {
            return runningAnimation;
        } else {
            return idleAnimation;
        }
    }

    public void updateAnimation(float deltaTime, Matrix4 projectionMatrix){


        animation = getCurrentAnimation();
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(hero.getX() * PPM, hero.getY() * PPM);

        /* Draws the current frame of the hero animation, at position x,y of it's body
        scaled to the PPM, its origin offset (for scaling and rotating) at half the body
        (A bit unsure about that, got it right by experimenting),
        full size of the textureregion, and finally scaled to PPM and rotated to match
        the rotation of the body.
         */
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(projectionMatrix);
        spriteBatch.draw(currentFrame,
                hero.getX() * PPM,
                hero.getY() * PPM,
                0.5f,
                0.5f,
                1f, 1.8f,
                getDirValue() * PPM, PPM, 0);

        spriteBatch.end();
    }

    //Returns an int value of the enum Direction. -1 for left, 1 for right.
    //Added to help set the spritescale to negative if walking left.
    //This is also where you set the SCALE of the sprites.
    private int getDirValue() {
        if (hero.getDirection() == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    }

}
