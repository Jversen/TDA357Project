package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Utils.WorldConstants;

/**
 * Created by Johan on 21/04/15.
 */
@lombok.Data
public class WorldController {
    private MyContactListener myContactListener;

    private Body body;

    public WorldController() {
        myContactListener = new MyContactListener();
        WorldConstants.CURRENT_WORLD.setContactListener(myContactListener);
    }

    public void update() {
        WorldConstants.CURRENT_WORLD.step(Gdx.graphics.getDeltaTime(), 6, 2);
        removeBodiesSafely();
        WorldConstants.CURRENT_WORLD.getBodies(WorldConstants.BODIES);
    }

    //A method that removes all bodies from the WorldConstants BODIES variable that are marked with the userdata "dead".
    private void removeBodiesSafely() {
        for (int i = 0; i < WorldConstants.CURRENT_WORLD.getBodyCount(); i++) {
            body = WorldConstants.BODIES.get(i);
            if (body != null) {
                if (body.getUserData() != null) {
                    if (body.getUserData().equals("dead")) {
                        WorldConstants.CURRENT_WORLD.destroyBody(body);
                    }
                }
            }
        }
    }
}
