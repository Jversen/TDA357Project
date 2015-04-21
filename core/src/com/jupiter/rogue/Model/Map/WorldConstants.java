package com.jupiter.rogue.Model.Map;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public class WorldConstants {

    private static WorldConstants instance = null;

    private float gravity = 15f;

    private WorldConstants() {

    }


    public static WorldConstants getInstance() {
        if(instance == null) {
            instance = new WorldConstants();
        }
        return instance;
    }
}
