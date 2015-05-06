package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.jupiter.rogue.Utils.WorldConstants;

/**
 * Created by Johan on 21/04/15.
 */
@lombok.Data
public class WorldController {
    private MyContactListener myContactListener;

    public WorldController() {
        myContactListener = new MyContactListener();
        WorldConstants.CURRENT_WORLD.setContactListener(myContactListener);
    }

    public void update() {
        WorldConstants.CURRENT_WORLD.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }
}
