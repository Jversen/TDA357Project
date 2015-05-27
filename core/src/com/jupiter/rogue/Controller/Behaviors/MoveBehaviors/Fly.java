package com.jupiter.rogue.Controller.Behaviors.MoveBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Controller.Behaviors.Behavior;

/**
 * Created by Oskar on 2015-05-18.
 */
public class Fly extends Behavior implements MoveBehavior {

    //Method for flying enemy movements.
    public void move(Direction direction, float moveSpeed){
        if (direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2((float)Math.cos(45)*moveSpeed, (float)Math.sin(45)*moveSpeed), body.getPosition(), true);

        } else {
            body.applyLinearImpulse(new Vector2(-(float)Math.cos(45)*moveSpeed, (float)Math.sin(45)*moveSpeed), body.getPosition(), true);
        }
    }
}


