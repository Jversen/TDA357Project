package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Immovable;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.CanNotJump;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.Stationary;
import com.jupiter.rogue.Model.Creatures.Boss;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.View.BossView;

/**
 * Created by hilden on 2015-05-30.
 */
public class BossController extends EnemyController {
    private Position startPosition;

    public BossController(Boss boss) {
        this.enemy = boss;
        this.enemyView = new BossView(boss);

        startPosition = enemy.getPosition();
    }

    @Override
    public void initBody() {
        super.initBody();

        //Sets the move behaivors of this enemy.
        attackBehavior = new MeleeAttack(this.body);
        jumpBehavior = new CanNotJump();
        moveBehavior = new Stationary();
        takeDamageBehavior = new Immovable();
    }

    @Override
    public void update() {

    }
}
