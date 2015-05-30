package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.AttackBehavior;
import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Impact;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.JumpBehavior;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.MoveBehavior;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.EnemyView;

import java.util.Timer;
import java.util.TimerTask;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;
import static com.jupiter.rogue.Utils.WorldConstants.TILE_SIZE;

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
        body.createFixture(fixtureDef).setUserData(this); //naming the fixture

    //    WorldConstants.BODIES.add(body);

        //disposes shape to save memory
        shape.dispose();

        weaponSensorFixtureDef = new FixtureDef();

        timer = new Timer();
        attackReady = true;
    }

    public void update(){
        updatePhysics();

        if (enemy.isCreatureDying()) {
            this.die();
        } else {
            if (!enemy.isIncapacitated()) {
                enemy.setEnemyDirection();
                if (heroNotNear()) {
                    enemy.relax();
                } else if (!heroInRange() && !heroNotNear()) {
                    enemy.setEnemyDirection();
                    enemy.walk(enemy.getDirection());
                    moveBehavior.move(enemy.getDirection(), enemy.getMovementSpeed());
                    /*if(enemyShouldJump()){
                        enemy.jump();
                        jumpBehavior.jump(enemy.getJumpHeight());
                    }*/
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
        timer.schedule(new CreateHitBoxTask(), 600);
    }

    private void die() {
        if (enemy.getMovementState() != MovementState.DYING) {
            Hero.getInstance().increaseExperience(enemy.getXpValue());
            enemy.die();
            timer.schedule(new SetCreatureDeadTask(), 950);
        }
    }

    private void createHitbox() {
        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();
        System.out.println("1");

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        System.out.println("2");
        if (!WorldConstants.CURRENT_WORLD.isLocked()) {
            weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        }
        System.out.println("3");
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("enemyHitbox");
        System.out.println("4");
        //clears memory
        shape.dispose();
        System.out.println("5");
    }

    private void removeHitbox() {
        weaponSensorFixture.setUserData("dead");
    }

    //help-method to the createWeaponHitBox methods, gets the hitbox information from the heroes weapon and then sets a hitbox accordingly depending on what direction the hero is facing.
    private void hitBoxShapeMaker() {
        hitBoxX = enemy.getAttackHitBoxX();
        if (enemy.getDirection() == Direction.LEFT) {
            hitBoxX = hitBoxX * -1;
        }
        shape.setAsBox(enemy.getAttackHitBoxWidth() / PPM, enemy.getAttackHitBoxHeight() / PPM, new Vector2(hitBoxX / PPM, enemy.getAttackHitBoxY() / PPM), 0);
    }
    private boolean enemyShouldJump(){
        if(enemy.getDirection() == Direction.RIGHT){
            return (Map.getInstance().getCurrentRoom().getTiledHandler().getSensorLayer().getCell((int)enemy.getX()/TILE_SIZE + 2*TILE_SIZE, (int)enemy.getY()/TILE_SIZE) != null);
        }
        else if(enemy.getDirection() == Direction.LEFT){
            return (Map.getInstance().getCurrentRoom().getTiledHandler().getSensorLayer().getCell((int)enemy.getX()/TILE_SIZE - 2*TILE_SIZE, (int)enemy.getY()/TILE_SIZE) != null);
        }
        return false;
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

    //A nestled class to implement a timertask. Timertask to control the delay for hitboxes to be created.
    class CreateHitBoxTask extends TimerTask {
        public void run() {
            if (!enemy.isCreatureDying()) {
                createHitbox();
                timer.schedule(new RemoveHitBoxTask(), 400);
            }
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
