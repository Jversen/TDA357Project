package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Utils.Enums.MovementState;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public class WidowView extends EnemyView {

    private String spritesheetPathDying;
    private String atlasFilePathDying;
    private Float animationSpeedDying;

    private String spritesheetPathAttacking;
    private String atlasFilePathAttacking;
    private Float animationSpeedAttacking;

    private Animation dyingAnimation;
    private Animation attackAnimation;

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

        spritesheetPathAttacking = "Data//EnemyAnimations//Widow//attacking//widowIdle.png"; //This png and atlas has the wrong names but are correct.
        atlasFilePathAttacking = "Data//EnemyAnimations//Widow//attacking//widowIdle.atlas";
        animationSpeedAttacking = 1/30f;

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

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathAttacking)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathAttacking));
        attackAnimation = new Animation(animationSpeedAttacking, atlas.getRegions());
    }

    @Override
    protected Animation getCurrentAnimation() {
        if (creature.isAttackInProgress()) {
            return attackAnimation;
        } else {
            if (creature.getMovementState() == MovementState.WALKING) {
                return runningAnimation;
            } else if (creature.getMovementState() == MovementState.DYING) {
                return dyingAnimation;
            } else {
                return idleAnimation;
            }
        }
    }
}
