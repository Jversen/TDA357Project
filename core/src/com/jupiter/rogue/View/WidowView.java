package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Model.Enums.MovementState;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public class WidowView extends EnemyView {

    private String spritesheetPathDying;
    private String atlasFilePathDying;
    private Float animationSpeedDying;

    private Animation dyingAnimation;

    public WidowView(Widow widow) {
        creature = widow;
        spritesheetPathRun = "Data//EnemyAnimations//Widow//walking//widowWalking.png";
        atlasFilePathRun = "Data//EnemyAnimations//Widow//walking//widowWalking.atlas";
        animationSpeedRun = 1 / 33f;

        spritesheetPathIdle = "Data//EnemyAnimations//Widow//idle//widowIdle.png";
        atlasFilePathIdle = "Data//EnemyAnimations//Widow//idle//widowIdle.atlas";
        animationSpeedIdle = 1 / 20f;

        spritesheetPathDying = "Data/EnemyAnimations/Widow/dyingFront/widowDyingFront.png";
        atlasFilePathDying = "Data/EnemyAnimations/Widow/dyingFront/widowDyingFront.atlas";
        animationSpeedDying = 1 / 20f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    @Override
    public void initAnimations() {
        super.initAnimations();

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathDying)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathDying));
        dyingAnimation = new Animation(animationSpeedDying, atlas.getRegions());

       // dyingAnimationTime = jumpingAnimation.getAnimationDuration() - jumpingAnimation.getFrameDuration();  //Sets the animation time to the animation duration minus one frame
        //because first frame runs twice for some reason.
    }

    @Override
    protected Animation getCurrentAnimation() {
        if (creature.getMovementState() == MovementState.WALKING) {
            return runningAnimation;
        } else if (creature.getMovementState() == MovementState.DYING) {
            return dyingAnimation;
        } else {
            return idleAnimation;
        }
    }
}
