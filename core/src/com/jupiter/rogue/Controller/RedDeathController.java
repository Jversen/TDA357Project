package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.MoveBehavior;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Utils.EnemyMovement;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.RedDeathView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 17/04/15.
 */
@lombok.Data
public class RedDeathController extends EnemyController{

    private EnemyMovement movement;
    private Position startPosition;
    private MoveBehavior moveBehavior;

    public RedDeathController(float xPos, float yPos, int level, boolean elite) {
        RedDeath redDeath = new RedDeath(xPos, yPos, level, elite);
        this.enemy = redDeath;
        this.enemyView = new RedDeathView(redDeath);
        startPosition = enemy.getPosition();
        initBody();

    }

    @Override
    public void initBody() {

        //creates a shapeless body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(startPosition.getXPos() / PPM, startPosition.getYPos() / PPM);

        //creates the shape of the bodyDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 20 / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.0f;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData("enemy");
        body.createFixture(fixtureDef).setUserData("enemy"); //naming the fixture
        movement = new EnemyMovement(body);

        WorldConstants.BODIES.add(body);

        //disposes shape to save memory
        shape.dispose();
    }

    @Override
    public void update(){
        updatePhysics(); //Unnecessary?

        enemy.setEnemyDirection();

        if(!enemy.heroInRange()){
            enemy.walk(enemy.getMovementSpeed(), movement);
        }
        else {
            enemy.attack(movement);
        }
    }

    private void updatePhysics() {
        Position physPos = new Position(movement.getBody().getPosition().x, movement.getBody().getPosition().y);
        enemy.setPosition(physPos);
    }
}
