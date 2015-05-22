package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.EnemyView;

import java.util.Timer;
import java.util.TimerTask;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public abstract class EnemyController {

    EnemyView enemyView;
    Enemy enemy;
    Body body;

    //Hitbox handling
    private PolygonShape shape;
    private FixtureDef weaponSensorFixtureDef;
    private Fixture weaponSensorFixture;

    private int hitBoxX;

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
//            createHitbox();
        }
    }

    public void initBody() {
        //creates a shapeless body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(enemy.getX() / PPM, enemy.getY() / PPM);

        //creates the shape of the bodyDef
        shape = new PolygonShape();
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

        weaponSensorFixtureDef = new FixtureDef();
    }

    private void createHitbox() {

        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("enemyHitbox");  //*************************** THIS FIXTURE MIGHT NEED "THIS" AS DATA ***********************************

        //clears memory
        shape.dispose();
    }

    private void removeHitbox() {
        body.destroyFixture(weaponSensorFixture);
    }

    //help-method to the createWeaponHitBox methods, gets the hitbox information from the heroes weapon and then sets a hitbox accordingly depending on what direction the hero is facing.
    private void hitBoxShapeMaker() {
        hitBoxX = enemy.getHitBoxX();
        if (enemy.getDirection() == Direction.LEFT) {
            hitBoxX = hitBoxX * -1;
        }
        shape.setAsBox(enemy.getHitBoxWidth() / PPM, enemy.getHitBoxHeight() / PPM, new Vector2(hitBoxX / PPM, enemy.getHitBoxY() / PPM), 0);
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
