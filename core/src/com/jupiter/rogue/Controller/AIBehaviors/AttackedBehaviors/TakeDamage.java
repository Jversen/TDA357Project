package com.jupiter.rogue.Controller.AIBehaviors.AttackedBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Controller.AIBehaviors.Behavior;

/**
 * Created by Oskar on 2015-05-19.
 */
public class TakeDamage extends Behavior implements AttackedBehavior {

    public void attacked(Direction heroDir){
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
