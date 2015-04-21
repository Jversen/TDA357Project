package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Model.Map.WorldConstants;

/**
 * Created by Johan on 21/04/15.
 */
public class WorldController {
    World world = WorldConstants.getInstance().getWorld();

    public void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }
}
