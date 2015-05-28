package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Vulnerable;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Model.Map.Position;
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

    public WidowController(float xPos, float yPos, int level, boolean elite) {
        Widow widow = new Widow(xPos, yPos, level, elite);
        this.enemy = widow;
        this.enemyView = new WidowView(widow);

        startPosition = enemy.getPosition();
        initBody();
    }

    @Override
    public void initBody() {
        super.initBody();

        //Sets the move behaivors of this enemy.
        attackBehavior = new MeleeAttack(this.body);
        jumpBehavior = new NormalJump(this.body);
        moveBehavior = new Walk(this.body);
        takeDamageBehavior = new Vulnerable(this.body);
    }
}
