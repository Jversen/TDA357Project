package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public final class WorldConstants {

    private static WorldConstants instance = null;

    public static final float PPM = 1/32f; //Pixels per meter conversion unit
    private Position HERO_START_POSITION = new Position(75,200);
    private Vector2 HERO_JUMP_VECTOR = new Vector2(0, 10f);
    private float GRAVITY = 10f;
    private World world;

    private WorldConstants() {
        world = new World(new Vector2(0, -GRAVITY), true);
    }


    public static WorldConstants getInstance() {
        if(instance == null) {
            instance = new WorldConstants();
        }
        return instance;
    }
}
