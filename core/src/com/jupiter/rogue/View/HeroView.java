package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by hilden on 2015-05-04.
 */
public class HeroView extends CreatureView {


    public HeroView() {
        creature = Hero.getInstance();

        spritesheetPathRun = "Data//HeroRunning//HeroRunningRight.png";
        atlasFilePathRun = "Data//HeroRunning//HeroRunningRight.atlas";
        animationSpeedRun = 1/10f;

        spritesheetPathIdle = "Data//HeroIdle//HeroIdle.png";
        atlasFilePathIdle = "Data//HeroIdle//HeroIdle.atlas";
        animationSpeedIdle = 1f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initCreatureAnimations();
    }
}
