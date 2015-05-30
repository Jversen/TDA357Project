package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Impact;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.RedDeathView;

/**
 * Created by Johan on 17/04/15.
 */
@lombok.Data
public class RedDeathController extends EnemyController{

    private Position startPosition;

    public RedDeathController(RedDeath redDeath) {
        this.enemy = redDeath;
        startPosition = enemy.getPosition();
    }

    @Override
    public void initBody() {
        super.initBody();

        //Sets the move behaivors of this enemy.
        attackBehavior = new MeleeAttack(this.body);
        jumpBehavior = new NormalJump(this.body);
        moveBehavior = new Walk(this.body);
        takeDamageBehavior = new Impact(this.body);
    }
}
