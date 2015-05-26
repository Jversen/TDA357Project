package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Controller.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Controller.AIBehaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.View.RedDeathView;

/**
 * Created by Johan on 17/04/15.
 */
@lombok.Data
public class RedDeathController extends EnemyController{

    private Position startPosition;

    public RedDeathController(float xPos, float yPos, int level, boolean elite) {
        RedDeath redDeath = new RedDeath(xPos, yPos, level, elite);
        this.enemy = redDeath;
        this.enemyView = new RedDeathView(redDeath);

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
