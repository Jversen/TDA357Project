package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public class WorldConstants {

    private static WorldConstants instance = null;

    private float gravity = 15f;
    private World world;

    private WorldConstants() {
        world = new World(new Vector2(0, -gravity), true);
    }


    public static WorldConstants getInstance() {
        if(instance == null) {
            instance = new WorldConstants();
        }
        return instance;
    }
}
