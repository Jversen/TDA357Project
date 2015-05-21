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
        //super(xPos, yPos, level, elite); //Feels unnecessary, just to make compilation work
        Widow widow = new Widow(xPos, yPos, level, elite);
        this.enemy = widow;
        this.enemyView = new WidowView(widow);
        startPosition = enemy.getPosition();
        initBody();
    }
        public void initBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(enemy.getX() / PPM, enemy.getY() / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 20 / PPM);

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.0f;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData(this);

        body.createFixture(fixtureDef).setUserData("enemy");
        WorldConstants.BODIES.add(body);

            enemy.setMoveBehavior(new Walk(body));
            enemy.setAttackBehavior(new MeleeAttack(body));
            enemy.setJumpBehavior(new NormalJump(body));

        //disposes shape to save memory
        shape.dispose();
    }


    @Override
    public void update(){
        updatePhysics(); //Unnecessary?'
        enemy.setEnemyDirection();
        if(heroNotNear()) {
            enemy.setMovementState(MovementState.STANDING);
        }else if(!heroInRange() && !heroNotNear()){
            enemy.setMovementState(MovementState.WALKING);
            enemy.performMove();
        }
        else {
            enemy.performAttack();
        }
    }

    /* This seems unnecessary and should probably be deleted?
     */
    private void updatePhysics() {
        Position physPos = new Position(body.getPosition().x, body.getPosition().y);
        enemy.setPosition(physPos);
    }
}
