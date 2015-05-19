package com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.Behavior;

/**
 * Created by Oskar on 2015-05-18.
 */
public class Walk extends Behavior implements MoveBehavior {

    public Walk(Body body){
        this.body = body;
    }

    //Method for enemies that move on the ground.
    public void move(Direction direction, float moveSpeed){
        if(direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2(moveSpeed*3,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x > moveSpeed) {
                body.setLinearVelocity(moveSpeed, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(new Vector2(-moveSpeed*3,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x < -moveSpeed) {
                body.setLinearVelocity(-moveSpeed, body.getLinearVelocity().y);
            }
        }
    }

}
