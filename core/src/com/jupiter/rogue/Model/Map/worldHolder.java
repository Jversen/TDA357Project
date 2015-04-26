package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Singleton. A singleton class to hold the world, since the World class itself is not singleton.
 * Created by hilden on 2015-04-26.
 */
public class WorldHolder {

    private static WorldHolder instance = null;
    private World world;

    private WorldHolder() {
        world  = new World(new Vector2(0, WorldConstants.GRAVITY), true);
    }

    public static WorldHolder getInstance() {
        if (instance == null) {
            instance = new WorldHolder();
        }
        return instance;
    }

    public World getWorld() {
        return world;
    }
}

