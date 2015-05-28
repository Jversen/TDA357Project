package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Impact;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.View.WidowView;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public class WidowController extends EnemyController {

    private Position startPosition;

    public WidowController(Widow widow) {
        this.enemy = widow;
        //TODO posibly change view instantiation?
        this.enemyView = new WidowView(widow);

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
