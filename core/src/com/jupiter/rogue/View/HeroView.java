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

    public HeroView() {
        creature = Hero.getInstance();

        spritesheetPathRun = "Data//HeroRunning//HeroRunningRight.png";
        atlasFilePathRun = "Data//HeroRunning//HeroRunningRight.atlas";
        animationSpeedRun = 1/10f;

        spritesheetPathIdle = "Data//HeroIdle//HeroIdle.png";
        atlasFilePathIdle = "Data//HeroIdle//HeroIdle.atlas";
        animationSpeedIdle = 1f;

        spritesheetPathFalling = "Data//HeroFalling//HeroFalling.png";
        atlasFilePathFalling = "Data//HeroFalling//HeroFalling.atlas";
        animationSpeedFalling = 1f;



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
    }

    @Override
    protected Animation getCurrentAnimation() {
        if (creature.getMovementState() == MovementState.WALKING) {
            return runningAnimation;
        } else if (creature.getMovementState() == MovementState.FALLING) {
            return fallingAnimation;
        } else {
            return idleAnimation;
    }
    }
}
