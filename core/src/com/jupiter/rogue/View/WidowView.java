package com.jupiter.rogue.View;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public class WidowView extends EnemyView {

    public WidowView(Widow widow) {
        creature = widow;
        spritesheetPathRun = "Animations/Widow/walking/widowWalking.png";
        atlasFilePathRun = "/Animations/Widow/walking/widowWalking.atlas";
        animationSpeedRun = 1 / 33f;

        spritesheetPathIdle = "Animations/Widow/walking/widowIdle.png";
        atlasFilePathIdle = "Animations/Widow/walking/widowIdle.atlas";
        animationSpeedIdle = 1 / 33f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }
}
