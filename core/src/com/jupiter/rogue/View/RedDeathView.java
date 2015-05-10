package com.jupiter.rogue.View;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jupiter.rogue.Model.Creatures.RedDeath;


/**
 * Created by hilden on 2015-05-08.
 */
public class RedDeathView extends EnemyView {

    public RedDeathView(RedDeath redDeath) {
        creature = redDeath;

        spritesheetPathRun = "Data//pixHeroAtlas.png";
        atlasFilePathRun = "Data//pixHeroAtlas.atlas";
        animationSpeedRun = 1 / 3f;

        spritesheetPathIdle = "Data//pixHeroAtlas.png";
        atlasFilePathIdle = "Data//pixHeroAtlas.atlas";
        animationSpeedIdle = 1f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }
}

























