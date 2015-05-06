package com.jupiter.rogue.Utils.libGDX;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import java.lang.Math.*;

/**
 * Created by oskar on 05/05/2015.
 */
public class EnemyMovement implements Movement {

    private Body body;

    public EnemyMovement(Body body) {
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
    public void jump(){

        body.setLinearVelocity(body.getLinearVelocity().x, 6);

    }

    @Override
    public void walk(Direction direction) {
        if(direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2(2f,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x > 2f) {
                body.setLinearVelocity(2, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(new Vector2(-2f,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x < -2f) {
                body.setLinearVelocity(-2, body.getLinearVelocity().y);
            }
        }
    }
    //Method for flying enemy movements.
    public void fly(Direction direction, float angle) {
        if (direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2(2f, cos(angle)), body.getPosition(), true);
            if (body.getLinearVelocity().x > 2f) {
                body.setLinearVelocity(2, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(new Vector2(-2f, angle), body.getPosition(), true);
            if (body.getLinearVelocity().x < -2f) {
                body.setLinearVelocity(-2, body.getLinearVelocity().y);
            }
        }
    }

    public void attack(){

    }

}
