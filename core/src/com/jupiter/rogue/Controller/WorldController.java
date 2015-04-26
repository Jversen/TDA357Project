package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Model.Map.WorldConstants;
import com.jupiter.rogue.Model.Map.WorldHolder;

/**
 * Created by Johan on 21/04/15.
 */
@lombok.Data
public class WorldController {

    private World world;
    private MyContactListener myContactListener;

    public WorldController() {
        world = WorldHolder.getInstance().getWorld();
        myContactListener = new MyContactListener();
        world.setContactListener(myContactListener);
    }

    public void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }
}
