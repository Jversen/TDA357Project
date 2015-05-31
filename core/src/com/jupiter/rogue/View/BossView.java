package com.jupiter.rogue.View;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jupiter.rogue.Model.Creatures.Boss;

/**
 * Created by hilden on 2015-05-31.
 */
public class BossView extends EnemyView {

    private String spritesheetPathAttack1;
    private String atlasFilePathAttack1;
    private Float animationSpeedAttack1;
    private Animation attack1Animation;

    private String spritesheetPathAttack2;
    private String atlasFilePathAttack2;
    private Float animationSpeedAttack2;
    private Animation attack2Animation;

    private String spritesheetPathAttack3;
    private String atlasFilePathAttack3;
    private Float animationSpeedAttack3;
    private Animation attack3Animation;

    private String spritesheetPathAttack4;
    private String atlasFilePathAttack4;
    private Float animationSpeedAttack4;
    private Animation attack4Animation;


    public BossView(Boss boss) {
        creature = boss;
        spritesheetPathRun = "Data//EnemyAnimations//Widow//walking//widowWalking.png";
        atlasFilePathRun = "Data//EnemyAnimations//Widow//walking//widowWalking.atlas";
        animationSpeedRun = 1 / 33f;

        spritesheetPathIdle = "Data//EnemyAnimations//Widow//idle//widowIdle.png";
        atlasFilePathIdle = "Data//EnemyAnimations//Widow//idle//widowIdle.atlas";
        animationSpeedIdle = 1 / 33f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    @Override
    public void initAnimations() {

    }

    @Override
    public Animation getCurrentAnimation() {
        return null;
    }
}
