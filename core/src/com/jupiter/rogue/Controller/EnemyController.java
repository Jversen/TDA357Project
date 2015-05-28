package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.AttackBehavior;
import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Impact;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.JumpBehavior;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.MoveBehavior;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Utils.Position;
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

    protected AttackBehavior attackBehavior;
    protected JumpBehavior jumpBehavior;
    protected MoveBehavior moveBehavior;
    protected Impact takeDamageBehavior;

    //Hitbox handling
    private PolygonShape shape;
    private FixtureDef weaponSensorFixtureDef;
    private Fixture weaponSensorFixture;

    private int hitBoxX;

    private boolean attackReady;

    private Timer timer;

    public void initBody() {
        //creates a shapeless body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(enemy.getX() / PPM, enemy.getY() / PPM);

        //creates the shape of the bodyDef
        shape = new PolygonShape();
        shape.setAsBox(enemy.getBodyWidth() / PPM, enemy.getBodyHeight() / PPM, new Vector2(0, enemy.getBodyY()), 0);

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData(this);
        body.createFixture(fixtureDef).setUserData("enemy"); //naming the fixture

        WorldConstants.BODIES.add(body);

        //disposes shape to save memory
        shape.dispose();

        weaponSensorFixtureDef = new FixtureDef();

        timer = new Timer();
        attackReady = true;
    }

    public void update(){
        updatePhysics();

        if (enemy.isCreatureDying()) {
            if (enemy.getMovementState() != MovementState.DYING) {
                Hero.getInstance().increaseExperience(enemy.getXpValue());
                enemy.setMovementState(MovementState.DYING);
                timer.schedule(new SetCreatureDeadTask(), 1450);
            }
        } else {
            if (!enemy.isIncapacitated()) {
                enemy.setEnemyDirection();
                if (heroNotNear()) {
                    enemy.relax();
                } else if (!heroInRange() && !heroNotNear()) {
                    enemy.setEnemyDirection();
                    enemy.walk(enemy.getDirection());
                    moveBehavior.move(enemy.getDirection(), enemy.getMovementSpeed());
                } else {
                    if (attackReady) {
                        attack();
                    }
                }
            }
        }
    }

    private void attack() {
        attackReady = false;
        enemy.setAttackInProgress(true);
        enemy.attack();
        createHitbox();
        timer.schedule(new RemoveHitBoxTask(), 1000);
    }

    private void createHitbox() {

        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("enemyHitbox");

        //clears memory
        shape.dispose();
    }

    private void removeHitbox() {
        if (body != null && body.getFixtureList() != null && body.getFixtureList().contains(weaponSensorFixture, true)){
            body.destroyFixture(weaponSensorFixture);
        }
    }

    //help-method to the createWeaponHitBox methods, gets the hitbox information from the heroes weapon and then sets a hitbox accordingly depending on what direction the hero is facing.
    private void hitBoxShapeMaker() {
        hitBoxX = enemy.getAttackHitBoxX();
        if (enemy.getDirection() == Direction.LEFT) {
            hitBoxX = hitBoxX * -1;
        }
        shape.setAsBox(enemy.getAttackHitBoxWidth() / PPM, enemy.getAttackHitBoxHeight() / PPM, new Vector2(hitBoxX / PPM, enemy.getAttackHitBoxY() / PPM), 0);
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

    //A nestled class to implement a timertask. Timertask to control the time before a dying enemies dies.
    class SetCreatureDeadTask extends TimerTask {
        public void run() {
            enemy.setCreatureDead(true);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the delay for hitboxes to be removed.
    class RemoveHitBoxTask extends TimerTask {
        public void run() {
            removeHitbox();
            enemy.setAttackInProgress(false);
            timer.schedule(new AttackReadyTask(), 200);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the attack cooldown of enemies.
    class AttackReadyTask extends TimerTask {
        public void run() {
            attackReady = true;
        }
    }
}
