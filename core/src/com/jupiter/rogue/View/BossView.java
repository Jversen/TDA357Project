package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jupiter.rogue.Model.Creatures.Boss;
import com.jupiter.rogue.Utils.Enums.MovementState;

/**
 * Created by hilden on 2015-05-31.
 */
public class BossView extends EnemyView {

    private String spritesheetPathAttack1;
    private String atlasFilePathAttack1;
    private Float animationSpeedAttack1;
    private Animation attackAnimation1;

    private String spritesheetPathAttack2;
    private String atlasFilePathAttack2;
    private Float animationSpeedAttack2;
    private Animation attackAnimation2;

    public BossView(Boss boss) {
        creature = boss;
        spritesheetPathRun = "";
        atlasFilePathRun = "";
        animationSpeedRun = 0f;

        spritesheetPathIdle = "Data//EnemyAnimations//Boss//BossIdle//BossIdle.png";
        atlasFilePathIdle = "Data//EnemyAnimations//Boss//BossIdle//BossIdle.atlas";
        animationSpeedIdle = 1/15f;

        spritesheetPathAttack1 = "Data//EnemyAnimations//Boss//BossAttack1//BossAttack1.png";
        atlasFilePathAttack1 = "Data//EnemyAnimations//Boss//BossAttack1//BossAttack1.atlas";
        animationSpeedAttack1 = 1/15f;

        spritesheetPathAttack2 = "Data//EnemyAnimations//Boss//BossAttack2//BossAttack2.png";
        atlasFilePathAttack2 = "Data//EnemyAnimations//Boss//BossAttack2//BossAttack2.atlas";
        animationSpeedAttack2 = 1/15f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    @Override
    public void initAnimations() {

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathIdle)));
        atlas = new TextureAtlas((Gdx.files.internal(atlasFilePathIdle)));
        idleAnimation = new Animation(animationSpeedIdle, atlas.getRegions());
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathAttack1)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathAttack1));
        attackAnimation1 = new Animation(animationSpeedAttack1, atlas.getRegions());

        spriteSheet = new Texture((Gdx.files.internal(spritesheetPathAttack2)));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathAttack2));
        attackAnimation2 = new Animation(animationSpeedAttack2, atlas.getRegions());
    }

    @Override
    public Animation getCurrentAnimation() {
        if (creature.getMovementState() == MovementState.DYING) {
            return null;
        } else if (((Boss)creature).isAttackInProgress1()) {
            return attackAnimation1;
        } else if (((Boss)creature).isAttackInProgress2()) {
            return attackAnimation2;
        } else {
            return idleAnimation;
        }
    }
}