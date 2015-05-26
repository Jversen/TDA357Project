package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Controller.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Controller.AIBehaviors.MoveBehaviors.Walk;
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
        enemy.setMoveBehavior(new Walk(body));
        enemy.setAttackBehavior(new MeleeAttack(body));
        enemy.setJumpBehavior(new NormalJump(body));
    }
}
