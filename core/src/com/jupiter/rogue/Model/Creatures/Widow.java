package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.ai.steer.behaviors.Jump;
import com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Utils.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.Walk;

/**
 * Created by Johan on 2015-05-10.
 */
public class Widow extends Enemy {
    
    public Widow(float xPos, float yPos, int level, boolean elite){
        super(100, 100, 25, 25, 1, true, xPos, yPos, level, elite);

        //moveBehavior = new Walk();
        //jumpBehavior = new NormalJump();
        //attackBehavior = new MeleeAttack();
    }
}
