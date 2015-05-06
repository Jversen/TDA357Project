package com.jupiter.rogue.Utils.libGDX;

import com.badlogic.gdx.math.Vector2;
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

    public Body getBody() {
        return body;
    }

    public Position getPosition() {
        if(body != null) {
            return new Position(body.getPosition().x, body.getPosition().y);
        }
        return null;
    }

    @Override
    public void jump() {
        body.setLinearVelocity(body.getLinearVelocity().x, 6);
    }

    @Override
    public void attack() {

    }

    @Override
    public void walk(Direction direction) {
        if(direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2(3f,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x > 3f) {
                body.setLinearVelocity(3, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(new Vector2(-3f,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x < -3f) {
                body.setLinearVelocity(-3, body.getLinearVelocity().y);
            }
        }
    }
}
