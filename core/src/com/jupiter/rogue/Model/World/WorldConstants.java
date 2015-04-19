package com.jupiter.rogue.Model.World;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public class WorldConstants {
    private static WorldConstants instance = null;

    private float gravity = 15f;


    public static WorldConstants getInstance() {
        if(instance == null) {
            instance = new WorldConstants();
        }
        return instance;
    }
}
