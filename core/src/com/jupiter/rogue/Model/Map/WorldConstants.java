package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public final class WorldConstants {

    public static final float PPM = 32f; //Pixels per meter conversion unit
    public static final Position HERO_START_POSITION = new Position(75,200);
    public static final Vector2 HERO_JUMP_VECTOR = new Vector2(0, 10f);
    public static final float GRAVITY = -10f;

    private WorldConstants() {
    }
}
