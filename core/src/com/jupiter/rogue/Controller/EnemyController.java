package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Utils.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.EnemyView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public abstract class EnemyController {

    EnemyView enemyView;
    Enemy enemy;
    Body body;

   // public EnemyController(float xPos, float yPos, int level, boolean elite){} //May change later

    public void update(){
        updatePhysics();
        enemy.setEnemyDirection();

        if (heroNotNear()) {
            enemy.setMovementState(MovementState.STANDING);
        } else if (!heroInRange() && !heroNotNear()) {
            enemy.setMovementState(MovementState.WALKING);
            enemy.performMove();
        } else {
            enemy.performAttack();
        }
    }

    public void initBody() {
        //creates a shapeless body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(enemy.getX() / PPM, enemy.getY() / PPM);

        //creates the shape of the bodyDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemy.getBodyWidth() / PPM, enemy.getBodyHeight() / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.0f;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData(this);
        body.createFixture(fixtureDef).setUserData("enemy"); //naming the fixture

        WorldConstants.BODIES.add(body);

        //disposes shape to save memory
        shape.dispose();
    }


    private boolean heroNotNear(){
        return((Math.abs((enemy.getX() + (enemy.getBodyWidth()/2)/PPM) - (Hero.getInstance().getX() + 5/PPM)) > 200/PPM) ||
                (Math.abs((enemy.getY() + (enemy.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) > 200/PPM));
    }

    private boolean heroInRange(){
        return((Math.abs((enemy.getX() + (enemy.getBodyWidth()/2)/PPM) - (Hero.getInstance().getX() + 5/PPM)) <= enemy.getAttackRange()/PPM) &&
                (Math.abs((enemy.getY() + (enemy.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) <= 38/PPM));
    }

    private void updatePhysics() {
        Position physPos = new Position(body.getPosition().x, body.getPosition().y);
        enemy.setPosition(physPos);
    }
}
