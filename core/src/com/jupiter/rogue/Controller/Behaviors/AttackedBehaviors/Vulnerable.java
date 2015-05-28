package com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Controller.Behaviors.Behavior;

/**
 * Created by Oskar on 2015-05-19.
 */
public class Vulnerable extends Behavior implements AttackedBehavior {

    public Vulnerable(Body body) {
        this.body = body;
    }

    public void takeDamage(Direction heroDir){
        if(heroDir == Direction.LEFT){
            body.applyLinearImpulse(new Vector2(-6f, 0f), body.getPosition(), false);
            if(body.getLinearVelocity().x < -6) {
                body.setLinearVelocity(-6, body.getLinearVelocity().y);
            }
        }
        else{
            body.applyLinearImpulse(new Vector2(6f, 0f), body.getPosition(), false);
            if(body.getLinearVelocity().x < 6) {
                body.setLinearVelocity(6, body.getLinearVelocity().y);
            }
        }
    }
}
