package com.jupiter.rogue.Utils.libGDX;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;

/**
 * Created by Johan on 04/05/15.
 */
public class HeroMovement implements Movement {
    private Body body;

    public HeroMovement(Body body) {
        this.body = body;
    }

    public Position getPosition() {
        if(body != null) {
            return new Position(body.getPosition().x, body.getPosition().y);
        }
        return null;
    }

    @Override
    public void jump() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void move(Direction direction) {

    }
}
