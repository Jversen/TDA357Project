package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.MovementState;

/**
 * Created by hilden on 2015-05-04.
 */
public class HeroView extends CreatureView {

    private Animation fallingAnimation;

    private String spritesheetPathFalling;
    private String atlasFilePathFalling;
    private Float animationSpeedFalling;

    private Animation jumpingAnimation;

    private String spritesheetPathJumping;
    private String atlasFilePathJumping;
    private Float animationSpeedJumping;


    public HeroView() {
        creature = Hero.getInstance();

        spritesheetPathRun = "Data//HeroAnimations//HeroRunning//HeroRunningRight.png";
        atlasFilePathRun = "Data//HeroAnimations//HeroRunning//HeroRunningRight.atlas";
        animationSpeedRun = 1 / 10f;

        spritesheetPathIdle = "Data//HeroAnimations//HeroIdle//HeroIdle.png";
        atlasFilePathIdle = "Data//HeroAnimations//HeroIdle//HeroIdle.atlas";
        animationSpeedIdle = 1f;

        spritesheetPathFalling = "Data//HeroAnimations//HeroFalling//HeroFalling.png";
        atlasFilePathFalling = "Data//HeroAnimations//HeroFalling//HeroFalling.atlas";
        animationSpeedFalling = 1f;

        spritesheetPathJumping = "Data//HeroAnimations//HeroJumping//HeroJumping.png";
        atlasFilePathJumping = "Data//HeroAnimations//HeroJumping//HeroJumping.atlas";
        animationSpeedJumping = 1 / 10f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    @Override
    public void initAnimations() {
        super.initAnimations();

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathFalling)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathFalling));
        fallingAnimation = new Animation(animationSpeedIdle, atlas.getRegions());

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathJumping)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathJumping));
        jumpingAnimation = new Animation(animationSpeedJumping, atlas.getRegions());
    }

    @Override
    protected Animation getCurrentAnimation() {
        if (creature.getMovementState() == MovementState.WALKING) {
            return runningAnimation;
        } else if (creature.getMovementState() == MovementState.FALLING) {
            return fallingAnimation;
        } else if (creature.getMovementState() == MovementState.JUMPING) {
            if (stateTime > 0.65) {      //A check to see if the jumping animation has played, if so, move on to FALLING.
                creature.setMovementState(MovementState.FALLING);
            }
            return jumpingAnimation;
        } else {
            return idleAnimation;
        }
    }
}
