package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.WorldConstants;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {

    public RedDeath(float xPos, float yPos, int level, boolean elite) {

        super(100, 100, 25, 1, true, xPos, yPos, level, elite);

        bodyWidth = 10;
        bodyHeight = 15;

    }
}
