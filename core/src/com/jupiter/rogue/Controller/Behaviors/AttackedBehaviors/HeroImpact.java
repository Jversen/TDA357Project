package com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.Behaviors.Behavior;
import com.jupiter.rogue.Utils.Enums.Direction;

/**
 * Created by hilden on 2015-05-28.
 */
public class HeroImpact extends Behavior implements AttackedBehavior {

    public HeroImpact(Body body) {
        this.body = body;
    }

    public void impact(Direction heroDir){
        if(heroDir == Direction.RIGHT){
            body.applyLinearImpulse(new Vector2(-6f, 0f), body.getPosition(), false);
            body.setLinearVelocity(3, body.getLinearVelocity().y);
            body.setLinearVelocity(-3, body.getLinearVelocity().x);
        } else {
            body.applyLinearImpulse(new Vector2(6f, 0f), body.getPosition(), false);
            body.setLinearVelocity(3, body.getLinearVelocity().y);
            body.setLinearVelocity(3, body.getLinearVelocity().x);
        }
    }
}
