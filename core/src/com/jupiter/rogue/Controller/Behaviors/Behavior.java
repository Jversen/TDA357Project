package com.jupiter.rogue.Controller.Behaviors;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Map.Position;

/**
 * Created by Oskar on 2015-05-19.
 */

//Class which contains some common data needed for each behavior
public abstract class Behavior {

    protected Body body;

    public Body getBody(){
        return body;
    }

    public Position getPosition(){
        return new Position(body.getPosition().x, body.getPosition().y);
    }

}
