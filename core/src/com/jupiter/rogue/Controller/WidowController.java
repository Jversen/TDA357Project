package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Utils.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.WidowView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

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
