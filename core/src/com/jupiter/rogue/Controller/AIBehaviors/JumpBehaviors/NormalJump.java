package com.jupiter.rogue.Controller.AIBehaviors.JumpBehaviors;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.AIBehaviors.Behavior;

/**
 * Created by Oskar on 2015-05-18.
 */
public class NormalJump extends Behavior implements JumpBehavior {

    public NormalJump(Body body){
        this.body = body;
    }

    //Method for a normal jump of varying heights.
    public void jump(float height){
        body.setLinearVelocity(body.getLinearVelocity().x, height);
    }

}
